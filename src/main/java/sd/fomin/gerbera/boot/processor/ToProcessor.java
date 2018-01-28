package sd.fomin.gerbera.boot.processor;

import sd.fomin.gerbera.transaction.TransactionBuilder;

import java.util.Arrays;
import java.util.List;

public class ToProcessor extends Processor {

    @Override
    protected TransactionBuilder doProcess(TransactionBuilder builder, List<String> arguments) {
        String destination = arguments.get(0);
        long amount;
        try {
            amount = Long.parseLong(arguments.get(1));
        } catch (NumberFormatException e) {
            throw new RuntimeException("Amount must be integer");
        }
        return builder.to(destination, amount);
    }

    @Override
    protected List<String> getArgumentDescriptions() {
        return Arrays.asList(
                "destination address",
                "amount (satoshi)"
        );
    }

}
