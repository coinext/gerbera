package sd.fomin.gerbera.transaction;

import sd.fomin.gerbera.constant.OpCodes;
import sd.fomin.gerbera.types.OpSize;
import sd.fomin.gerbera.util.ByteBuffer;
import sd.fomin.gerbera.types.ULong;
import sd.fomin.gerbera.types.VarInt;
import sd.fomin.gerbera.util.Base58CheckUtils;
import sd.fomin.gerbera.util.HexUtils;

import java.util.Arrays;

class Output {

    private long satoshi;

    private byte[] lockingScript;

    private OutputType type;

    Output(long satoshi, String destination, OutputType type) {
        this.satoshi = satoshi;
        this.lockingScript = getLockingScript(destination);
        this.type = type;
    }

    byte[] serializeForSign() {
        ByteBuffer serialized = new ByteBuffer();

        serialized.append(ULong.of(satoshi).asLitEndBytes());

        serialized.append(VarInt.of(lockingScript.length).asLitEndBytes());
        serialized.append(lockingScript);

        return serialized.bytes();
    }

    void fillTransaction(Transaction transaction) {
        transaction.addLine("   Output (" + type.getDesc() + ")");

        transaction.addLine("      Satoshi", ULong.of(satoshi).toString());
        transaction.addLine("      Lock length", VarInt.of(lockingScript.length).toString());
        transaction.addLine("      Lock", HexUtils.asString(lockingScript));
    }

    private byte[] getLockingScript(String destination) {
        if (!destination.startsWith("1") && !destination.startsWith("3")) {
            throw new IllegalArgumentException("Only destination addresses starting with 1 (P2PKH) or 3 (P2SH) supported.");
        }

        byte[] decodedAddress = Base58CheckUtils.decode(destination);
        byte[] hash = Arrays.copyOfRange(decodedAddress, 1, decodedAddress.length);

        ByteBuffer lockingScript = new ByteBuffer();
        if (decodedAddress[0] == 0) {
            //P2PKH
            lockingScript.append(OpCodes.DUP, OpCodes.HASH160);
            lockingScript.append(OpSize.ofInt(hash.length).getSize());
            lockingScript.append(hash);
            lockingScript.append(OpCodes.EQUALVERIFY, OpCodes.CHECKSIG);
        } else if (decodedAddress[0] == 5) {
            //P2SH
            lockingScript.append(OpCodes.HASH160);
            lockingScript.append(OpSize.ofInt(hash.length).getSize());
            lockingScript.append(hash);
            lockingScript.append(OpCodes.EQUAL);
        } else {
            throw new IllegalStateException("Should never happen");
        }

        return lockingScript.bytes();
    }

    long getSatoshi() {
        return satoshi;
    }
}
