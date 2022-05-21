package dev.noid.toolbox.opdf.pdfbox;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.apache.pdfbox.io.MemoryUsageSetting;
import org.apache.pdfbox.multipdf.Splitter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PdfBoxSplitterTest {

  private PdfBoxSplitter splitter;

  @BeforeEach
  void setUp() {
    var pdfSplitter = new Splitter();
    var inMemory = MemoryUsageSetting.setupMainMemoryOnly();
    splitter = new PdfBoxSplitter(pdfSplitter, inMemory);
  }

  @Test
  void split_document_into_two_pages() {
    var twoPages = new TestSource("two-blank-pages.pdf");
    var testSink = new TestSink();

    splitter.split(twoPages, testSink);

    assertEquals(2, testSink.size());
    assertEquals(1, PdfBoxUtil.getPageCount(testSink.getWritingBytes(0)));
    assertEquals(1, PdfBoxUtil.getPageCount(testSink.getWritingBytes(1)));
  }

  @Test
  void split_output_has_constant_size() {
    var twoPages = new TestSource("two-blank-pages.pdf");
    var testSink = new TestSink();

    splitter.split(twoPages, testSink);

    assertEquals(2, testSink.size());
    assertEquals(833, testSink.getWritingBytes(0).length);
    assertEquals(833, testSink.getWritingBytes(1).length);
  }

  @Test
  void split_closes_all_resources() throws Exception {
    var twoPages = new TestSource("two-blank-pages.pdf");
    var testSink = new TestSink();

    splitter.split(twoPages, testSink);

    assertEquals(2, testSink.size());
    verify(twoPages.getReadingRef(), times(1)).close();
    // closed explicitly by the merger and implicitly by PDF box
    verify(testSink.getWritingRef(0), times(2)).close();
    verify(testSink.getWritingRef(1), times(2)).close();
  }
}