package dev.noid.toolbox.opdf.cli;

import dev.noid.toolbox.opdf.api.DataSink;
import dev.noid.toolbox.opdf.api.DataSource;
import dev.noid.toolbox.opdf.api.DataSplitter;
import dev.noid.toolbox.opdf.io.FileUtil;
import dev.noid.toolbox.opdf.io.NamingStrategy;
import dev.noid.toolbox.opdf.io.FileSink;
import dev.noid.toolbox.opdf.io.FileSource;
import dev.noid.toolbox.opdf.spi.DataFactoryProvider;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import picocli.CommandLine.Command;
import picocli.CommandLine.Model.CommandSpec;
import picocli.CommandLine.Option;
import picocli.CommandLine.ParameterException;
import picocli.CommandLine.Parameters;
import picocli.CommandLine.Spec;

@Command(
    name = "split",
    header = "Split one PDF file into pages",
    synopsisHeading = "%nUsage: ",
    description = "%nWhen no output directory is given, the split results will be stored at the same directory."
)
public class Split implements Runnable {

  private Path sourceFile;
  private Path outputDirectory;

  @Spec
  private CommandSpec spec; // injected by picocli

  @Parameters(
      paramLabel = "FILE",
      description = "a PDF file to split"
  )
  public void setSourceFile(Path sourceFile) {
    if (!Files.isRegularFile(sourceFile)) {
      throw new ParameterException(spec.commandLine(),
          String.format("Cannot find PDF file: '%s'", sourceFile));
    }
    this.sourceFile = sourceFile;
  }

  @Option(
      names = {"-o", "--output"},
      description = "output directory"
  )
  public void setOutputDirectory(Path outputDirectory) {
    this.outputDirectory = outputDirectory;
  }

  @Override
  public void run() {
    if (outputDirectory == null) {
      outputDirectory = sourceFile.getParent();
    }

    DataSource source = new FileSource(sourceFile);
    DataSink sink = getStreamSink(outputDirectory, sourceFile);
    DataSplitter splitter = DataFactoryProvider.getSplitterFactory().getSplitter();
    splitter.split(source, sink);
  }

  private DataSink getStreamSink(Path targetDir, Path sourceFile) {
    String nameOnly = FileUtil.getNameWithoutExtension(sourceFile);
    String filePath = targetDir.resolve(nameOnly).toString();
    String extension = FileUtil.getExtension(sourceFile);

    NamingStrategy naming = NamingStrategy.ordinary(filePath, extension);
    return () -> new FileSink(Paths.get(naming.getName())).getWriting();
  }
}