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
        transaction.addLine("   Satoshi", ULong.of(satoshi).toString());
        transaction.addLine("   Lock length", VarInt.of(lockingScript.length).toString());
        transaction.addLine("   Lock", HexUtils.asString(lockingScript));
    }

    private byte[] getLockingScript(String destination) {
        byte[] decodedAddress = Base58CheckUtils.decode(destination);
        if (decodedAddress[0] != 0) {
            throw new IllegalArgumentException("Wrong destination address");
        }

        ByteBuffer lockingScript = new ByteBuffer();

        lockingScript.append(OpCodes.DUP, OpCodes.HASH160);
        lockingScript.append(OpSize.ofInt(decodedAddress.length - 1).getSize());
        lockingScript.append(Arrays.copyOfRange(decodedAddress, 1, decodedAddress.length));
        lockingScript.append(OpCodes.EQUALVERIFY, OpCodes.CHECKSIG);

        return lockingScript.bytes();
    }

    long getSatoshi() {
        return satoshi;
    }

    OutputType getType() {
        return type;
    }
}
