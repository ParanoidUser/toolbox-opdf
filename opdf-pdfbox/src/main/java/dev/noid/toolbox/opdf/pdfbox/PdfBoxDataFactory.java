package dev.noid.toolbox.opdf.pdfbox;

import dev.noid.toolbox.opdf.api.DataMerger;
import dev.noid.toolbox.opdf.api.DataSplitter;
import dev.noid.toolbox.opdf.spi.DataMergerFactory;
import dev.noid.toolbox.opdf.spi.DataSplitterFactory;
import org.apache.pdfbox.io.MemoryUsageSetting;
import org.apache.pdfbox.multipdf.PDFMergerUtility;
import org.apache.pdfbox.multipdf.Splitter;

import static org.apache.pdfbox.io.MemoryUsageSetting.setupMixed;

public final class PdfBoxDataFactory implements DataMergerFactory, DataSplitterFactory {

  private static final long MEMORY_BUFFER_SIZE = Long.getLong("inMemoryBufferSize", 100 * 1024 * 1024L);
  private static final PdfBoxDataFactory INSTANCE = new PdfBoxDataFactory(setupMixed(MEMORY_BUFFER_SIZE));

  public static PdfBoxDataFactory getInstance() {
    return INSTANCE;
  }

  private final MemoryUsageSetting memorySetting;

  PdfBoxDataFactory(MemoryUsageSetting memorySetting) {
    this.memorySetting = memorySetting;
  }

  @Override
  public DataMerger getMerger() {
    PDFMergerUtility pdfMerger = new PDFMergerUtility();
    return new PdfBoxMerger(pdfMerger, memorySetting);
  }

  @Override
  public DataSplitter getSplitter() {
    Splitter pdfSplitter = new Splitter();
    return new PdfBoxSplitter(pdfSplitter, memorySetting);
  }
}
