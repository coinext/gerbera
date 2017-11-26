package sd.fomin.gerbera.types;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertArrayEquals;

public class UIntTest {

    @Test
    public void testMinValue() {
        UInt value = UInt.of(0);
        assertEquals("00000000", value.toString());
        assertArrayEquals(new byte[] {0, 0, 0, 0}, value.asLitEndBytes());
    }

    @Test
    public void testMaxValue() {
        UInt value = UInt.of(0xFFFFFFFF);
        assertEquals("ffffffff", value.toString());
        assertArrayEquals(
                new byte[] {
                        (byte) 0xFF,
                        (byte) 0xFF,
                        (byte) 0xFF,
                        (byte) 0xFF },
                value.asLitEndBytes());
    }

    @Test
    public void testDiffBytes() {
        UInt value = UInt.of(0x11335577);
        assertEquals("77553311", value.toString());
        assertArrayEquals(
                new byte[] {
                        (byte) 0x77,
                        (byte) 0x55,
                        (byte) 0x33,
                        (byte) 0x11 },
                value.asLitEndBytes());
    }

    @Test(expected = Exception.class)
    public void testAsByte() {
        UInt valid = UInt.of(0x01);
        assertEquals(1, valid.asByte());

        UInt invalid = UInt.of(0x0101);
        invalid.asByte();
    }
}
