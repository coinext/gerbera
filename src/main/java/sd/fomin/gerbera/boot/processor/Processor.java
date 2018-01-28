package sd.fomin.gerbera.boot.processor;

import sd.fomin.gerbera.boot.processor.annotation.BuilderNotRequired;
import sd.fomin.gerbera.transaction.TransactionBuilder;

import java.util.List;

public abstract class Processor {

    public TransactionBuilder process(TransactionBuilder builder, List<String> arguments) {
        validate(builder, arguments);
        return doProcess(builder, arguments);
    }

    private void validate(TransactionBuilder builder, List<String> arguments) {
        if (requiresBuilder() && builder == null) {
            throw new RuntimeException("Initialize new builder first");
        }

        if (arguments.size() < getArgumentDescriptions().size()) {
            StringBuilder error = new StringBuilder();
            error.append("'").append(getCommandName()).append("' command requires ")
                    .append(getArgumentDescriptions().size()).append(" argument(s):");
            for (String desc : getArgumentDescriptions()) {
                error.append("\n   * " + desc);
            }
            throw new RuntimeException(error.toString());
        }
    }

    public String getCommandName() {
        String className = getClass().getSimpleName();
        int endingStart = className.indexOf("Processor");
        return className.substring(0, endingStart).toLowerCase();
    }

    protected abstract TransactionBuilder doProcess(TransactionBuilder builder, List<String> arguments);

    protected abstract List<String> getArgumentDescriptions();

    protected boolean requiresBuilder() {
        return getClass().getAnnotation(BuilderNotRequired.class) == null;
    }

}
