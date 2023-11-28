package dev.noid.toolbox.opdf.commands;

import dev.noid.toolbox.opdf.annotations.Alias;
import dev.noid.toolbox.opdf.api.Command;
import dev.noid.toolbox.opdf.api.DataSink;
import dev.noid.toolbox.opdf.spi.ServiceProvider;
import java.nio.charset.StandardCharsets;

@Alias("reverse")
class ReverseCommand implements Command {

  private final ReverseCommandArgs args;
  private final DataSink output = ServiceProvider.findFirst(DataSink.class);

  public ReverseCommand() {
    this(ReverseCommandArgs.ofContext());
  }

  ReverseCommand(ReverseCommandArgs args) {
    this.args = args;
  }

  @Override
  public void execute() {
    String reversed = new StringBuilder(args.text()).reverse().toString();
    saveResult(reversed);
  }

  private void saveResult(String reversed) {
    try (var output = this.output.getWriting()) {
      output.write(reversed.getBytes(StandardCharsets.UTF_8));
    } catch (Exception cause) {
      throw new ExecutionFailed("Command execution failed: " + args, cause);
    }
  }
}
