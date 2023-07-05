package dev.noid.toolbox.opdf.io;

import dev.noid.toolbox.opdf.api.DataSink;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;

public class FileSink implements DataSink {

  public static FileSink of(Path filePath) {
    return new FileSink(filePath);
  }

  private final Path filePath;

  private FileSink(Path filePath) {
    this.filePath = filePath;
  }

  @Override
  public OutputStream getWriting() {
    try {
      return new BufferedOutputStream(Files.newOutputStream(filePath));
    } catch (IOException cause) {
      throw new IllegalArgumentException("Unable to open file for writing: " + filePath, cause);
    }
  }
}
