package sd.fomin.gerbera;

import org.junit.Assert;
import org.junit.Test;
import sd.fomin.gerbera.transaction.Transaction;
import sd.fomin.gerbera.transaction.TransactionBuilder;

public class RawTransactionTest {

    @Test
    public void testFull() {
        String expectedRaw =
                "0100000004033f2e5cc2ad25e4106d0289928ee5bd796380522baeb81de36f47" +
                "ff3edf2237010000008b483045022100832f9cba48483a812c1eeccb5e7d80d6" +
                "0bdb1425a19a3d24e18f9a10ba6c1213022020ac8f35cff76d4df19847eb3e5e" +
                "f058ba0ef03ddaf7e05b11cb586637aad61c01410479be667ef9dcbbac55a062" +
                "95ce870b07029bfcdb2dce28d959f2815b16f81798483ada7726a3c4655da4fb" +
                "fc0e1108a8fd17b448a68554199c47d08ffb10d4b8ffffffff7ee6299e47cc58" +
                "07c81df5a06806c3c3cf3aa7b0152189d109056c8cdb1282fb0d0000008b4830" +
                "45022100c3bd4651cb467dc8901445e611e17f7c73038507430bb07aea386a80" +
                "c93cfdc902201473a8a508c85bdc04720678590d04278089d35554babfcec21b" +
                "4989d3638e1b01410479be667ef9dcbbac55a06295ce870b07029bfcdb2dce28" +
                "d959f2815b16f81798483ada7726a3c4655da4fbfc0e1108a8fd17b448a68554" +
                "199c47d08ffb10d4b8ffffffffbe9ad71c5b90601f558e5b10793aea4e07e3cb" +
                "9a23925eec38ba67a8cbc02d26f50300006a473044022030a84f1a9f6a02c4fc" +
                "44943220a3b4eb836790ef4db7a2422365f394437e7a00022067510ba9f503c9" +
                "02f6d058da37e57d989bf36d74c378597121292e9eb36f2454012102c6047f94" +
                "41ed7d6d3045406e95c07cd85c778e4b8cef3ca7abac09b95c709ee5ffffffff" +
                "bae575cd4a87e071945a2fc9ffa690abb0ee39c7301ac4a9d37b2b55319e108b" +
                "394e00006b483045022100977466bf3b963d8919b564c85074eb2719667a1eda" +
                "e9104137dbdc3475a2ddc402204eaf5fbeacda741eb0aedc4e026a0705d55921" +
                "d5a3ee32b9d29a9ac7bd7b7747012102c6047f9441ed7d6d3045406e95c07cd8" +
                "5c778e4b8cef3ca7abac09b95c709ee5ffffffff0450c30000000000001976a9" +
                "14ec7eced2c57ed1292bc4eb9bfd13c9f7603bc33888ac140500000000000019" +
                "76a914c42e7ef92fdb603af844d064faad95db9bcdfd3d88ac50c30000000000" +
                "0017a914000102030405060708090001020304050607080987b6f00400000000" +
                "001976a9144747e8746cddb33b0f7f95a90f89f89fb387cbb688ac00000000";

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
                .to("1NZUP3JAc9JkmbvmoTv7nVgZGtyJjirKV1", 50000)
                .to("1JtK9CQw1syfWj1WtFMWomrYdV3W2tWBF9", 1300)
                .to("31h38a54tFMrR8kvVig3R23ntQMoitzkAf", 50000)
                .withFee(40002)
                .changeTo("17Vu7st1U1KwymUKU4jJheHHGRVNqrcfLD")
                .build();

        Assert.assertEquals(expectedRaw, transaction.getRawTransaction());
    }
}
