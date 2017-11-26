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

    private byte[] outputTransactionHash;

    private UInt outputIndex;

    private byte[] lockingScript;

    private long satoshi;

    private PrivateKey privateKey;

    Input(String transaction, int outputIndex, String lockingScript, long satoshi) {
        this.outputTransactionHash = new ByteBuffer(HexUtils.asBytes(transaction)).bytesReversed();
        this.outputIndex = UInt.of(outputIndex);
        this.lockingScript = HexUtils.asBytes(lockingScript);
        this.satoshi = satoshi;
        validateLockingScript();
    }

    private void validateLockingScript() {
        if (lockingScript[0] != OpCodes.DUP
                || lockingScript[1] != OpCodes.HASH160
                || lockingScript[lockingScript.length - 2] != OpCodes.EQUALVERIFY
                || lockingScript[lockingScript.length - 1] != OpCodes.CHECKSIG) {
            throw new IllegalArgumentException("Only pay-to-pubkey-hash supported");
        }

        OpSize pubKeyHashSize = OpSize.ofByte(lockingScript[2]);
        if (pubKeyHashSize.getSize() != lockingScript.length - 5) {
            throw new IllegalArgumentException("Incorrect locking script format");
        }
    }

    byte[] serializeForSign(boolean includeLockingScript) {
        ByteBuffer serialized = new ByteBuffer();

        serialized.append(outputTransactionHash);
        serialized.append(outputIndex.asLitEndBytes());

        if (includeLockingScript) {
            serialized.append(VarInt.of(lockingScript.length).asLitEndBytes());
            serialized.append(lockingScript);
        } else {
            serialized.append(VarInt.of(0).asLitEndBytes());
        }

        serialized.append(SEQUENCE.asLitEndBytes());

        return serialized.bytes();
    }

    void setPrivateKey(String wif) {
        privateKey = PrivateKey.ofWif(wif);
    }

    private byte[] unlocking(byte[] signBase) {
        if (privateKey == null) {
            throw new IllegalStateException("No private key for input to sign");
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

    void fillTransaction(byte[] signBase, Transaction transaction) {
        byte[] unlocking = unlocking(signBase);

        transaction.addLine("   Transaction out", HexUtils.asString(outputTransactionHash));
        transaction.addLine("   Tout index", outputIndex.toString());
        transaction.addLine("   Unlock length", HexUtils.asString(VarInt.of(unlocking.length).asLitEndBytes()));
        transaction.addLine("   Unlock", HexUtils.asString(unlocking));
        transaction.addLine("   Sequence", SEQUENCE.toString());
    }

    boolean hasPrivateKey() {
        return privateKey != null;
    }

    long getSatoshi() {
        return satoshi;
    }
}
