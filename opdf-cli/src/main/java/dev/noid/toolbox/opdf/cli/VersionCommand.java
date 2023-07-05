package dev.noid.toolbox.opdf.cli;

import java.util.Objects;
import java.util.function.Consumer;

class VersionCommand implements Runnable {

  private final Consumer<String> stdout;
  private final String version;

  VersionCommand(Consumer<String> stdout) {
    this.stdout = stdout;

    String implVersion = VersionCommand.class.getPackage().getImplementationVersion();
    this.version = Objects.requireNonNullElse(implVersion, "unknown");
  }

  @Override
  public void run() {
    stdout.accept(version);
  }
}
