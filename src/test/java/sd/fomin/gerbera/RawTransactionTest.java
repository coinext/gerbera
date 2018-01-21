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
                        20,
                        "5HpHagT65TZzG1PH3CSu63k8DbpvD8s5ip4nEB3kEsreAnchuDf"
                )
                .from(
                        "fb8212db8c6c0509d1892115b0a73acfc3c30668a0f51dc80758cc479e29e67e",
                        13,
                        "76a91491b24bf9f5288532960ac687abb035127b1d28a588ac",
                        4040,
                        "5HpHagT65TZzG1PH3CSu63k8DbpvD8s5ip4nEB3kEsreAnchuDf"
                )
                .from(
                        "262dc0cba867ba38ec5e92239acbe3074eea3a79105b8e551f60905b1cd79abe",
                        1013,
                        "76a91406afd46bcdfd22ef94ac122aa11f241244a37ecc88ac",
                        60880,
                        "KwDiBf89QgGbjEhKnhXJuH7LrciVrZi3qYjgd9M7rFU74NMTptX4"
                )
                .from(
                        "8b109e31552b7bd3a9c41a30c739eeb0ab90a6ffc92f5a9471e0874acd75e5ba",
                        20025,
                        "76a91406afd46bcdfd22ef94ac122aa11f241244a37ecc88ac",
                        400128,
                        "KwDiBf89QgGbjEhKnhXJuH7LrciVrZi3qYjgd9M7rFU74NMTptX4"
                )
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
                        20,
                        "93RmmDH1KBdXpnx4pQqrCJv1h6kKxF3K4FD7eCdXin12SsiVXSX"
                )
                .from(
                        "b95aa6e5713d88cf107fa8ef582252c7f67c25f0f791be88eb85297f3f1ea47f",
                        13,
                        "76a9140af1ae78875d89840db368c013e9938468a493db88ac",
                        4040,
                        "93RmmDH1KBdXpnx4pQqrCJv1h6kKxF3K4FD7eCdXin12SsiVXSX"
                )
                .from(
                        "b95aa6e5713d88cf107fa8ef582252c7f67c25f0f791be88eb85297f3f1ea47f",
                        1013,
                        "76a9141f594f74c37771ef1d3c73317411d84e52ed743188ac",
                        60880,
                        "cViLa9BePFvF3wp3rGEc8v4z3zNEepyChKiUKCLEbPd7NqqtDoA7"
                )
                .from(
                        "7fa41e3f7f2985eb88be91f7f0257cf6c7522258efa87f10cf883d71e5a65ab9",
                        20025,
                        "76a9141f594f74c37771ef1d3c73317411d84e52ed743188ac",
                        400128,
                        "cViLa9BePFvF3wp3rGEc8v4z3zNEepyChKiUKCLEbPd7NqqtDoA7"
                )
                .to("muyUrFwhKH8EG7szasDbL1Ytsug9MvgLA4", 50000)
                .to("mpdiUuo7vfU4hGkb2g9n6GPabHiMK3h7uw", 1300)
                .to("2N8hwP1WmJrFF5QWABn38y63uYLhnJYJYTF", 50000)
                .withFee(40002)
                .changeTo("mwxFsmFviNnxAJngFBovrnvzYP8WMNiogW")
                .build();

        Assert.assertEquals(expectedRaw, transaction.getRawTransaction());
    }

    @Test
    public void testFullSpendSegwitTestnet() {
        String expectedRaw =
                "01000000000106d960c8e1f9d0f8f39016dd254f0b26ec3a0115c0aa2d0f6048" +
                "c06cbb2b90fdba000000008b483045022100c3bd4651cb467dc8901445e611e1" +
                "7f7c73038507430bb07aea386a80c93cfdc9022025af5ac2dad8e2ed47f13ebc" +
                "0b64ff0c526ab9c8e01feaebaeb61ecdf650db8d014104c1029aa08c5e72d092" +
                "28d9bb90ae48888a6955f79ec052753a81dfd049f39bb75f8b7142bf86c237a3" +
                "a5e5892358b5a9c6a393c47a0db5bf48d36859f1a68dc1ffffffff1dfe38509e" +
                "3da5fa35b13fa4c704ee51fed6d2df6538cfafce5de498d2090c5a000000006a" +
                "47304402206e855a62140f4c6fad68bd22a54b54299fc508e01eab1b156b2810" +
                "7dba1cb7e8022062f24996dd43d40bc0b88f2f4f9777eeda1015eb2cdb948595" +
                "09b25fc3a743a2012103c1029aa08c5e72d09228d9bb90ae48888a6955f79ec0" +
                "52753a81dfd049f39bb7ffffffff82cb6eb26722118afac52ba256f8753f44de" +
                "29637bb874fa5d952ebdf952a8cb00000000171600141f594f74c37771ef1d3c" +
                "73317411d84e52ed7431ffffffffd960c8e1f9d0f8f39016dd254f0b26ec3a01" +
                "15c0aa2d0f6048c06cbb2b90fdba010000008b483045022100d0395194267d02" +
                "41e6b7b189fa847eb6c918ea561f4c5c9e8491b2261c4b863202206465907d6b" +
                "6c81fabf731b5f5c78a560736ad5767dd181b33eca7ab97670af900141045cd5" +
                "c37e79fd8c38fa0d6aaec361248df946f26ff1b2db35318bc145e9a69f90e4cc" +
                "eba44c1696a8b82b69ea8edba844d6151415c92971666f13c5ba2991649fffff" +
                "ffff1dfe38509e3da5fa35b13fa4c704ee51fed6d2df6538cfafce5de498d209" +
                "0c5a010000006b483045022100d1e3c09bc1a6d4e8e3e1acc373c4a8f589838f" +
                "80867bedc2d2689e00f3a988c2022003b789d6c9db37c0ba6cd489692dbace33" +
                "5a00f2f3040ca3e7e2dfc139200dca0121035cd5c37e79fd8c38fa0d6aaec361" +
                "248df946f26ff1b2db35318bc145e9a69f90ffffffff82cb6eb26722118afac5" +
                "2ba256f8753f44de29637bb874fa5d952ebdf952a8cb01000000171600146400" +
                "837067ff8bdc6458e1c7f35267a6acb9f97cffffffff04393000000000000019" +
                "76a914ffe45889c9389bc9a436779b72c2ee4c947377c088accd810100000000" +
                "001976a91442e6647d19d3c404662bf6f9d578bb5ae42b008888ac31de0b0000" +
                "00000017a9143cfafc3e72d2e7f4295cafed29117b3db00a3029875932220000" +
                "00000017a91427a0d282da0e95b82e96b9aa657ee4ac16779c82870000024830" +
                "45022100a3fbb3bc030adc2ce168cf6659b597318bc64c79d8e658ff4818bd5f" +
                "fbe466c8022042df295f0b9fec048fd2de74fa69f07f041c261e56369f03ad27" +
                "a586d503b2c6012103c1029aa08c5e72d09228d9bb90ae48888a6955f79ec052" +
                "753a81dfd049f39bb700000247304402200dbb715e3df454fd9290c61b11f5d1" +
                "2e9384e1db07cef84fc975da9181e576c002207a18f1a4160560976d4a226dc1" +
                "1eb43847ffb274a13c3624f565f33f55b3d5680121035cd5c37e79fd8c38fa0d" +
                "6aaec361248df946f26ff1b2db35318bc145e9a69f9000000000";

        Transaction transaction = TransactionBuilder.create(false)
                .from(
                        "bafd902bbb6cc048600f2daac015013aec260b4f25dd1690f3f8d0f9e1c860d9",
                        0,
                        "76a9140af1ae78875d89840db368c013e9938468a493db88ac",
                        10000,
                        "93RmmDH1KBdXpnx4pQqrCJv1h6kKxF3K4FD7eCdXin12SsiVXSX"
                )
                .from(
                        "5a0c09d298e45dceafcf3865dfd2d6fe51ee04c7a43fb135faa53d9e5038fe1d",
                        0,
                        "76a9141f594f74c37771ef1d3c73317411d84e52ed743188ac",
                        100000,
                        "cViLa9BePFvF3wp3rGEc8v4z3zNEepyChKiUKCLEbPd7NqqtDoA7"
                )
                .from(
                        "cba852f9bd2e955dfa74b87b6329de443f75f856a22bc5fa8a112267b26ecb82",
                        0,
                        "a914587852b3fe1a872ebc3eea917df93d08caad19af87",
                        1000000,
                        "cViLa9BePFvF3wp3rGEc8v4z3zNEepyChKiUKCLEbPd7NqqtDoA7"
                )
                .from(
                        "bafd902bbb6cc048600f2daac015013aec260b4f25dd1690f3f8d0f9e1c860d9",
                        1,
                        "76a9149e96150c26d90fb043e3a7bf2690cda1ff6c233388ac",
                        20000,
                        "92TwqzoFQwKqF5AXDTuEJBehyzWUZ8MnQ1rqtNwLziexAKG3aTx"
                )

                .from(
                        "5a0c09d298e45dceafcf3865dfd2d6fe51ee04c7a43fb135faa53d9e5038fe1d",
                        1,
                        "76a9146400837067ff8bdc6458e1c7f35267a6acb9f97c88ac",
                        200000,
                        "cRTw5G78cen7w7P2N1jXgr1RtYq6coUjNanzmmpsFkrWU419KgiS"
                )
                .from(
                        "cba852f9bd2e955dfa74b87b6329de443f75f856a22bc5fa8a112267b26ecb82",
                        1,
                        "a91456c28fde412e818d21c495ddfa675ef93b3ce3a187",
                        2000000,
                        "cRTw5G78cen7w7P2N1jXgr1RtYq6coUjNanzmmpsFkrWU419KgiS"
                )
                .to("n4qz9ie8g7hu3dGSDtBkFGh7y2C9HskrXd", 12345)
                .to("mmcgtfjqG1sMvZjzEupfufSqehgzngcqFo", 98765)
                .to("2MxofCZSNFE9Xo5kmtGYpMH4d4JZsThzfhN", 777777)
                .withFee(200000)
                .changeTo("2MvrkztsxEdgMGbDSGVeGFx3tbE5Gre8ZtU")
                .build();

        Assert.assertEquals(expectedRaw, transaction.getRawTransaction());
    }
}
