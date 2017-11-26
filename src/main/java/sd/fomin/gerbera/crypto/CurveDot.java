package sd.fomin.gerbera.crypto;

import java.math.BigInteger;

import static sd.fomin.gerbera.crypto.Numbers.*;

public class CurveDot {

    private static final BigInteger BI_2 = new BigInteger("2");
    private static final BigInteger BI_3 = new BigInteger("3");
    private static final BigInteger BI_7 = new BigInteger("7");

    public static final CurveDot G = new CurveDot(G_X, G_Y);

    private static final CurveDot[] DOUBLED_DOTS;

    static {
        DOUBLED_DOTS = new CurveDot[256];
        DOUBLED_DOTS[0] = CurveDot.G;
        for (int i = 1; i < 256; i++) {
            DOUBLED_DOTS[i] = DOUBLED_DOTS[i - 1].twice();
        }
    }

    private BigInteger x;
    private BigInteger y;

    private CurveDot(BigInteger x, BigInteger y) {
        if (x == null || y == null) {
            throw new IllegalArgumentException("X and Y coordinates must not be null");
        }
        this.x = x;
        this.y = y;
        checkDotInCurve();
    }

    public static CurveDot ofGTimes(BigInteger times) {
        CurveDot result = null;
        for (int i = 0; i < times.bitLength(); i++) {
            if (times.testBit(i)) {
                result = DOUBLED_DOTS[i].plus(result);
            }
        }

        return result;
    }

    private CurveDot plus(CurveDot other) {
        if (other == null) {
            return this;
        }

        BigInteger lambda;
        if (this.equals(other)) {
            lambda = this.x.pow(2).multiply(BI_3)
                    .multiply(this.y.multiply(BI_2).modInverse(P)).mod(P);
        } else {
            lambda = this.y.subtract(other.y).multiply(this.x.subtract(other.x).modInverse(P)).mod(P);
        }

        BigInteger x = lambda.pow(2).subtract(this.x).subtract(other.x).mod(P);
        BigInteger y = lambda.multiply(this.x.subtract(x)).subtract(this.y).mod(P);

        return new CurveDot(x, y);
    }

    private CurveDot twice() {
        return this.plus(this);
    }

    private void checkDotInCurve() {
        if (!x.pow(3).add(BI_7).mod(P).equals(y.pow(2).mod(P))) {
            throw new IllegalArgumentException("The dot [" + x + ", " + y + "] is not on the curve");
        };
    }

    public BigInteger x() {
        return x;
    }

    public BigInteger y() {
        return y;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        CurveDot dot = (CurveDot) o;

        if (!x.equals(dot.x)) {
            return false;
        }

        return y.equals(dot.y);
    }
}
