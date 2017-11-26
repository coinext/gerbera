package sd.fomin.gerbera.util;

import org.junit.Test;

import static org.junit.Assert.assertArrayEquals;

public class Base58CheckUtilsTest {

    @Test
    public void testDecodeEmpty() {
        byte[] decoded = Base58CheckUtils.decode("3QJmnh");
        assertArrayEquals(new byte[] {}, decoded);
    }

    @Test
    public void testOneByteZero() {
        byte[] decoded = Base58CheckUtils.decode("1Wh4bh");
        assertArrayEquals(new byte[] { 0 }, decoded);
    }

    @Test
    public void testOneByteNonZero() {
        byte[] decoded = Base58CheckUtils.decode("BXvDbH");
        assertArrayEquals(new byte[] { 1 }, decoded);
    }

    @Test
    public void testLeadingZero() {
        byte[] decoded = Base58CheckUtils.decode("15sPzhL1ouhNBTAtdT");
        assertArrayEquals(new byte[] { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9 }, decoded);
    }

    @Test
    public void testMultipleLeadingZeros() {
        byte[] decoded = Base58CheckUtils.decode("111kdY9bfye13qL4rbC4");
        assertArrayEquals(new byte[] { 0, 0, 0, 9, 8, 7, 6, 5, 4, 3, 2, 1 }, decoded);
    }
}
