package dev.noid.toolbox.opdf.cli;

import dev.noid.toolbox.opdf.api.DataMerger;
import dev.noid.toolbox.opdf.api.DataSink;
import dev.noid.toolbox.opdf.io.FileSink;
import dev.noid.toolbox.opdf.io.FileSource;
import dev.noid.toolbox.opdf.spi.DataFactoryProvider;
import dev.noid.toolbox.opdf.spi.DataMergerFactory;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Stream;

class MergeCommand implements Runnable {

  private final Path sourceDir;

  MergeCommand(Path sourceDir) {
    this.sourceDir = sourceDir;
  }

  @Override
  public void run() {
    Path outputFile = sourceDir.resolve("merged.pdf");
    try (Stream<Path> fileStream = Files.list(sourceDir).sorted()) {
      List<FileSource> sources = fileStream.map(FileSource::of).toList();
      DataSink sink = FileSink.of(outputFile);

      DataMerger merger = DataFactoryProvider.getInstance(DataMergerFactory.class).getMerger();
      merger.merge(sources, sink);
    } catch (Exception cause) {
      throw new IllegalArgumentException(cause);
    }
  }
}
