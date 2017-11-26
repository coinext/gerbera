package sd.fomin.gerbera.util;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertArrayEquals;

public class HexUtilsTest {

    @Test
    public void testStringEmpty() {
        String string = HexUtils.asString(new byte[] {});
        assertEquals("", string);
    }

    @Test
    public void testString() {
        String string = HexUtils.asString(new byte[] {
                (byte) 0x00, (byte) 0x11, (byte) 0x22, (byte) 0x33,
                (byte) 0x44, (byte) 0x55, (byte) 0x66, (byte) 0x77,
                (byte) 0x88, (byte) 0x99, (byte) 0xAA, (byte) 0xBB,
                (byte) 0xCC, (byte) 0xDD, (byte) 0xEE, (byte) 0xFF
        });
        assertEquals("00112233445566778899aabbccddeeff", string);
    }

    @Test
    public void testStringLeadingZeros() {
        String string = HexUtils.asString(new byte[] { (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x11 });
        assertEquals("00000011", string);
    }

    @Test
    public void testBytesEmpty() {
        byte[] bytes = HexUtils.asBytes("");
        assertArrayEquals(new byte[] { }, bytes);
    }

    @Test
    public void testBytesOddStringLength() {
        byte[] bytes = HexUtils.asBytes("10203");
        assertArrayEquals(new byte[] { (byte) 0x01, (byte) 0x02, (byte) 0x03 }, bytes);
    }

    @Test
    public void testBytes() {
        byte[] bytes = HexUtils.asBytes("00112233445566778899aabbccddeeff");
        assertArrayEquals(
                new byte[] {
                        (byte) 0x00, (byte) 0x11, (byte) 0x22, (byte) 0x33,
                        (byte) 0x44, (byte) 0x55, (byte) 0x66, (byte) 0x77,
                        (byte) 0x88, (byte) 0x99, (byte) 0xAA, (byte) 0xBB,
                        (byte) 0xCC, (byte) 0xDD, (byte) 0xEE, (byte) 0xFF
                },
                bytes);
    }

    @Test
    public void testBytesAlignedExceed() {
        byte[] bytes = HexUtils.asBytesAligned("112233", 2);
        assertArrayEquals(new byte[] { 0x11, 0x22, 0x33 }, bytes);
    }

    @Test
    public void testBytesAlignedAlign() {
        byte[] bytes = HexUtils.asBytesAligned("112233", 5);
        assertArrayEquals(new byte[] { 0x00, 0x00, 0x11, 0x22, 0x33 }, bytes);
    }

    @Test
    public void testBytesAlignedAlignOddStringLength() {
        byte[] bytes = HexUtils.asBytesAligned("10203", 4);
        assertArrayEquals(new byte[] { 0x00, 0x01, 0x02, 0x03 }, bytes);
    }
}
