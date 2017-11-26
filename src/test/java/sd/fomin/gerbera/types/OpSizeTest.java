package sd.fomin.gerbera.types;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class OpSizeTest {

    @Test
    public void testMinValueInt() {
        OpSize value = OpSize.ofInt(1);
        assertEquals((byte) 0x01, value.getSize());
    }

    @Test
    public void testMaxValueInt() {
        OpSize value = OpSize.ofInt(0x4b);
        assertEquals((byte) 0x4b, value.getSize());
    }

    @Test
    public void testMidValueInt() {
        OpSize value = OpSize.ofInt(20);
        assertEquals((byte) 20, value.getSize());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testLowerValueInt() {
        OpSize.ofInt(0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testHigherValueInt() {
        OpSize.ofInt(0x4c);
    }

    @Test
    public void testMinValueByte() {
        OpSize value = OpSize.ofByte((byte) 1);
        assertEquals((byte) 0x01, value.getSize());
    }

    @Test
    public void testMaxValueByte() {
        OpSize value = OpSize.ofByte((byte) 0x4b);
        assertEquals((byte) 0x4b, value.getSize());
    }

    @Test
    public void testMidValueByte() {
        OpSize value = OpSize.ofByte((byte) 20);
        assertEquals((byte) 20, value.getSize());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testLowerValueByte() {
        OpSize.ofByte((byte) 0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testHigherValueByte() {
        OpSize.ofByte((byte) 0x4c);
    }
}
