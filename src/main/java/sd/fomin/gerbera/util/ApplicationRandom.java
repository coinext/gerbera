package sd.fomin.gerbera.util;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.security.SecureRandom;
import java.util.Properties;
import java.util.Random;

public class ApplicationRandom {

    private static Random random;

    static {
        reset();
    }

    private ApplicationRandom() { }

    public static Random get() {
        return random;
    }

    public static void reset() {
        Properties properties = new Properties();
        try (InputStream in = new BufferedInputStream(ApplicationRandom.class.getResourceAsStream("/gerbera.properties"))) {
            properties.load(in);
        } catch (Exception e) {
            throw new IllegalStateException("Cannot load gerbera.properties");
        }

        String runtime = properties.getProperty("runtime");
        if (runtime == null || Boolean.valueOf(runtime)) {
            random = new SecureRandom();
        } else {
            random = new Random(7);
        }
    }
}
