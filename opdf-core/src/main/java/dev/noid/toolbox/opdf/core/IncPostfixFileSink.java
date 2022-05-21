package dev.noid.toolbox.opdf.core;

import dev.noid.toolbox.opdf.api.DataSink;
import java.io.OutputStream;
import java.nio.file.Path;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Supplier;

public class IncPostfixFileSink implements DataSink {

  private final Supplier<Path> filePathFactory;

  public IncPostfixFileSink(Path filePath) {
    Path directory = filePath.getParent();
    String fullFileName = filePath.getFileName().toString();

    int extensionAt = fullFileName.lastIndexOf('.');
    if (extensionAt < 1) {
      throw new IllegalArgumentException("File name cannot start with extension: " + filePath);
    }

    String name = fullFileName.substring(0, extensionAt);
    String extension = fullFileName.substring(extensionAt);
    AtomicInteger sequence = new AtomicInteger();

    filePathFactory = () -> directory.resolve(name + "_" + sequence.getAndIncrement() + extension);
  }

  @Override
  public OutputStream getWriting() {
    return new FileSink(filePathFactory.get()).getWriting();
  }
}
