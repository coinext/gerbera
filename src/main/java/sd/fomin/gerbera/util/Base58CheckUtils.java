package sd.fomin.gerbera.util;

import java.math.BigInteger;
import java.util.Arrays;

public class Base58CheckUtils {

    private static final String ALPHABET = "123456789ABCDEFGHJKLMNPQRSTUVWXYZabcdefghijkmnopqrstuvwxyz";

    private Base58CheckUtils() { }

    public static byte[] decode(String base58) {
        StringBuilder encoded = new StringBuilder(base58).reverse();

        ByteBuffer decoded = new ByteBuffer();
        while (encoded.charAt(encoded.length() - 1) == ALPHABET.charAt(0)) {
            decoded.append((byte) 0x00);
            encoded.deleteCharAt(encoded.length() - 1);
        }

        BigInteger number = new BigInteger("0");
        while (encoded.length() > 1) {
            number = number.add(
                    new BigInteger(Integer.toString(ALPHABET.indexOf(encoded.charAt(encoded.length() - 1))))
            ).multiply(new BigInteger("58"));
            encoded.deleteCharAt(encoded.length() - 1);
        }
        number = number.add(new BigInteger(Integer.toString(ALPHABET.indexOf(encoded.charAt(encoded.length() - 1)))));

        byte[] numAsBytes = HexUtils.asBytes(number.toString(16));
        decoded.append(numAsBytes);

        byte[] payload = Arrays.copyOfRange(decoded.bytes(), 0, decoded.size() - 4);
        byte[] checksum = Arrays.copyOfRange(decoded.bytes(), decoded.size() - 4, decoded.size());

        byte[] shaOfSha = HashUtils.sha256(HashUtils.sha256(payload));

        for (int i = 0; i < 4; i++) {
            if (shaOfSha[i] != checksum[i]) {
                throw new IllegalArgumentException("Wrong checksum");
            }
        }

        return payload;
    }
}
