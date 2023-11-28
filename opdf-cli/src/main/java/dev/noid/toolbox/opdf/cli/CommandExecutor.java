package dev.noid.toolbox.opdf.cli;

import dev.noid.toolbox.opdf.api.Command;

import java.util.Arrays;

public class CommandExecutor {

  private final CommandLookup lookup = new CommandLookup();

  public void execute(String... args) {
    String commandName = args[0];
    String[] commandArgs = Arrays.copyOfRange(args, 1, args.length);
    Context.OUTPUT.set(System.out::println);
    Context.ARGS.set(commandArgs);
    Command command = lookup.lookup(commandName);
    command.execute();
  }
}
