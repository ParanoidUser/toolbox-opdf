package dev.noid.toolbox.opdf.pdfbox;

import dev.noid.toolbox.opdf.api.DataMerger;
import dev.noid.toolbox.opdf.api.DataSplitter;
import dev.noid.toolbox.opdf.spi.DataFactoryProvider;
import dev.noid.toolbox.opdf.spi.DataMergerFactory;
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
  void default_splitter_factory_type() {
    DataMergerFactory splitterFactory = DataFactoryProvider.getInstance(DataMergerFactory.class);
    assertTrue(splitterFactory instanceof PdfBoxDataFactory);
  }

  @Test
  void default_splitter_type() {
    PdfBoxDataFactory factory = PdfBoxDataFactory.getInstance();
    DataSplitter splitter = factory.getSplitter();
    assertTrue(splitter instanceof PdfBoxSplitter);
  }

  @Test
  void default_merge_factory_type() {
    DataMergerFactory mergerFactory = DataFactoryProvider.getInstance(DataMergerFactory.class);
    assertTrue(mergerFactory instanceof PdfBoxDataFactory);
  }

  @Test
  void default_merger_type() {
    PdfBoxDataFactory factory = PdfBoxDataFactory.getInstance();
    DataMerger merger = factory.getMerger();
    assertTrue(merger instanceof PdfBoxMerger);
  }
}