package sd.fomin.gerbera.types;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertArrayEquals;

public class ULongTest {

    @Test
    public void testMinValue() {
        ULong value = ULong.of(0);
        assertEquals("0000000000000000", value.toString());
        assertArrayEquals(new byte[] {0, 0, 0, 0, 0, 0, 0, 0}, value.asLitEndBytes());
    }

    @Test
    public void testMaxValue() {
        ULong value = ULong.of(0xFFFFFFFFFFFFFFFFL);
        assertEquals("ffffffffffffffff", value.toString());
        assertArrayEquals(
                new byte[] {
                        (byte) 0xFF,
                        (byte) 0xFF,
                        (byte) 0xFF,
                        (byte) 0xFF,
                        (byte) 0xFF,
                        (byte) 0xFF,
                        (byte) 0xFF,
                        (byte) 0xFF },
                value.asLitEndBytes());
    }

    @Test
    public void testDiffBytes() {
        ULong value = ULong.of(0x1122334455667788L);
        assertEquals("8877665544332211", value.toString());
        assertArrayEquals(
                new byte[] {
                        (byte) 0x88,
                        (byte) 0x77,
                        (byte) 0x66,
                        (byte) 0x55,
                        (byte) 0x44,
                        (byte) 0x33,
                        (byte) 0x22,
                        (byte) 0x11 },
                value.asLitEndBytes());
    }
}
