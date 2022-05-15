package dev.noid.toolbox.opdf.pdfbox;

import dev.noid.toolbox.opdf.spi.PdfSplitter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.function.Supplier;
import org.apache.pdfbox.io.MemoryUsageSetting;
import org.apache.pdfbox.multipdf.Splitter;
import org.apache.pdfbox.pdmodel.PDDocument;

public class ApachePdfBoxSplitter implements PdfSplitter {

  private final Splitter docSplitter;
  private final MemoryUsageSetting memorySetting;

  public ApachePdfBoxSplitter() {
    this(new Splitter(), MemoryUsageSetting.setupMainMemoryOnly());
  }

  ApachePdfBoxSplitter(Splitter docSplitter, MemoryUsageSetting memorySetting) {
    this.docSplitter = docSplitter;
    this.memorySetting = memorySetting;
  }

  @Override
  public void split(InputStream docSource, Supplier<OutputStream> destSupplier) throws IOException {
    PDDocument document = PDDocument.load(docSource, memorySetting);
    List<PDDocument> pages = docSplitter.split(document);
    saveAllDocPages(pages, destSupplier);
  }

  private void saveAllDocPages(List<PDDocument> pages, Supplier<OutputStream> destSupplier) {
    for (PDDocument page : pages) {
      try (page) {
        saveDocPage(page, destSupplier);
      } catch (IOException cause) {
        // keep saving other pages
      }
    }
  }

  private void saveDocPage(PDDocument page, Supplier<OutputStream> destSupplier)
      throws IOException {
    try (OutputStream destination = destSupplier.get()) {
      page.save(destination);
    }
  }
}
