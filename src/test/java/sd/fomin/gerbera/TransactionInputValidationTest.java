package sd.fomin.gerbera;

import org.junit.Test;
import sd.fomin.gerbera.transaction.TransactionBuilder;

public class TransactionInputValidationTest {

    @Test(expected = IllegalArgumentException.class)
    public void testNullTransaction() {
        TransactionBuilder.create()
                .from(
                        null,
                        1,
                        "76a914000000000000000000000000000000000000000088ac",
                        1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testInvalidIndex() {
        TransactionBuilder.create()
                .from(
                        "0000000000000000000000000000000000000000000000000000000000000000",
                        -1,
                        "76a914000000000000000000000000000000000000000088ac",
                        1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNullScript() {
        TransactionBuilder.create()
                .from(
                        "0000000000000000000000000000000000000000000000000000000000000000",
                        1,
                        null,
                        1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testZeroSatochi() {
        TransactionBuilder.create()
                .from(
                        "0000000000000000000000000000000000000000000000000000000000000000",
                        1,
                        "76a914000000000000000000000000000000000000000088ac",
                        0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNegativeSatochi() {
        TransactionBuilder.create()
                .from(
                        "0000000000000000000000000000000000000000000000000000000000000000",
                        1,
                        "76a914000000000000000000000000000000000000000088ac",
                        -1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testIncorrectScriptFormatOpDup() {
        TransactionBuilder.create()
                .from(
                        "0000000000000000000000000000000000000000000000000000000000000000",
                        1,
                        "75a914000000000000000000000000000000000000000088ac",
                        1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testIncorrectScriptFormatOpHash() {
        TransactionBuilder.create()
                .from(
                        "0000000000000000000000000000000000000000000000000000000000000000",
                        1,
                        "76a814000000000000000000000000000000000000000088ac",
                        1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testIncorrectScriptFormatOpEqual() {
        TransactionBuilder.create()
                .from(
                        "0000000000000000000000000000000000000000000000000000000000000000",
                        1,
                        "76a914000000000000000000000000000000000000000087ac",
                        1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testIncorrectScriptFormatOpChecksig() {
        TransactionBuilder.create()
                .from(
                        "0000000000000000000000000000000000000000000000000000000000000000",
                        1,
                        "76a914000000000000000000000000000000000000000088ad",
                        1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testIncorrectScriptPKHSize() {
        TransactionBuilder.create()
                .from(
                        "0000000000000000000000000000000000000000000000000000000000000000",
                        1,
                        "76a913000000000000000000000000000000000000000088ac",
                        1);
    }

}
