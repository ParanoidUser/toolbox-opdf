package dev.noid.toolbox.opdf.cli;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Function;

class CommandFactory {

  private final Map<String, Function<String[], Runnable>> registry;

  CommandFactory(Consumer<String> stdout) {
    this.registry = Map.of(
            "help", args -> new HelpCommand(stdout),
            "version", args -> new VersionCommand(stdout),
            "split", args -> new SplitCommand(getSource(args)),
            "merge", args -> new MergeCommand(getSource(args))
    );
  }

  public Runnable getCommand(String... args) {
    String key = args.length > 0 ? args[0] : "help";
    return registry.getOrDefault(key, registry.get("help")).apply(args);
  }

  private Path getSource(String[] args) {
    if (args.length < 2) {
      throw new IllegalArgumentException("Source path is missing");
    }
    return Paths.get(args[1]);
  }
}
