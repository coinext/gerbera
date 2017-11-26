package sd.fomin.gerbera.types;

public class OpSize {

    private byte size;

    private OpSize(byte size) {
        this.size = size;
    }

    public static OpSize ofByte(byte byteValue) {
        if (byteValue < 1 || byteValue > 0x4b) {
            throw new IllegalArgumentException("Only one byte op size is supported. To be implemented");
        }
        return new OpSize(byteValue);
    }

    public static OpSize ofInt(int intValue) {
        return ofByte((byte) intValue);
    }

    public byte getSize() {
        return size;
    }
}
