package sd.fomin.gerbera;

import org.junit.Assert;
import org.junit.Test;
import sd.fomin.gerbera.transaction.Transaction;
import sd.fomin.gerbera.transaction.TransactionBuilder;

public class RawTransactionTest {

    @Test
    public void testFull() {
        String expectedRaw =
                "0100000004033f2e5cc2ad25e4106d0289928ee5bd796380522baeb81de36f47ff3edf2237010000" +
                "008a4730440220080a8e8dbf870a060daad725e79a79700e6bcf7f282d98f837cc94ebdd58111f02" +
                "20348f1fa4834844d442d5f54b7977ea0aa368bb77c12d56bc4d9ccc9071ecd73001410479be667e" +
                "f9dcbbac55a06295ce870b07029bfcdb2dce28d959f2815b16f81798483ada7726a3c4655da4fbfc" +
                "0e1108a8fd17b448a68554199c47d08ffb10d4b8ffffffff7ee6299e47cc5807c81df5a06806c3c3" +
                "cf3aa7b0152189d109056c8cdb1282fb0d0000008b483045022100c3bd4651cb467dc8901445e611" +
                "e17f7c73038507430bb07aea386a80c93cfdc902206f55954c33677fed8335c6b4e651dbbb79a56d" +
                "8ae0b350a3946d0e1486ff5cc401410479be667ef9dcbbac55a06295ce870b07029bfcdb2dce28d9" +
                "59f2815b16f81798483ada7726a3c4655da4fbfc0e1108a8fd17b448a68554199c47d08ffb10d4b8" +
                "ffffffffbe9ad71c5b90601f558e5b10793aea4e07e3cb9a23925eec38ba67a8cbc02d26f5030000" +
                "6b483045022100f6ee8ec9f6ff414fa4d6c1813232d4edf5d90270cf2f792ca56dfd382046896402" +
                "2012cf11bd0a705c7f8c7c046e94bc67a9ba3221aca86f62ec2d6c91cd66f27035012102c6047f94" +
                "41ed7d6d3045406e95c07cd85c778e4b8cef3ca7abac09b95c709ee5ffffffffbae575cd4a87e071" +
                "945a2fc9ffa690abb0ee39c7301ac4a9d37b2b55319e108b394e00006b483045022100d039519426" +
                "7d0241e6b7b189fa847eb6c918ea561f4c5c9e8491b2261c4b8632022043b5731b7d0035b6a9b1f3" +
                "e44f1e27adaa1ec612c51945b6619b2bed204e4abb012102c6047f9441ed7d6d3045406e95c07cd8" +
                "5c778e4b8cef3ca7abac09b95c709ee5ffffffff03a0860100000000001976a914ec7eced2c57ed1" +
                "292bc4eb9bfd13c9f7603bc33888ac14050000000000001976a914c42e7ef92fdb603af844d064fa" +
                "ad95db9bcdfd3d88acb6f00400000000001976a9144747e8746cddb33b0f7f95a90f89f89fb387cb" +
                "b688ac00000000";

        Transaction transaction = TransactionBuilder.create()
                .from(
                        "3722df3eff476fe31db8ae2b52806379bde58e9289026d10e425adc25c2e3f03",
                        1,
                        "76a91491b24bf9f5288532960ac687abb035127b1d28a588ac",
                        20
                )
                .from(
                        "fb8212db8c6c0509d1892115b0a73acfc3c30668a0f51dc80758cc479e29e67e",
                        13,
                        "76a91491b24bf9f5288532960ac687abb035127b1d28a588ac",
                        4040
                )
                .signedWithWif("5HpHagT65TZzG1PH3CSu63k8DbpvD8s5ip4nEB3kEsreAnchuDf")
                .from(
                        "262dc0cba867ba38ec5e92239acbe3074eea3a79105b8e551f60905b1cd79abe",
                        1013,
                        "76a91406afd46bcdfd22ef94ac122aa11f241244a37ecc88ac",
                        60880
                )
                .from(
                        "8b109e31552b7bd3a9c41a30c739eeb0ab90a6ffc92f5a9471e0874acd75e5ba",
                        20025,
                        "76a91406afd46bcdfd22ef94ac122aa11f241244a37ecc88ac",
                        400128
                )
                .signedWithWif("KwDiBf89QgGbjEhKnhXJuH7LrciVrZi3qYjgd9M7rFU74NMTptX4")
                .to("1NZUP3JAc9JkmbvmoTv7nVgZGtyJjirKV1", 100000)
                .to("1JtK9CQw1syfWj1WtFMWomrYdV3W2tWBF9", 1300)
                .withFee(40002)
                .changeTo("17Vu7st1U1KwymUKU4jJheHHGRVNqrcfLD")
                .build();

        Assert.assertEquals(expectedRaw, transaction.getRawTransaction());
    }
}
