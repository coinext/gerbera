package sd.fomin.gerbera.transaction;

import sd.fomin.gerbera.constant.SigHashType;
import sd.fomin.gerbera.util.ByteBuffer;
import sd.fomin.gerbera.types.UInt;
import sd.fomin.gerbera.types.VarInt;
import sd.fomin.gerbera.util.HashUtils;

import java.util.LinkedList;
import java.util.List;

public class TransactionBuilder {

    private static final String DONATE_ADDRESS = "16F1Z5PWTxSLfc47xuuQuTmjYMxs2UuKVT";

    private static final UInt VERSION = UInt.of(1);
    private static final UInt LOCK_TIME = UInt.of(0);

    private List<Input> inputs = new LinkedList<>();
    private List<Output> outputs = new LinkedList<>();

    private String changeAddress;

    private long fee;
    private long donate;

    private TransactionBuilder() { }

    public static TransactionBuilder create() {
        return new TransactionBuilder();
    }

    public TransactionBuilder from(String fromTransactionBigEnd, int fromToutNumber, String closingScript, long satoshi) {
        inputs.add(new Input(fromTransactionBigEnd, fromToutNumber, closingScript, satoshi));
        return this;
    }

    public TransactionBuilder signedWithWif(String privateKey) {
        inputs.stream().filter(input -> !input.hasPrivateKey()).forEach(input -> input.setPrivateKey(privateKey));
        return this;
    }

    public TransactionBuilder to(String address, long value) {
        outputs.add(new Output(value, address, OutputType.CUSTOM));
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
            outputs.add(new Output(donate, DONATE_ADDRESS, OutputType.DONATE));
        }

        long change = getChange();
        if (change > 0) {
            outputs.add(new Output(change, changeAddress, OutputType.CHANGE));
        }

        Transaction transaction = new Transaction();
        transaction.addLine("Version", VERSION.toString());

        transaction.addLine("Input count", VarInt.of(inputs.size()).toString());
        for (int i = 0; i < inputs.size(); i++) {
            byte[] signBase = getSignBase(i);
            inputs.get(i).fillTransaction(signBase, transaction);
        }

        transaction.addLine("Output count", VarInt.of(outputs.size()).toString());
        outputs.forEach(output -> output.fillTransaction(transaction));

        transaction.addLine("Locktime", LOCK_TIME.toString());

        return transaction;
    }

    private byte[] getSignBase(int signedIndex) {
        ByteBuffer signBase = new ByteBuffer();

        signBase.append(VERSION.asLitEndBytes());

        signBase.append(VarInt.of(inputs.size()).asLitEndBytes());
        for (int i = 0; i < inputs.size(); i++) {
            Input input = inputs.get(i);
            byte[] serialized = input.serializeForSign(i == signedIndex);
            signBase.append(serialized);
        }

        signBase.append(VarInt.of(outputs.size()).asLitEndBytes());
        outputs.stream().map(Output::serializeForSign).forEach(signBase::append);

        signBase.append(LOCK_TIME.asLitEndBytes());

        signBase.append(SigHashType.ALL.asLitEndBytes());

        return HashUtils.sha256(HashUtils.sha256(signBase.bytes()));
    }

    private long getChange() {
        long income = inputs.stream().mapToLong(Input::getSatoshi).sum();
        long outcome = outputs.stream().mapToLong(Output::getSatoshi).sum();
        long change = income - outcome - fee;

        if (change < 0) {
            throw new IllegalStateException();
        }

        if (change > 0 && changeAddress == null) {
            throw new IllegalStateException("Transaction contains change (" + change + " satoshi) but no address to send them to");
        }

        return change;
    }
}
