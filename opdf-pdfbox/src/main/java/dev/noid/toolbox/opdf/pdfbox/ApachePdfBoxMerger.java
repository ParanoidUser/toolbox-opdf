package dev.noid.toolbox.opdf.pdfbox;

import dev.noid.toolbox.opdf.spi.PdfMerger;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.apache.pdfbox.io.MemoryUsageSetting;
import org.apache.pdfbox.multipdf.PDFMergerUtility;

public class ApachePdfBoxMerger implements PdfMerger {

  private final PDFMergerUtility docMerger;
  private final MemoryUsageSetting memorySettings;

  public ApachePdfBoxMerger() {
    this(new PDFMergerUtility(), MemoryUsageSetting.setupMainMemoryOnly());
  }

  ApachePdfBoxMerger(PDFMergerUtility docMerger, MemoryUsageSetting memorySettings) {
    this.docMerger = docMerger;
    this.memorySettings = memorySettings;
  }

  @Override
  public void merge(Iterable<InputStream> docSources, OutputStream mergeResult) throws IOException {
    docSources.forEach(docMerger::addSource);
    docMerger.setDestinationStream(mergeResult);
    docMerger.mergeDocuments(memorySettings);
  }
}
