package sd.fomin.gerbera.util;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertArrayEquals;

public class ByteBufferTest {

    @Test
    public void testEmpty() {
        ByteBuffer buffer = new ByteBuffer();
        assertEquals(0, buffer.size());
        assertArrayEquals(new byte[] {}, buffer.bytes());
        assertArrayEquals(new byte[] {}, buffer.bytesReversed());
    }

    @Test
    public void testAppend() {
        ByteBuffer buffer = new ByteBuffer();
        buffer.append((byte) 0);
        buffer.append(new byte[] { 1, 2 });
        buffer.append(new byte[] { 3, 4, 5 });
        assertEquals(6, buffer.size());
        assertArrayEquals(new byte[] { 0, 1, 2, 3, 4, 5 }, buffer.bytes());
        assertArrayEquals(new byte[] { 5, 4, 3, 2, 1, 0}, buffer.bytesReversed());
    }

    @Test
    public void testPutFirst() {
        ByteBuffer buffer = new ByteBuffer();
        buffer.putFirst((byte) 0);
        buffer.putFirst(new byte[] { 1, 2 });
        buffer.putFirst(new byte[] { 3, 4, 5 });
        assertEquals(6, buffer.size());
        assertArrayEquals(new byte[] { 3, 4, 5, 1, 2, 0 }, buffer.bytes());
        assertArrayEquals(new byte[] {0, 2, 1, 5, 4, 3}, buffer.bytesReversed());
    }
}
