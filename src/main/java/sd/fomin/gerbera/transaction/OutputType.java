package sd.fomin.gerbera.transaction;

public enum OutputType {

    CUSTOM("Custom"), DONATE("Donate"), CHANGE("Change");

    private String desc;

    OutputType(String desc) {
        this.desc = desc;
    }

    public String getDesc() {
        return desc;
    }
}
