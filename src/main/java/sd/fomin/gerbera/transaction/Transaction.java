package sd.fomin.gerbera.transaction;

public class Transaction {

    private static final String ALIGN_FORMAT = "%-25s";

    private StringBuilder raw = new StringBuilder();

    private StringBuilder split = new StringBuilder();

    private long fee;

    public String getRawTransaction() {
        return raw.toString();
    }

    public String getSplitTransaction() {
        return split.toString();
    }

    public String getTransactionInfo() {
        StringBuilder info = new StringBuilder();

        int size = raw.length() / 2;
        info
                .append("Size (bytes):")
                .append("\n  " )
                .append(size)
                .append("\n");

        double feePerByte = fee / (double) size;
        info
                .append("Fee (satoshi/byte):")
                .append("\n  " )
                .append(String.format("%.3f", feePerByte))
                .append("\n");

        return info.toString();
    };

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

    void setFee(long fee) {
        this.fee = fee;
    }

    private String aligned(String string) {
        return String.format(ALIGN_FORMAT, string);
    }

}

