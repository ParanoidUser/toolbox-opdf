package dev.noid.toolbox.opdf.pdfbox;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.List;
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
    var twoPages = new TestSource("two-text-pages.pdf");
    var testSink = new TestSink();

    splitter.split(twoPages, testSink);

    assertEquals(2, testSink.countWritings());
    assertEquals(1, PdfBoxUtil.getPageCount(testSink.getWritingBytes(0)));
    assertEquals(1, PdfBoxUtil.getPageCount(testSink.getWritingBytes(1)));
  }

  @Test
  void split_result_has_concistent_size() {
    var twoPages = new TestSource("two-text-pages.pdf");
    var testSink = new TestSink();

    splitter.split(twoPages, testSink);

    assertEquals(2, testSink.countWritings());
    assertEquals(1245, testSink.getWritingBytes(0).length);
    assertEquals(1208, testSink.getWritingBytes(1).length);
  }

  @Test
  void split_closes_all_resources() throws Exception {
    var twoPages = new TestSource("two-text-pages.pdf");
    var testSink = new TestSink();

    splitter.split(twoPages, testSink);

    assertEquals(2, testSink.countWritings());
    verify(twoPages.getReadingRef(), times(1)).close();
    // closed explicitly by the merger and implicitly by PDF box
    verify(testSink.getWritingRef(0), times(2)).close();
    verify(testSink.getWritingRef(1), times(2)).close();
  }

  @Test
  void split_complex_document() {
    var multiPage = new TestSource("wfrw-ii-figures-and-tables.pdf");
    var testSink = new TestSink();

    splitter.split(multiPage, testSink);

    var knownPageSizes = List.of(
        68643, 96934, 50784, 141184, 107684, 67333, 94891, 132892, 168068, 143445, 102770,
        144425, 111671, 113513, 104010, 148316, 104181, 140941, 202150, 97582, 94488, 50652
    );
    assertEquals(knownPageSizes.size(), testSink.countWritings());
    for (int i = 0; i < knownPageSizes.size(); i++) {
      assertEquals(knownPageSizes.get(i), testSink.getWritingBytes(i).length);
    }
  }
}