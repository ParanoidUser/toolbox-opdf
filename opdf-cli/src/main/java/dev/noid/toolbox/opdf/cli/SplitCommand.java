package dev.noid.toolbox.opdf.cli;

import dev.noid.toolbox.opdf.api.DataSink;
import dev.noid.toolbox.opdf.api.DataSource;
import dev.noid.toolbox.opdf.api.DataSplitter;
import dev.noid.toolbox.opdf.io.FileSink;
import dev.noid.toolbox.opdf.io.FileSource;
import dev.noid.toolbox.opdf.io.FileUtil;
import dev.noid.toolbox.opdf.io.NamingStrategy;
import dev.noid.toolbox.opdf.spi.DataFactoryProvider;
import dev.noid.toolbox.opdf.spi.DataSplitterFactory;

import java.nio.file.Path;
import java.nio.file.Paths;

class SplitCommand implements Runnable {

  private final Path sourceFile;

  SplitCommand(Path sourceFile) {
    this.sourceFile = sourceFile;
  }

  @Override
  public void run() {
    Path outputDirectory = sourceFile.getParent();
    DataSource source = FileSource.of(sourceFile);
    DataSink sink = getStreamSink(outputDirectory, sourceFile);
    DataSplitter splitter = DataFactoryProvider.getInstance(DataSplitterFactory.class).getSplitter();
    splitter.split(source, sink);
  }

  private DataSink getStreamSink(Path targetDir, Path sourceFile) {
    String nameOnly = FileUtil.getNameWithoutExtension(sourceFile);
    String filePath = targetDir.resolve(nameOnly).toString();
    String extension = FileUtil.getExtension(sourceFile);

    NamingStrategy naming = NamingStrategy.ordinary(filePath, extension);
    return () -> FileSink.of(Paths.get(naming.getName())).getWriting();
  }
}
