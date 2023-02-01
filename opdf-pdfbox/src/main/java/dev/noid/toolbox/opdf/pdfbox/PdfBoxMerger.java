package dev.noid.toolbox.opdf.pdfbox;

import dev.noid.toolbox.opdf.api.DataMerger;
import dev.noid.toolbox.opdf.api.DataSink;
import dev.noid.toolbox.opdf.api.DataSource;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.LinkedList;
import java.util.List;
import org.apache.pdfbox.io.MemoryUsageSetting;
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
    List<InputStream> openedStreams = addAllSources(sources);
    try {
      mergeSources(sink);
    } finally {
      closeAllSilently(openedStreams);
    }
  }

  private List<InputStream> addAllSources(Iterable<? extends DataSource> sources) {
    LinkedList<InputStream> openedStreams = new LinkedList<>();
    for (DataSource source : sources) {
      try {
        InputStream stream = source.getReading();
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
      docMerger.mergeDocuments(memorySettings);
    } catch (Exception cause) {
      throw new IllegalArgumentException("Cannot merge documents", cause);
    }
  }

  private void closeAllSilently(List<InputStream> streams) {
    for (InputStream stream : streams) {
      try {
        stream.close();
      } catch (Exception ignore) {
        // keep closing other streams
      }
    }
  }
}
