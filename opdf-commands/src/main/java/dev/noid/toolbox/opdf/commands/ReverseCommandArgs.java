package dev.noid.toolbox.opdf.commands;

public record ReverseCommandArgs(String text) {

  public static ReverseCommandArgs ofContext() {

    String[] args = Context.ARGS.get();
    return new ReverseCommandArgs(args[0]);
  }
}
