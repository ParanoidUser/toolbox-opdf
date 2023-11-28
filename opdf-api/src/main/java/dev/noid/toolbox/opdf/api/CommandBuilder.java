package dev.noid.toolbox.opdf.api;

public interface CommandBuilder {

  CommandBuilder command(String...args);

  CommandBuilder input(DataSource source);

  CommandBuilder output(DataSink output);

  Command build();
}
