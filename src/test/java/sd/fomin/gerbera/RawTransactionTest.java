package sd.fomin.gerbera;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import sd.fomin.gerbera.transaction.Transaction;
import sd.fomin.gerbera.transaction.TransactionBuilder;
import sd.fomin.gerbera.util.ApplicationRandom;

public class RawTransactionTest {

    @Before
    public void resetRandom() {
        ApplicationRandom.reset();
    }

    @Test
    public void testFullMainnet() {
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

    @Test
    public void testFullTestnet() {
        String expectedRaw =
                "01000000047fa41e3f7f2985eb88be91f7f0257cf6c7522258efa87f10cf883d" +
                "71e5a65ab9010000008b483045022100832f9cba48483a812c1eeccb5e7d80d6" +
                "0bdb1425a19a3d24e18f9a10ba6c121302203a7a1b48f4b06fe902068001a034" +
                "6019d7755a13693f5866e15bb65588c6b456014104c1029aa08c5e72d09228d9" +
                "bb90ae48888a6955f79ec052753a81dfd049f39bb75f8b7142bf86c237a3a5e5" +
                "892358b5a9c6a393c47a0db5bf48d36859f1a68dc1ffffffff7fa41e3f7f2985" +
                "eb88be91f7f0257cf6c7522258efa87f10cf883d71e5a65ab90d0000008a4730" +
                "440220080a8e8dbf870a060daad725e79a79700e6bcf7f282d98f837cc94ebdd" +
                "58111f02203c26ca9b488a620b5f4825e8e80f5ae7150e7871300af9f50aeb51" +
                "1bb0a34e4f014104c1029aa08c5e72d09228d9bb90ae48888a6955f79ec05275" +
                "3a81dfd049f39bb75f8b7142bf86c237a3a5e5892358b5a9c6a393c47a0db5bf" +
                "48d36859f1a68dc1ffffffff7fa41e3f7f2985eb88be91f7f0257cf6c7522258" +
                "efa87f10cf883d71e5a65ab9f50300006a473044022030a84f1a9f6a02c4fc44" +
                "943220a3b4eb836790ef4db7a2422365f394437e7a00022067e92d9bbf9efe42" +
                "f246cdb103dac5b086aed85be181e4883ed2c76e97e87501012103c1029aa08c" +
                "5e72d09228d9bb90ae48888a6955f79ec052753a81dfd049f39bb7ffffffffb9" +
                "5aa6e5713d88cf107fa8ef582252c7f67c25f0f791be88eb85297f3f1ea47f39" +
                "4e00006b483045022100f6ee8ec9f6ff414fa4d6c1813232d4edf5d90270cf2f" +
                "792ca56dfd3820468964022028f699410a0a28a1ce9456f342c1dd7e80768669" +
                "40bd5ccaf37361b3edf3c89d012103c1029aa08c5e72d09228d9bb90ae48888a" +
                "6955f79ec052753a81dfd049f39bb7ffffffff0450c30000000000001976a914" +
                "9e96150c26d90fb043e3a7bf2690cda1ff6c233388ac14050000000000001976" +
                "a9146400837067ff8bdc6458e1c7f35267a6acb9f97c88ac50c3000000000000" +
                "17a914a9974100aeee974a20cda9a2f545704a0ab54fdc87b6f0040000000000" +
                "1976a914b44b166f47b2a24712cc27fab9518686d32777fc88ac00000000";

        Transaction transaction = TransactionBuilder.create(false)
                .from(
                        "b95aa6e5713d88cf107fa8ef582252c7f67c25f0f791be88eb85297f3f1ea47f",
                        1,
                        "76a9140af1ae78875d89840db368c013e9938468a493db88ac",
                        20
                )
                .from(
                        "b95aa6e5713d88cf107fa8ef582252c7f67c25f0f791be88eb85297f3f1ea47f",
                        13,
                        "76a9140af1ae78875d89840db368c013e9938468a493db88ac",
                        4040
                )
                .signedWithWif("93RmmDH1KBdXpnx4pQqrCJv1h6kKxF3K4FD7eCdXin12SsiVXSX")
                .from(
                        "b95aa6e5713d88cf107fa8ef582252c7f67c25f0f791be88eb85297f3f1ea47f",
                        1013,
                        "76a9141f594f74c37771ef1d3c73317411d84e52ed743188ac",
                        60880
                )
                .from(
                        "7fa41e3f7f2985eb88be91f7f0257cf6c7522258efa87f10cf883d71e5a65ab9",
                        20025,
                        "76a9141f594f74c37771ef1d3c73317411d84e52ed743188ac",
                        400128
                )
                .signedWithWif("cViLa9BePFvF3wp3rGEc8v4z3zNEepyChKiUKCLEbPd7NqqtDoA7")
                .to("muyUrFwhKH8EG7szasDbL1Ytsug9MvgLA4", 50000)
                .to("mpdiUuo7vfU4hGkb2g9n6GPabHiMK3h7uw", 1300)
                .to("2N8hwP1WmJrFF5QWABn38y63uYLhnJYJYTF", 50000)
                .withFee(40002)
                .changeTo("mwxFsmFviNnxAJngFBovrnvzYP8WMNiogW")
                .build();

        Assert.assertEquals(expectedRaw, transaction.getRawTransaction());
    }
}
