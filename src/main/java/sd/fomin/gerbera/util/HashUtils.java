package sd.fomin.gerbera.util;

import org.spongycastle.crypto.digests.RIPEMD160Digest;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class HashUtils {

    private HashUtils() { }

    public static byte[] sha256(byte[] bytes) {
        try {
            return MessageDigest.getInstance("SHA-256").digest(bytes);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    public static byte[] ripemd160(byte[] bytes) {
        RIPEMD160Digest d = new RIPEMD160Digest();
        d.update (bytes, 0, bytes.length);

        byte[] result = new byte[d.getDigestSize()];
        d.doFinal(result, 0);

        return result;
    }
}
