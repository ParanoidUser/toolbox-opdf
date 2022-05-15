package dev.noid.toolbox.opdf.cli;

import picocli.CommandLine.Command;
import picocli.CommandLine.Model.CommandSpec;
import picocli.CommandLine.Spec;

@Command(
    name = "version",
    header = "Displays version information",
    synopsisHeading = "%nUsage: ",
    description = "%nDisplays version information."
)
public class Version implements Runnable {

  private final String implementationVersion = Version.class.getPackage().getImplementationVersion();

  @Spec
  private CommandSpec spec; // injected by picocli

  @Override
  public void run() {
    System.out.println(implementationVersion);
  }
}