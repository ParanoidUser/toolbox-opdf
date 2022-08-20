package dev.noid.toolbox.opdf.pdfbox;

import dev.noid.toolbox.opdf.api.DataMerger;
import dev.noid.toolbox.opdf.api.DataSplitter;
import dev.noid.toolbox.opdf.spi.DataMergerFactory;
import dev.noid.toolbox.opdf.spi.DataSplitterFactory;
import org.apache.pdfbox.io.MemoryUsageSetting;
import org.apache.pdfbox.multipdf.PDFMergerUtility;
import org.apache.pdfbox.multipdf.Splitter;

public class PdfBoxDataFactory implements DataMergerFactory, DataSplitterFactory {

  @Override
  public DataMerger getMerger() {
    PDFMergerUtility pdfMerger = new PDFMergerUtility();
    MemoryUsageSetting inMemory = MemoryUsageSetting.setupMainMemoryOnly();
    return new PdfBoxMerger(pdfMerger, inMemory);
  }

  @Override
  public DataSplitter getSplitter() {
    Splitter pdfSplitter = new Splitter();
    MemoryUsageSetting inMemory = MemoryUsageSetting.setupMainMemoryOnly();
    return new PdfBoxSplitter(pdfSplitter, inMemory);
  }
}
