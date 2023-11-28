package dev.noid.toolbox.opdf.pdfbox;

import dev.noid.toolbox.opdf.api.DataMerger;
import dev.noid.toolbox.opdf.api.DataSink;
import dev.noid.toolbox.opdf.api.DataSource;
import java.io.Closeable;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.LinkedList;
import java.util.List;
import org.apache.pdfbox.io.MemoryUsageSetting;
import org.apache.pdfbox.io.RandomAccessRead;
import org.apache.pdfbox.io.RandomAccessReadBuffer;
import org.apache.pdfbox.io.ScratchFile;
import org.apache.pdfbox.multipdf.PDFMergerUtility;

public class PdfBoxMerger implements DataMerger {

  private final PDFMergerUtility docMerger;
  private final MemoryUsageSetting memorySettings;

  PdfBoxMerger(PDFMergerUtility docMerger, MemoryUsageSetting memorySettings) {
    this.docMerger = docMerger;
    this.memorySettings = memorySettings;
  }

  @Override
  public void merge(Iterable<? extends DataSource> sources, DataSink sink) {
    List<Closeable> openedStreams = addAllSources(sources);
    try {
      mergeSources(sink);
    } finally {
      closeAllSilently(openedStreams);
    }
  }

  private List<Closeable> addAllSources(Iterable<? extends DataSource> sources) {
    LinkedList<Closeable> openedStreams = new LinkedList<>();
    for (DataSource source : sources) {
      try {
        InputStream reading = source.getReading();
        openedStreams.add(reading);
        RandomAccessRead stream = new RandomAccessReadBuffer(reading);
        openedStreams.add(stream);
        docMerger.addSource(stream);
      } catch (Exception cause) {
        closeAllSilently(openedStreams);
        throw new IllegalArgumentException("Cannot add source for merging", cause);
      }
    }
    return openedStreams;
  }

  private void mergeSources(DataSink sink) {
    try (OutputStream stream = sink.getWriting()) {
      docMerger.setDestinationStream(stream);
      docMerger.mergeDocuments(() -> new ScratchFile(memorySettings));
    } catch (Exception cause) {
      throw new IllegalArgumentException("Cannot merge documents", cause);
    }
  }

  private void closeAllSilently(List<Closeable> streams) {
    for (Closeable stream : streams) {
      try {
        stream.close();
      } catch (Exception ignore) {
        // keep closing other streams
      }
    }
  }
}
