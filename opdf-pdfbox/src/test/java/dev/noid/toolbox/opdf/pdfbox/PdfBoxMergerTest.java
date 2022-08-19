package dev.noid.toolbox.opdf.pdfbox;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.List;
import org.apache.pdfbox.io.MemoryUsageSetting;
import org.apache.pdfbox.multipdf.PDFMergerUtility;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PdfBoxMergerTest {

  private PdfBoxMerger merger;

  @BeforeEach
  void setUp() {
    var pdfMerger = new PDFMergerUtility();
    var inMemory = MemoryUsageSetting.setupMainMemoryOnly();
    merger = new PdfBoxMerger(pdfMerger, inMemory);
  }

  @Test
  void merge_two_document_copies_into_one() {
    var onePage = new TestSource("one-blank-page.pdf");
    var testSink = new TestSink();

    merger.merge(List.of(onePage, onePage), testSink);

    assertEquals(1, testSink.countWritings());
    assertEquals(2, PdfBoxUtil.getPageCount(testSink.getWritingBytes(0)));
  }

  @Test
  void merge_result_has_concistent_size() {
    var onePage = new TestSource("one-blank-page.pdf");
    var testSink = new TestSink();

    merger.merge(List.of(onePage, onePage), testSink);

    assertEquals(1, testSink.countWritings());
    assertEquals(1291, testSink.getWritingBytes(0).length);
  }

  @Test
  void merge_closes_all_resources() throws Exception {
    var onePage = new TestSource("one-blank-page.pdf");
    var testSink = new TestSink();

    merger.merge(List.of(onePage, onePage), testSink);

    assertEquals(1, testSink.countWritings());
    verify(onePage.getReadingRef(), times(1)).close();
    // closed explicitly by the merger and implicitly by PDF box
    verify(testSink.getWritingRef(0), times(2)).close();
  }
}