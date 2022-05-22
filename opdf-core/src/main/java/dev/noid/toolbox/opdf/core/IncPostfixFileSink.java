package dev.noid.toolbox.opdf.core;

import dev.noid.toolbox.opdf.api.DataSink;
import java.io.OutputStream;
import java.nio.file.Path;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Supplier;

public class IncPostfixFileSink implements DataSink {

  private static final String TEMPLATE = "%s_%s.%s";

  private final Supplier<Path> filePathFactory;

  public IncPostfixFileSink(Path filePath) {
    String filename = FileUtil.getNameWithoutExtension(filePath);
    String extension = FileUtil.getExtension(filePath);
    Path directory = filePath.getParent();
    AtomicInteger sequence = new AtomicInteger();
    filePathFactory = () -> {
      var nextFileName = String.format(TEMPLATE, filename, sequence.getAndIncrement(), extension);
      return directory.resolve(nextFileName);
    };
  }

  @Override
  public OutputStream getWriting() {
    return new FileSink(filePathFactory.get()).getWriting();
  }
}
