package sd.fomin.gerbera.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class ByteBuffer {

    private ByteArrayOutputStream buffer = new ByteArrayOutputStream();

    public ByteBuffer(byte... bytes) {
        buffer = new ByteArrayOutputStream();
        append(bytes);
    }

    public void append(byte... bytes) {
        try {
            buffer.write(bytes);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void putFirst(byte... bytes) {
        byte[] current = buffer.toByteArray();
        buffer.reset();
        try {
            buffer.write(bytes);
            buffer.write(current);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public byte[] bytes() {
        return buffer.toByteArray();
    }

    public byte[] bytesReversed() {
        byte[] bytes = bytes();
        byte[] result = new byte[bytes.length];
        for (int i = 0; i < bytes.length; i++) {
            result[i] = bytes[bytes.length - 1 - i];
        }
        return result;
    }

    public int size() {
        return buffer.size();
    }
}
