package sd.fomin.gerbera.transaction;

import sd.fomin.gerbera.constant.OpCodes;
import sd.fomin.gerbera.constant.SigHashType;
import sd.fomin.gerbera.types.OpSize;
import sd.fomin.gerbera.types.ULong;
import sd.fomin.gerbera.util.ByteBuffer;
import sd.fomin.gerbera.types.UInt;
import sd.fomin.gerbera.types.VarInt;
import sd.fomin.gerbera.util.HashUtils;
import sd.fomin.gerbera.util.HexUtils;

import java.util.LinkedList;
import java.util.List;

public class TransactionBuilder {

    private static final String DONATE_ADDRESS_MAINNET = "3Jyiwca9Gz8fD9LCCw5JnPaUciEtM6Fi7Z";
    private static final String DONATE_ADDRESS_TESTNET = "mwC7PAhQWSHmjeVXuCwXaP28kjMmsr2LZk";

    private static final UInt VERSION = UInt.of(1);
    private static final byte SEGWIT_MARKER = (byte) 0x00;
    private static final byte SEGWIT_FLAG = (byte) 0x01;
    private static final UInt LOCK_TIME = UInt.of(0);

    private boolean mainNet;

    private List<Input> inputs = new LinkedList<>();
    private List<Output> outputs = new LinkedList<>();

    private String changeAddress;

    private long fee;
    private long donate;

    private TransactionBuilder(boolean mainNet) {
        this.mainNet = mainNet;
    }

    public static TransactionBuilder create() {
        return new TransactionBuilder(true);
    }

    public static TransactionBuilder create(boolean mainNet) {
        return new TransactionBuilder(mainNet);
    }

    public TransactionBuilder from(String fromTransactionBigEnd, int fromToutNumber, String closingScript, long satoshi) {
        inputs.add(new Input(fromTransactionBigEnd, fromToutNumber, closingScript, satoshi));
        return this;
    }

    public TransactionBuilder signedWithWif(String privateKey) {
        inputs.stream().filter(input -> !input.hasPrivateKey()).forEach(input -> input.setPrivateKey(mainNet, privateKey));
        return this;
    }

    public TransactionBuilder to(String address, long value) {
        outputs.add(new Output(mainNet, value, address, OutputType.CUSTOM));
        return this;
    }

    public TransactionBuilder changeTo(String changeAddress) {
        this.changeAddress = changeAddress;
        return this;
    }

    public TransactionBuilder withFee(long fee) {
        this.fee = fee;
        return this;
    }

    public TransactionBuilder donate(long donate) {
        this.donate = donate;
        return this;
    }

    public Transaction build() {
        if (donate > 0) {
            String donateAddress = mainNet ? DONATE_ADDRESS_MAINNET : DONATE_ADDRESS_TESTNET;
            outputs.add(new Output(mainNet, donate, donateAddress, OutputType.DONATE));
        }

        long change = getChange();
        if (change > 0) {
            outputs.add(new Output(mainNet, change, changeAddress, OutputType.CHANGE));
        }

        boolean buildSegWitTransaction = inputs.stream().anyMatch(i -> i.isSegWit());

        Transaction transaction = new Transaction();
        transaction.addLine("Version", VERSION.toString());
        if (buildSegWitTransaction) {
            transaction.addLine("Marker", HexUtils.asString(SEGWIT_MARKER));
            transaction.addLine("Flag", HexUtils.asString(SEGWIT_FLAG));
        }

        transaction.addLine("Input count", VarInt.of(inputs.size()).toString());
        List<byte[]> witnesses = new LinkedList<>();
        for (int i = 0; i < inputs.size(); i++) {
            byte[] sigHash = getSigHash(i);
            inputs.get(i).fillTransaction(sigHash, transaction);
            if (buildSegWitTransaction) {
                witnesses.add(inputs.get(i).getWitness(sigHash));
            }
        }

        transaction.addLine("Output count", VarInt.of(outputs.size()).toString());
        outputs.forEach(output -> output.fillTransaction(transaction));

        if (buildSegWitTransaction) {
            transaction.addLine("Witnesses");
            witnesses.forEach(w ->
                    transaction.addLine("   Witness", HexUtils.asString(w))
            );
        }

        transaction.addLine("Locktime", LOCK_TIME.toString());

        transaction.setFee(fee);

        return transaction;
    }

    private byte[] getSigHash(int signedIndex) {
        ByteBuffer signBase = new ByteBuffer();

        signBase.append(VERSION.asLitEndBytes());
        if (inputs.get(signedIndex).isSegWit()) {
            signBase.append(getSegwitPreimage(signedIndex));
        } else {
            signBase.append(getRegularPreimage(signedIndex));
        }
        signBase.append(LOCK_TIME.asLitEndBytes());
        signBase.append(SigHashType.ALL.asLitEndBytes());

        return HashUtils.sha256(HashUtils.sha256(signBase.bytes()));
    }

    private byte[] getRegularPreimage(int signedIndex) {
        ByteBuffer result = new ByteBuffer();

        result.append(VarInt.of(inputs.size()).asLitEndBytes());
        for (int i = 0; i < inputs.size(); i++) {
            Input input = inputs.get(i);
            result.append(input.getTransactionHashBytesLitEnd());
            result.append(UInt.of(input.getIndex()).asLitEndBytes());

            if (i == signedIndex) {
                byte[] lockBytes = HexUtils.asBytes(input.getLock());
                result.append(VarInt.of(lockBytes.length).asLitEndBytes());
                result.append(lockBytes);
            } else {
                result.append(VarInt.of(0).asLitEndBytes());
            }

            result.append(input.getSequence().asLitEndBytes());
        }

        result.append(VarInt.of(outputs.size()).asLitEndBytes());
        outputs.stream().map(Output::serializeForSigHash).forEach(result::append);

        return result.bytes();
    }

    private byte[] getSegwitPreimage(int signedIndex) {
        ByteBuffer result = new ByteBuffer();
        Input currentInput = inputs.get(signedIndex);

        ByteBuffer prevOuts = new ByteBuffer(); //hashPrevOuts
        for (int i = 0; i < inputs.size(); i++) {
            Input input = inputs.get(i);
            prevOuts.append(input.getTransactionHashBytesLitEnd());
            prevOuts.append(UInt.of(input.getIndex()).asLitEndBytes());
        }
        result.append(HashUtils.sha256(HashUtils.sha256(prevOuts.bytes())));

        ByteBuffer sequences = new ByteBuffer(); //hashSequences
        for (int i = 0; i < inputs.size(); i++) {
            sequences.append(inputs.get(i).getSequence().asLitEndBytes());
        }
        result.append(HashUtils.sha256(HashUtils.sha256(sequences.bytes())));

        result.append(currentInput.getTransactionHashBytesLitEnd()); //outpoint
        result.append(UInt.of(currentInput.getIndex()).asLitEndBytes());

        byte[] pkh = HashUtils.ripemd160(HashUtils.sha256(currentInput.getPrivateKey().getPublicKey())); //scriptCode
        ByteBuffer scriptCode = new ByteBuffer(OpCodes.DUP, OpCodes.HASH160, (byte) 0x14);
        scriptCode.append(pkh);
        scriptCode.append(OpCodes.EQUALVERIFY, OpCodes.CHECKSIG);
        scriptCode.putFirst(OpSize.ofInt(scriptCode.size()).getSize());
        result.append(scriptCode.bytes());

        result.append(ULong.of(currentInput.getSatoshi()).asLitEndBytes()); //amount in

        result.append(currentInput.getSequence().asLitEndBytes()); //sequence

        ByteBuffer outs = new ByteBuffer(); //hash outs
        outputs.stream().map(Output::serializeForSigHash).forEach(outs::append);
        result.append(HashUtils.sha256(HashUtils.sha256(outs.bytes())));

        return result.bytes();
    }

    private long getChange() {
        long income = inputs.stream().mapToLong(Input::getSatoshi).sum();
        long outcome = outputs.stream().mapToLong(Output::getSatoshi).sum();
        long change = income - outcome - fee;

        if (change < 0) {
            throw new IllegalStateException("Not enough satoshi. All inputs: " + income +
                    ". All outputs with fee: " + (outcome + fee));
        }

        if (change > 0 && changeAddress == null) {
            throw new IllegalStateException("Transaction contains change (" + change + " satoshi) but no address to send them to");
        }

        return change;
    }
}
