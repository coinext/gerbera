package sd.fomin.gerbera.boot.processor;

import sd.fomin.gerbera.transaction.TransactionBuilder;

import java.util.Arrays;
import java.util.List;

public class WithFeeProcessor extends Processor {

    @Override
    protected TransactionBuilder doProcess(TransactionBuilder builder, List<String> arguments) {
        long amount;
        try {
            amount = Long.parseLong(arguments.get(0));
        } catch (NumberFormatException e) {
            throw new RuntimeException("Fee must be integer");
        }
        return builder.withFee(amount);
    }

    @Override
    protected List<String> getArgumentDescriptions() {
        return Arrays.asList(
                "fee (satoshi)"
        );
    }
}
