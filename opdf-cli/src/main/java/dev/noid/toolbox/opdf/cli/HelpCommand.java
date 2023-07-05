package dev.noid.toolbox.opdf.cli;

import java.util.function.Consumer;

class HelpCommand implements Runnable {

  private static final String HELP = """
          Open PDF Tool
                    
          Usage: opdf [COMMAND]
          Commands:
            help     Displays help information about the specified command
            version  Displays version information
            merge    Merge several PDF files into one
            split    Split one PDF file into pages
          """;

  private final Consumer<String> stdout;

  HelpCommand(Consumer<String> stdout) {
    this.stdout = stdout;
  }

  @Override
  public void run() {
    stdout.accept(HELP);
  }
}
