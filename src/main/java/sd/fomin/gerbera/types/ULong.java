package sd.fomin.gerbera.types;

import sd.fomin.gerbera.util.HexUtils;

public class ULong {

    private byte[] litEndBytes;

    private ULong(long value) {
        this.litEndBytes = new byte[] {
                (byte) value,
                (byte) (value >> 8),
                (byte) (value >> 16),
                (byte) (value >> 24),
                (byte) (value >> 32),
                (byte) (value >> 40),
                (byte) (value >> 48),
                (byte) (value >> 56),
        };
    }

    public static ULong of(long value) {
        return new ULong(value);
    }

    public byte[] asLitEndBytes() {
        return litEndBytes;
    }

    @Override
    public String toString() {
        return HexUtils.asString(litEndBytes);
    }
}
