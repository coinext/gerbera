package sd.fomin.gerbera.transaction;

import sd.fomin.gerbera.constant.OpCodes;
import sd.fomin.gerbera.constant.SigHashType;
import sd.fomin.gerbera.types.OpSize;
import sd.fomin.gerbera.util.ByteBuffer;
import sd.fomin.gerbera.crypto.PrivateKey;
import sd.fomin.gerbera.types.UInt;
import sd.fomin.gerbera.types.VarInt;
import sd.fomin.gerbera.util.HashUtils;
import sd.fomin.gerbera.util.HexUtils;

class Input {

    private static final UInt SEQUENCE = UInt.of(0xFFFFFFFF);

    private final boolean mainNet;
    private final String transaction;
    private final int index;
    private final String lock;
    private final long satoshi;
    private final String wif;

    Input(boolean mainNet, String transaction, int index, String lock, long satoshi, String wif) {
        if (transaction == null || transaction.trim().isEmpty()) {
            throw new IllegalArgumentException("Previous transaction hash must not be null or empty");
        }
        if (index < 0) {
            throw new IllegalArgumentException("Previous transaction output index must be a positive value");
        }
        if (lock == null || lock.trim().isEmpty()) {
            throw new IllegalArgumentException("Locking script must not be null or empty");
        }
        if (satoshi <= 0) {
            throw new IllegalArgumentException("Number of satoshi must be a positive value");
        }
        if (wif == null || wif.trim().isEmpty()) {
            throw new IllegalArgumentException("WIF must not be null or empty");
        }
        this.mainNet = mainNet;
        this.transaction = transaction;
        this.index = index;
        this.lock = lock;
        this.satoshi = satoshi;
        this.wif = wif;

        validateLockingScript();
    }

    private void validateLockingScript() {
        LockScriptType lockScriptType = LockScriptType.forLock(lock);
        if (LockScriptType.P2PKH.equals(lockScriptType)) {
            byte[] lockBytes = HexUtils.asBytes(lock);
            OpSize pubKeyHashSize = OpSize.ofByte(lockBytes[2]);
            if (pubKeyHashSize.getSize() != lockBytes.length - 5) {
                throw new IllegalArgumentException("Incorrect PKH size. " +
                        "Expected: " + pubKeyHashSize.getSize() +
                        ". [" + lock + "]");
            }
        } else if (LockScriptType.P2SH.equals(lockScriptType)) {
            byte[] lockBytes = HexUtils.asBytes(lock);
            OpSize pubKeyHashSize = OpSize.ofByte(lockBytes[1]);
            if (pubKeyHashSize.getSize() != lockBytes.length - 3) {
                throw new IllegalArgumentException("Incorrect redeemScript size. " +
                        "Expected: " + pubKeyHashSize.getSize() +
                        ". [" + lock + "]");
            }
        } else {
            throw new IllegalArgumentException("Provided locking script is not P2PKH or P2SH [" + lock + "]");
        }
    }

    void fillTransaction(byte[] sigHash, Transaction transaction) {
        boolean segWit = isSegWit();

        transaction.addHeader(segWit ? "   Input (Segwit)" : "   Input");

        byte[] unlocking = segWit ? createUnlockSegwit() : createUnlockRegular(sigHash);
        transaction.addData("      Transaction out", HexUtils.asString(getTransactionHashBytesLitEnd()));
        transaction.addData("      Tout index", UInt.of(index).toString());
        transaction.addData("      Unlock length", HexUtils.asString(VarInt.of(unlocking.length).asLitEndBytes()));
        transaction.addData("      Unlock", HexUtils.asString(unlocking));
        transaction.addData("      Sequence", SEQUENCE.toString());
    }

    private byte[] createUnlockRegular(byte[] sigHash) {
        if (wif == null) {
            throw new IllegalStateException(
                    "No WIF provided for input [" + transaction + ", " + index + "]");
        }

        ByteBuffer result = new ByteBuffer();

        PrivateKey privateKey = PrivateKey.ofWif(mainNet, wif);
        result.append(privateKey.sign(sigHash));
        result.append(SigHashType.ALL.asByte());

        result.putFirst(OpSize.ofInt(result.size()).getSize());

        byte[] publicKey = privateKey.getPublicKey();
        result.append(OpSize.ofInt(publicKey.length).getSize());
        result.append(publicKey);

        return result.bytes();
    }

    private byte[] createUnlockSegwit() {
        if (wif == null) {
            throw new IllegalStateException(
                    "No WIF provided for input [" + transaction + ", " + index + "]");
        }

        ByteBuffer result = new ByteBuffer();

        result.append(OpCodes.FALSE);
        result.append((byte) 0x14); //ripemd160 size
        PrivateKey privateKey = PrivateKey.ofWif(mainNet, wif);
        result.append(HashUtils.ripemd160(HashUtils.sha256(privateKey.getPublicKey())));
        result.putFirst(OpSize.ofInt(result.size()).getSize()); //PUSH DATA

        return result.bytes();
    }

    byte[] getWitness(byte[] sigHash) {
        ByteBuffer result = new ByteBuffer();
        if (isSegWit()) {
            PrivateKey privateKey = PrivateKey.ofWif(mainNet, wif);

            result.append((byte) 0x02);

            ByteBuffer sign = new ByteBuffer(privateKey.sign(sigHash));
            sign.append(SigHashType.ALL.asByte());
            result.append(OpSize.ofInt(sign.size()).getSize());
            result.append(sign.bytes());

            byte[] pubKey = privateKey.getPublicKey();
            result.append(OpSize.ofInt(pubKey.length).getSize());
            result.append(pubKey);
        } else {
            result.append((byte) 0x00);
        }

        return result.bytes();
    }

    byte[] getTransactionHashBytesLitEnd() {
        return new ByteBuffer(HexUtils.asBytes(transaction)).bytesReversed();
    }

    long getSatoshi() {
        return satoshi;
    }

    boolean isSegWit() {
        return LockScriptType.forLock(lock).isSegWit();
    }

    int getIndex() {
        return index;
    }

    String getLock() {
        return lock;
    }

    String getWif() {
        return wif;
    }

    UInt getSequence() {
        return SEQUENCE;
    }

    @Override
    public String toString() {
        return new StringBuilder().append(transaction)
                .append(" ").append(index)
                .append(" ").append(lock)
                .append(" ").append(satoshi)
                .append(" ").append(wif)
                .toString();
    }
}
