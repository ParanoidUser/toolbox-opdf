package dev.noid.toolbox.opdf.pdfbox;

import static org.junit.jupiter.api.Assertions.assertEquals;

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
    var page1 = new TestSource("text-page-1.pdf");
    var page2 = new TestSource("text-page-2.pdf");
    var testSink = new TestSink();

    merger.merge(List.of(page1, page2), testSink);

    assertEquals(1, testSink.getWritingCalls());
    assertEquals(2, PdfBoxUtil.getPageCount(testSink.getBytesWritten(0)));
  }

  @Test
  void merge_result_has_concistent_size() {
    var page1 = new TestSource("text-page-1.pdf");
    var page2 = new TestSource("text-page-2.pdf");
    var testSink = new TestSink();

    merger.merge(List.of(page1, page2), testSink);

    assertEquals(1, testSink.getWritingCalls());
    assertEquals(2138, testSink.getBytesWritten(0).length);
  }

  @Test
  void merge_closes_all_resources() {
    var page1 = new TestSource("text-page-1.pdf");
    var page2 = new TestSource("text-page-2.pdf");
    var testSink = new TestSink();

    merger.merge(List.of(page1, page2), testSink);

    assertEquals(1, page1.getCloseCalls());
    assertEquals(1, page2.getCloseCalls());
    assertEquals(1, testSink.getWritingCalls());
    // closed explicitly by the merger and implicitly by PDF box
    assertEquals(2, testSink.getCloseCalls());
  }
}