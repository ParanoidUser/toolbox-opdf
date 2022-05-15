package dev.noid.toolbox.opdf.core;

import dev.noid.toolbox.opdf.spi.PdfMerger;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class PdfMergeTask implements DataTask {

  private final PdfMerger merger;
  private final Iterable<DataSource> dataSources;
  private final DataSink dataSink;

  public PdfMergeTask(PdfMerger merger, Iterable<DataSource> dataSources, DataSink dataSink) {
    this.merger = merger;
    this.dataSources = dataSources;
    this.dataSink = dataSink;
  }

  @Override
  public void execute() {
    List<InputStream> dataStreams = StreamSupport
        .stream(dataSources.spliterator(), false)
        .map(DataSource::getReading)
        .collect(Collectors.toList());

    try (OutputStream resultStream = dataSink.getWriting()) {
      merger.merge(dataStreams, resultStream);
    } catch (Exception cause) {
      throw new TaskExecutionException("PDF merge task failed", cause);
    }
  }
}
