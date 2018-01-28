package sd.fomin.gerbera.boot.processor;

import sd.fomin.gerbera.transaction.TransactionBuilder;

import java.util.Arrays;
import java.util.List;

public class FromProcessor extends Processor {

    @Override
    protected TransactionBuilder doProcess(TransactionBuilder builder, List<String> arguments) {
        String tix = arguments.get(0);
        int tout;
        try {
            tout = Integer.parseInt(arguments.get(1));
        } catch (NumberFormatException e) {
            throw new RuntimeException("Output number must be integer");
        }
        String script = arguments.get(2);
        long amount;
        try {
            amount = Long.parseLong(arguments.get(3));
        } catch (NumberFormatException e) {
            throw new RuntimeException("Amount must be integer");
        }
        String wif = arguments.get(4);

        return builder.from(tix, tout, script, amount, wif);
    }

    @Override
    protected List<String> getArgumentDescriptions() {
        return Arrays.asList(
                "unspent transaction id (bit endian)",
                "unspent output number",
                "locking script",
                "unspent amount (satoshi)",
                "WIF"
        );
    }

}
