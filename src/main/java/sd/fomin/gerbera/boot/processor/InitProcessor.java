package sd.fomin.gerbera.boot.processor;

import sd.fomin.gerbera.boot.processor.annotation.BuilderNotRequired;
import sd.fomin.gerbera.transaction.TransactionBuilder;

import java.util.Arrays;
import java.util.List;

@BuilderNotRequired
public class InitProcessor extends Processor {

    @Override
    public TransactionBuilder doProcess(TransactionBuilder current, List<String> arguments) {

        String network = arguments.get(0);
        if ("mainnet".equalsIgnoreCase(network)) {
            return TransactionBuilder.create(true);
        } else if ("testnet".equalsIgnoreCase(network)) {
            return TransactionBuilder.create(false);
        } else {
            throw new RuntimeException("First argument must be 'mainnet' or 'testnet'");
        }
    }

    @Override
    protected List<String> getArgumentDescriptions() {
        return Arrays.asList(
                "network (mainnet or testnet)"
        );
    }

}
