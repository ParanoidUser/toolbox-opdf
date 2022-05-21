package dev.noid.toolbox.opdf.pdfbox;

import dev.noid.toolbox.opdf.api.DataMerger;
import dev.noid.toolbox.opdf.api.DataSink;
import dev.noid.toolbox.opdf.api.DataSource;
import java.io.InputStream;
import java.util.LinkedList;
import java.util.List;
import org.apache.pdfbox.io.MemoryUsageSetting;
import org.apache.pdfbox.multipdf.PDFMergerUtility;

public class PdfBoxMerger implements DataMerger {

  private final PDFMergerUtility docMerger;
  private final MemoryUsageSetting memorySettings;

  public PdfBoxMerger(PDFMergerUtility docMerger, MemoryUsageSetting memorySettings) {
    this.docMerger = docMerger;
    this.memorySettings = memorySettings;
  }

  @Override
  public void merge(Iterable<DataSource> sources, DataSink sink) {
    var openedStreams = addAllSources(sources);
    try {
      mergeSources(sink);
    } finally {
      closeAllSilently(openedStreams);
    }
  }

  private List<InputStream> addAllSources(Iterable<DataSource> sources) {
    var openedStreams = new LinkedList<InputStream>();
    for (var source : sources) {
      try {
        var stream = source.getReading();
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
    try (var stream = sink.getWriting()) {
      docMerger.setDestinationStream(stream);
      docMerger.mergeDocuments(memorySettings);
    } catch (Exception cause) {
      throw new IllegalArgumentException("Cannot merge documents", cause);
    }
  }

  private void closeAllSilently(List<InputStream> streams) {
    for (var stream : streams) {
      try {
        stream.close();
      } catch (Exception ignore) {
        // keep closing other streams
      }
    }
  }
}
