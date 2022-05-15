package dev.noid.toolbox.opdf.cli;

import picocli.CommandLine;
import picocli.CommandLine.Command;

@Command(
    name = "opdf",
    header = "Open PDF Tool",
    synopsisHeading = "%nUsage: ",
    subcommands = {
        CommandLine.HelpCommand.class,
        Version.class,
        Merge.class,
        Split.class
    }
)
public class Opdf {

  public static void main(String[] args) {
    int exitCode = new CommandLine(Opdf.class).execute(args);
    System.exit(exitCode);
  }
}