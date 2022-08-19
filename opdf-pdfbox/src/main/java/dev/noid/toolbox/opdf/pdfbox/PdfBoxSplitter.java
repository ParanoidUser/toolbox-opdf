package dev.noid.toolbox.opdf.pdfbox;

import dev.noid.toolbox.opdf.api.DataSink;
import dev.noid.toolbox.opdf.api.DataSource;
import dev.noid.toolbox.opdf.api.DataSplitter;
import java.util.List;
import org.apache.pdfbox.io.MemoryUsageSetting;
import org.apache.pdfbox.multipdf.Splitter;
import org.apache.pdfbox.pdmodel.PDDocument;

public class PdfBoxSplitter implements DataSplitter {

  private final Splitter docSplitter;
  private final MemoryUsageSetting memorySetting;

  PdfBoxSplitter(Splitter docSplitter, MemoryUsageSetting memorySetting) {
    this.docSplitter = docSplitter;
    this.memorySetting = memorySetting;
  }

  @Override
  public void split(DataSource source, DataSink sink) {
    try (var stream = source.getReading(); var document = PDDocument.load(stream, memorySetting)) {
      List<PDDocument> pages = docSplitter.split(document);
      for (PDDocument page : pages) {
        savePage(page, sink);
      }
    } catch (Exception cause) {
      throw new IllegalArgumentException("Cannot split document", cause);
    }
  }

  private void savePage(PDDocument page, DataSink sink) {
    try (var stream = sink.getWriting(); page) {
      page.save(stream);
    } catch (Exception ignore) {
      // keep saving other pages
    }
  }
}
