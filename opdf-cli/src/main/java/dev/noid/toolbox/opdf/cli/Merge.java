package dev.noid.toolbox.opdf.cli;

import dev.noid.toolbox.opdf.api.DataMerger;
import dev.noid.toolbox.opdf.api.DataSink;
import dev.noid.toolbox.opdf.api.DataSource;
import dev.noid.toolbox.opdf.io.FileSink;
import dev.noid.toolbox.opdf.io.FileSource;
import dev.noid.toolbox.opdf.spi.DataFactoryProvider;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import picocli.CommandLine.Command;
import picocli.CommandLine.Model.CommandSpec;
import picocli.CommandLine.Option;
import picocli.CommandLine.ParameterException;
import picocli.CommandLine.Parameters;
import picocli.CommandLine.Spec;

@Command(
    name = "merge",
    header = "Merge several PDF files into one",
    synopsisHeading = "%nUsage: ",
    description = "%nWhen no output file is given, the merge result will be stored at the same directory."
)
public class Merge implements Runnable {

  private List<Path> sourceFiles;
  private Path outputFile;

  @Spec
  private CommandSpec spec; // injected by picocli

  @Parameters(arity = "1..*", paramLabel = "DIR or FILES", description = "several PDF files (or directory) to merge")
  public void setSourceFiles(List<Path> sourceFiles) {
    for (Path file : sourceFiles) {
      if (!Files.isReadable(file)) {
        throw new ParameterException(spec.commandLine(),
            String.format("Cannot find PDF file: '%s'", file));
      }
    }
    this.sourceFiles = sourceFiles;
  }

  @Option(names = {"-o", "--output"}, description = "output file with merge results")
  public void setOutputFile(Path outputFile) {
    this.outputFile = outputFile;
  }

  @Override
  public void run() {

    if (sourceFiles.size() == 1 && Files.isDirectory(sourceFiles.get(0))) {
      try (Stream<Path> fileStream = Files.list(sourceFiles.get(0))) {
        sourceFiles = fileStream.collect(Collectors.toList());
      } catch (Exception cause) {
        throw new IllegalArgumentException(cause);
      }
    }

    if (outputFile == null) {
      Path firstFile = sourceFiles.get(0);
      String fileName = firstFile.getFileName().toString();
      Path directory = firstFile.getParent();
      outputFile = directory.resolve(fileName + "_merged.pdf");
    }

    List<DataSource> sources = sourceFiles.stream().map(FileSource::new)
        .collect(Collectors.toList());
    DataSink sink = new FileSink(outputFile);

    DataMerger merger = DataFactoryProvider.getMergerFactory().getMerger();
    merger.merge(sources, sink);
  }
}