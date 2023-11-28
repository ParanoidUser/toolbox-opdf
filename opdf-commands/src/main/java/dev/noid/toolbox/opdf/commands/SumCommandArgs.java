package dev.noid.toolbox.opdf.commands;

public record SumCommandArgs(String text) {

  public static SumCommandArgs ofContext() {

    String[] args = Context.ARGS.get();
    return new SumCommandArgs(args[0]);
  }
}
