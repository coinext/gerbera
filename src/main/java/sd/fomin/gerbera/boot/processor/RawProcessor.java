package sd.fomin.gerbera.boot.processor;

import sd.fomin.gerbera.transaction.TransactionBuilder;

import java.util.Collections;
import java.util.List;

public class RawProcessor extends Processor {

    @Override
    protected TransactionBuilder doProcess(TransactionBuilder builder, List<String> arguments) {
        System.out.println(builder.build().getRawTransaction());
        return builder;
    }

    @Override
    protected List<String> getArgumentDescriptions() {
        return Collections.emptyList();
    }

}
