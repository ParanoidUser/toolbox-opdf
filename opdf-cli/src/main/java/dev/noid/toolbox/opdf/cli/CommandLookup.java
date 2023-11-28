package dev.noid.toolbox.opdf.cli;

import dev.noid.toolbox.opdf.annotations.Alias;
import dev.noid.toolbox.opdf.annotations.AnnotationAccessor;
import dev.noid.toolbox.opdf.api.Command;
import dev.noid.toolbox.opdf.spi.CommandProvider;

public class CommandLookup {

    private final AnnotationAccessor accessor = new AnnotationAccessor();

    public Command lookup(String commandName) {
        Iterable<Command> commands = CommandProvider.getCommands();
        for (Command command : commands) {
            String commandAlias = accessor.getAnnotation(command, Alias.class).value();
            if (commandName.equalsIgnoreCase(commandAlias)) {
                return command;
            }
        }
        throw new IllegalArgumentException("No command found for: " + commandName);
    }
}
