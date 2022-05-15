package dev.noid.toolbox.opdf.core;

import dev.noid.toolbox.opdf.spi.PdfSplitter;
import java.io.InputStream;

public class PdfSplitTask implements DataTask {

  private final PdfSplitter splitter;
  private final DataSource dataSource;
  private final DataSink dataSink;

  public PdfSplitTask(PdfSplitter splitter, DataSource dataSource, DataSink dataSink) {
    this.splitter = splitter;
    this.dataSource = dataSource;
    this.dataSink = dataSink;
  }

  @Override
  public void execute() {
    try (InputStream dataStream = dataSource.getReading()) {
      splitter.split(dataStream, dataSink::getWriting);
    } catch (Exception cause) {
      throw new TaskExecutionException("PDF split task failed", cause);
    }
  }
}
