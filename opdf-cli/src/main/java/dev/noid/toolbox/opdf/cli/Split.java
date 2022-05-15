package dev.noid.toolbox.opdf.cli;

import dev.noid.toolbox.opdf.api.PdfEngineFactory;
import dev.noid.toolbox.opdf.core.DataSink;
import dev.noid.toolbox.opdf.core.DataSource;
import dev.noid.toolbox.opdf.core.DataTask;
import dev.noid.toolbox.opdf.core.FileSource;
import dev.noid.toolbox.opdf.core.IncPostfixFileSink;
import dev.noid.toolbox.opdf.core.PdfSplitTask;
import dev.noid.toolbox.opdf.spi.PdfSplitter;
import java.nio.file.Files;
import java.nio.file.Path;
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

    PdfSplitter splitter = new PdfEngineFactory().getEngine(PdfSplitter.class);
    DataSource source = new FileSource(sourceFile);
    DataSink sink = new IncPostfixFileSink(outputDirectory.resolve(sourceFile.getFileName()));
    DataTask task = new PdfSplitTask(splitter, source, sink);
    task.execute();
  }
}