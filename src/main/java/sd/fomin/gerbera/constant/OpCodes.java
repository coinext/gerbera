package sd.fomin.gerbera.constant;

public interface OpCodes {

    byte FALSE = (byte) 0x00;

    byte DUP = (byte) 0x76;

    byte HASH160 = (byte) 0xA9;

    byte EQUAL = (byte) 0x87;

    byte EQUALVERIFY = (byte) 0x88;

    byte CHECKSIG = (byte) 0xAC;

}
