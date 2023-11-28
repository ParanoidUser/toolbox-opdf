package dev.noid.toolbox.opdf.spi;

import dev.noid.toolbox.opdf.api.Command;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.ServiceLoader;

public final class CommandProvider {

    public static Iterable<Command> getCommands() {
        Iterator<Command> iterator = ServiceLoader.load(Command.class).iterator();
        if (!iterator.hasNext()) {
            throw new NoClassDefFoundError("Cannot find any implementations of factory class: " + Command.class);
        }
        LinkedList<Command> commands = new LinkedList<>();
        iterator.forEachRemaining(commands::add);
        return commands;
    }

    private CommandProvider() {
        throw new UnsupportedOperationException();
    }
}
