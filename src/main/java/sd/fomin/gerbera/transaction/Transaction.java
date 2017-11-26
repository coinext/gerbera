package sd.fomin.gerbera.transaction;

public class Transaction {

    private static final String ALIGN_FORMAT = "%-20s";

    private StringBuilder raw = new StringBuilder();

    private StringBuilder split = new StringBuilder();

    public String getRawTransaction() {
        return raw.toString();
    }

    public String getSplitTransaction() {
        return split.toString();
    }

    void addLine(String name, String value) {
        raw.append(value);

        split.append(aligned(name));
        split.append(value);
        split.append("\n");
    }

    void addLine(String name) {
        split.append(name);
        split.append("\n");
    }

    private String aligned(String string) {
        return String.format(ALIGN_FORMAT, string);
    }

}

