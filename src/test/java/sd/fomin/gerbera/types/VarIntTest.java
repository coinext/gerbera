package sd.fomin.gerbera.types;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertArrayEquals;

public class VarIntTest {

    @Test
    public void testOneByteVarInt() {
        VarInt varInt0 = VarInt.of(0L);
        assertEquals("00", varInt0.toString());
        assertArrayEquals(new byte[] { 0 }, varInt0.asLitEndBytes());

        VarInt varInt1 = VarInt.of(1L);
        assertEquals("01", varInt1.toString());
        assertArrayEquals(new byte[] { 1 }, varInt1.asLitEndBytes());

        VarInt varInt100 = VarInt.of(0x64L);
        assertEquals("64", varInt100.toString());
        assertArrayEquals(new byte[] { 0x64 }, varInt100.asLitEndBytes());

        VarInt varIntMax = VarInt.of(0xFCL);
        assertEquals("fc", varIntMax.toString());
        assertArrayEquals(new byte[] { (byte) 0xFC }, varIntMax.asLitEndBytes());
    }

    @Test
    public void testThreeByteVarInt() {
        VarInt varIntMin = VarInt.of(0xFDL);
        assertEquals("fdfd00", varIntMin.toString());
        assertArrayEquals(new byte[] { (byte) 0xFD, (byte) 0xFD, (byte) 0x00 }, varIntMin.asLitEndBytes());

        VarInt varInt1000 = VarInt.of(0x3E8L);
        assertEquals("fde803", varInt1000.toString());
        assertArrayEquals(new byte[] { (byte) 0xFD, (byte) 0xE8, (byte) 0x03 }, varInt1000.asLitEndBytes());

        VarInt varIntMax = VarInt.of(0xFFFFL);
        assertEquals("fdffff", varIntMax.toString());
        assertArrayEquals(new byte[] { (byte) 0xFD, (byte) 0xFF, (byte) 0xFF }, varIntMax.asLitEndBytes());
    }

    @Test
    public void testFiveByteVarInt() {
        VarInt varIntMin = VarInt.of(0x10000L);
        assertEquals("fe00000100", varIntMin.toString());
        assertArrayEquals(
                new byte[] { (byte) 0xFE, (byte) 0x00, (byte) 0x00, (byte) 0x01, (byte) 0x00 },
                varIntMin.asLitEndBytes()
        );

        VarInt varIntAllBytes = VarInt.of(0xAABBCCDDL);
        assertEquals("feddccbbaa", varIntAllBytes.toString());
        assertArrayEquals(
                new byte[] { (byte) 0xFE, (byte) 0xDD, (byte) 0xCC, (byte) 0xBB, (byte) 0xAA },
                varIntAllBytes.asLitEndBytes()
        );

        VarInt varIntMax = VarInt.of(0xFFFFFFFFL);
        assertEquals("feffffffff", varIntMax.toString());
        assertArrayEquals(
                new byte[] { (byte) 0xFE, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF },
                varIntMax.asLitEndBytes()
        );
    }

    @Test
    public void testNineByteVarInt() {
        VarInt varIntMin = VarInt.of(0x100000000L);
        assertEquals("ff0000000001000000", varIntMin.toString());
        assertArrayEquals(
                new byte[] {
                        (byte) 0xFF,
                        (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00,
                        (byte) 0x01, (byte) 0x00, (byte) 0x00, (byte) 0x00
                },
                varIntMin.asLitEndBytes()
        );

        VarInt varIntAllBytes = VarInt.of(0x1122334455667788L);
        assertEquals("ff8877665544332211", varIntAllBytes.toString());
        assertArrayEquals(
                new byte[] {
                        (byte) 0xFF,
                        (byte) 0x88, (byte) 0x77, (byte) 0x66, (byte) 0x55,
                        (byte) 0x44, (byte) 0x33, (byte) 0x22, (byte) 0x11
                },
                varIntAllBytes.asLitEndBytes()
        );

        VarInt varIntMax = VarInt.of(0xFFFFFFFFFFFFFFFFL);
        assertEquals("ffffffffffffffffff", varIntMax.toString());
        assertArrayEquals(
                new byte[] {
                        (byte) 0xFF,
                        (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF,
                        (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF
                },
                varIntMax.asLitEndBytes()
        );
    }
}
