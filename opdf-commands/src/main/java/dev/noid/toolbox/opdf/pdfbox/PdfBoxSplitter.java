package dev.noid.toolbox.opdf.pdfbox;

import dev.noid.toolbox.opdf.api.DataSink;
import dev.noid.toolbox.opdf.api.DataSource;
import dev.noid.toolbox.opdf.api.DataSplitter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import org.apache.pdfbox.io.MemoryUsageSetting;
import org.apache.pdfbox.io.RandomAccessRead;
import org.apache.pdfbox.io.RandomAccessReadBuffer;
import org.apache.pdfbox.io.ScratchFile;
import org.apache.pdfbox.multipdf.Splitter;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.Loader;

public class PdfBoxSplitter implements DataSplitter {

  private final Splitter docSplitter;
  private final MemoryUsageSetting memorySetting;

  PdfBoxSplitter(Splitter docSplitter, MemoryUsageSetting memorySetting) {
    this.docSplitter = docSplitter;
    this.memorySetting = memorySetting;
  }

  @Override
  public void split(DataSource source, DataSink sink) {
    try (InputStream reading = source.getReading();
         RandomAccessRead stream = new RandomAccessReadBuffer(reading);
         PDDocument document = Loader.loadPDF(stream, () -> new ScratchFile(memorySetting))) {
      List<PDDocument> pages = docSplitter.split(document);
      for (PDDocument page : pages) {
        try (page) {
          savePage(page, sink);
        }
      }
    } catch (Exception cause) {
      throw new IllegalArgumentException("Cannot split document", cause);
    }
  }

  private void savePage(PDDocument page, DataSink sink) throws IOException {
    try (OutputStream stream = sink.getWriting()) {
      page.save(stream);
    }
  }
}
