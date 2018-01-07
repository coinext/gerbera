package sd.fomin.gerbera.transaction;

public class Transaction {

    private static final String ALIGN_FORMAT = "%-25s";

    private StringBuilder raw = new StringBuilder();

    private StringBuilder split = new StringBuilder();

    private long fee;

    private int realSize;

    private int newSize;

    public String getRawTransaction() {
        return raw.toString();
    }

    public String getSplitTransaction() {
        return split.toString();
    }

    public String getTransactionInfo() {
        StringBuilder info = new StringBuilder();

        int size = (int) Math.ceil((newSize * 3 + realSize) / 4.0);
        info
                .append((realSize == newSize ? "" : "Virtual ") + "Size (bytes):")
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

    void addData(String name, String value) {
        addData(name, value, true);
    }

    void addData(String name, String value, boolean countSize) {
        int dataSize = value.length() / 2;
        realSize += dataSize;
        newSize += countSize ? dataSize : 0;

        raw.append(value);

        split.append(aligned(name));
        split.append(value);
        split.append("\n");
    }

    void addHeader(String name) {
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

