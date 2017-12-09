package sd.fomin.gerbera.transaction;

import sd.fomin.gerbera.constant.OpCodes;
import sd.fomin.gerbera.constant.SigHashType;
import sd.fomin.gerbera.types.OpSize;
import sd.fomin.gerbera.util.ByteBuffer;
import sd.fomin.gerbera.crypto.PrivateKey;
import sd.fomin.gerbera.types.UInt;
import sd.fomin.gerbera.types.VarInt;
import sd.fomin.gerbera.util.HexUtils;

class Input {

    private static final UInt SEQUENCE = UInt.of(0xFFFFFFFF);

    private String transaction;

    private int index;

    private String lock;

    private long satoshi;

    private PrivateKey privateKey;

    Input(String transaction, int index, String lock, long satoshi) {
        this.transaction = transaction;
        this.index = index;
        this.lock = lock;
        this.satoshi = satoshi;
        validate();
    }

    byte[] serializeForSign(boolean includeLockingScript) {
        ByteBuffer serialized = new ByteBuffer();

        serialized.append(getTransactionHashBytesLitEnd());
        serialized.append(UInt.of(index).asLitEndBytes());

        if (includeLockingScript) {
            byte[] lockBytes = HexUtils.asBytes(lock);
            serialized.append(VarInt.of(lockBytes.length).asLitEndBytes());
            serialized.append(lockBytes);
        } else {
            serialized.append(VarInt.of(0).asLitEndBytes());
        }

        serialized.append(SEQUENCE.asLitEndBytes());

        return serialized.bytes();
    }

    void setPrivateKey(String wif) {
        privateKey = PrivateKey.ofWif(wif);
    }

    void fillTransaction(byte[] signBase, Transaction transaction) {
        transaction.addLine("   Input");

        byte[] unlocking = unlocking(signBase);
        transaction.addLine("      Transaction out", HexUtils.asString(getTransactionHashBytesLitEnd()));
        transaction.addLine("      Tout index", UInt.of(index).toString());
        transaction.addLine("      Unlock length", HexUtils.asString(VarInt.of(unlocking.length).asLitEndBytes()));
        transaction.addLine("      Unlock", HexUtils.asString(unlocking));
        transaction.addLine("      Sequence", SEQUENCE.toString());
    }

    boolean hasPrivateKey() {
        return privateKey != null;
    }

    long getSatoshi() {
        return satoshi;
    }

    private void validate() {
        if (transaction == null) {
            throw new IllegalArgumentException("Previous transaction hash must not be null");
        }
        if (index < 0) {
            throw new IllegalArgumentException("Previous transaction output index must be a positive value");
        }
        if (lock == null) {
            throw new IllegalArgumentException("Locking script must not be null");
        }
        if (satoshi <= 0) {
            throw new IllegalArgumentException("Number of satoshi must be a positive value");
        }
        validateLockingScript();
    }

    private void validateLockingScript() {
        byte[] lockBytes = HexUtils.asBytes(lock);

        if (lockBytes[0] != OpCodes.DUP
                || lockBytes[1] != OpCodes.HASH160
                || lockBytes[lockBytes.length - 2] != OpCodes.EQUALVERIFY
                || lockBytes[lockBytes.length - 1] != OpCodes.CHECKSIG) {
            throw new IllegalArgumentException("Provided closing script is not P2PKH [" + lock + "]");
        }

        OpSize pubKeyHashSize = OpSize.ofByte(lockBytes[2]);
        if (pubKeyHashSize.getSize() != lockBytes.length - 5) {
            throw new IllegalArgumentException("Incorrect PKH size. " +
                    "Expected: " + pubKeyHashSize.getSize() +
                    ". [" + lock + "]");
        }
    }

    private byte[] unlocking(byte[] signBase) {
        if (privateKey == null) {
            throw new IllegalStateException(
                    "No WIF provided for input [" + transaction + ", " + index + "]");
        }

        ByteBuffer unlocking = new ByteBuffer();

        unlocking.append(privateKey.sign(signBase));
        unlocking.append(SigHashType.ALL.asByte());

        unlocking.putFirst(OpSize.ofInt(unlocking.size()).getSize());

        byte[] publicKey = privateKey.getPublicKey();
        unlocking.append(OpSize.ofInt(publicKey.length).getSize());
        unlocking.append(publicKey);

        return unlocking.bytes();
    }

    private byte[] getTransactionHashBytesLitEnd() {
        return new ByteBuffer(HexUtils.asBytes(transaction)).bytesReversed();
    }
}
