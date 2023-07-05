package dev.noid.toolbox.opdf.pdfbox;

import dev.noid.toolbox.opdf.api.DataMerger;
import dev.noid.toolbox.opdf.api.DataSplitter;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

class PdfBoxDataFactoryTest {

  @Test
  void factory_singleton_instance() {
    PdfBoxDataFactory instance1 = PdfBoxDataFactory.getInstance();
    PdfBoxDataFactory instance2 = PdfBoxDataFactory.getInstance();
    assertSame(instance1, instance2);
  }

  @Test
  void default_splitter_type() {
    PdfBoxDataFactory factory = PdfBoxDataFactory.getInstance();
    DataSplitter splitter = factory.getSplitter();
    assertTrue(splitter instanceof PdfBoxSplitter);
  }

  @Test
  void default_merger_type() {
    PdfBoxDataFactory factory = PdfBoxDataFactory.getInstance();
    DataMerger merger = factory.getMerger();
    assertTrue(merger instanceof PdfBoxMerger);
  }
}