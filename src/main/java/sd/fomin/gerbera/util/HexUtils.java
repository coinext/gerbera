package sd.fomin.gerbera.util;

public class HexUtils {

    private HexUtils() { }

    public static String asString(byte[] bytes) {
        StringBuilder result = new StringBuilder();

        for ( int i = 0; i < bytes.length; i++) {
            int v = bytes[i] & 0xFF;
            result.append(Character.forDigit(v >>> 4, 16));
            result.append(Character.forDigit(v & 0x0F, 16));
        }

        return result.toString();
    }

    public static byte[] asBytes(String string) {
        if (string.length() % 2 == 1) {
            string = "0" + string;
        }

        ByteBuffer result = new ByteBuffer();
        for (int i = 0; i < string.length(); i += 2) {
            result.append((byte) ((Character.digit(string.charAt(i), 16) << 4) + Character.digit(string.charAt(i + 1), 16)));
        }

        return result.bytes();
    }

    public static byte[] asBytesAligned(String string, int alignment) {
        ByteBuffer result = new ByteBuffer(asBytes(string));
        if (result.size() < alignment) {
            result.putFirst(new byte[alignment - result.size()]);
        }
        return result.bytes();
    }
}
