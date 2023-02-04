package dev.noid.toolbox.opdf.cli;

import dev.noid.toolbox.opdf.api.DataMerger;
import dev.noid.toolbox.opdf.api.DataSink;
import dev.noid.toolbox.opdf.api.DataSource;
import dev.noid.toolbox.opdf.api.DataSplitter;
import dev.noid.toolbox.opdf.io.FileSink;
import dev.noid.toolbox.opdf.io.FileSource;
import dev.noid.toolbox.opdf.io.FileUtil;
import dev.noid.toolbox.opdf.io.NamingStrategy;
import dev.noid.toolbox.opdf.spi.DataFactoryProvider;
import dev.noid.toolbox.opdf.spi.DataMergerFactory;
import dev.noid.toolbox.opdf.spi.DataSplitterFactory;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Stream;

public class Opdf {

  public static void main(String[] args) {
    if (args.length == 0) {
      printHelp();
    } else if ("version".equals(args[0])) {
      printVersion();
    } else if ("split".equals(args[0])) {
      splitDocument(Paths.get(args[1]));
    } else if ("merge".equals(args[0])) {
      mergeDocuments(Paths.get(args[1]));
    } else {
      printHelp();
    }
  }

  private static void printHelp() {
    String help = """
        Open PDF Tool
                
        Usage: opdf [COMMAND]
        Commands:
          help     Displays help information about the specified command
          version  Displays version information
          merge    Merge several PDF files into one
          split    Split one PDF file into pages
          """;
    System.out.println(help);
  }

  private static void printVersion() {
    String version = Opdf.class.getPackage().getImplementationVersion();
    System.out.println(version);
  }

  private static void splitDocument(Path sourceFile) {
    Path outputDirectory = sourceFile.getParent();
    DataSource source = new FileSource(sourceFile);
    DataSink sink = getStreamSink(outputDirectory, sourceFile);
    DataSplitter splitter = DataFactoryProvider.getInstance(DataSplitterFactory.class).getSplitter();
    splitter.split(source, sink);
  }

  private static void mergeDocuments(Path sourceDir) {
    if (!Files.isDirectory(sourceDir)) {
      return;
    }

    Path outputFile = sourceDir.resolve("merged.pdf");
    try (Stream<Path> fileStream = Files.list(sourceDir)) {
      List<FileSource> sources = fileStream.map(FileSource::new).toList();
      DataSink sink = new FileSink(outputFile);

      DataMerger merger = DataFactoryProvider.getInstance(DataMergerFactory.class).getMerger();
      merger.merge(sources, sink);
    } catch (Exception cause) {
      throw new IllegalArgumentException(cause);
    }
  }

  private static DataSink getStreamSink(Path targetDir, Path sourceFile) {
    String nameOnly = FileUtil.getNameWithoutExtension(sourceFile);
    String filePath = targetDir.resolve(nameOnly).toString();
    String extension = FileUtil.getExtension(sourceFile);

    NamingStrategy naming = NamingStrategy.ordinary(filePath, extension);
    return () -> new FileSink(Paths.get(naming.getName())).getWriting();
  }
}