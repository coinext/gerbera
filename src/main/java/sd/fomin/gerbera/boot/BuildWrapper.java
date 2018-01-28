package sd.fomin.gerbera.boot;

import sd.fomin.gerbera.boot.processor.*;
import sd.fomin.gerbera.transaction.TransactionBuilder;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class BuildWrapper {

    private static final List<Processor> processors = new LinkedList<Processor>() {{
        add(new InitProcessor());
        add(new FromProcessor());
        add(new ToProcessor());
        add(new ChangeToProcessor());
        add(new WithFeeProcessor());
        add(new DonateProcessor());
        add(new RawProcessor());
        add(new SplitProcessor());
        add(new InfoProcessor());
    }};

    TransactionBuilder builder;

    void processLine(String line) {
        List<String> parts = getParts(line);
        if (parts.isEmpty()) {
            throw new RuntimeException("Empty input line");
        }

        String commandName = parts.get(0).toLowerCase();
        Processor processor = processors.stream()
                .filter(p -> commandName.equalsIgnoreCase(p.getCommandName()))
                .findFirst()
                .orElse(null);
        if (processor == null) {
            throw new RuntimeException("Unknown command");
        }

        List<String> arguments = parts.subList(1, parts.size());
        builder = processor.process(builder, arguments);
    }

    private List<String> getParts(String line) {
        return Arrays.stream(line.split(" "))
                .map(String::trim)
                .filter(s -> !s.isEmpty())
                .collect(Collectors.toList());
    }

    String state() {
        return builder == null ? "EMPTY" : builder.toString();
    }
}
