package dev.noid.toolbox.opdf.io;

import dev.noid.toolbox.opdf.api.DataSource;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;

public class FileSource implements DataSource {

  private final Path filePath;

  public FileSource(Path filePath) {
    this.filePath = filePath;
  }

  @Override
  public InputStream getReading() {
    try {
      return new BufferedInputStream(Files.newInputStream(filePath));
    } catch (IOException cause) {
      throw new IllegalArgumentException("Unable to open file for reading: " + filePath, cause);
    }
  }
}
