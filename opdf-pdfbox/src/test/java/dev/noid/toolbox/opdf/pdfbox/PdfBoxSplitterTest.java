package dev.noid.toolbox.opdf.pdfbox;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import org.apache.pdfbox.io.MemoryUsageSetting;
import org.apache.pdfbox.multipdf.Splitter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledOnOs;
import org.junit.jupiter.api.condition.OS;

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

    assertEquals(2, testSink.getWritingCalls());
    assertEquals(1, PdfBoxUtil.getPageCount(testSink.getBytesWritten(0)));
    assertEquals(1, PdfBoxUtil.getPageCount(testSink.getBytesWritten(1)));
  }

  @Test
  @EnabledOnOs(value = OS.WINDOWS, disabledReason = "on Unix final size is few bytes less")
  void split_result_has_concistent_size() {
    var twoPages = new TestSource("two-text-pages.pdf");
    var testSink = new TestSink();

    splitter.split(twoPages, testSink);

    assertEquals(2, testSink.getWritingCalls());
    assertEquals(1245, testSink.getBytesWritten(0).length);
    assertEquals(1208, testSink.getBytesWritten(1).length);
  }

  @Test
  void split_closes_all_resources() throws Exception {
    var twoPages = new TestSource("two-text-pages.pdf");
    var testSink = new TestSink();

    splitter.split(twoPages, testSink);

    assertEquals(1, twoPages.getCloseCalls());
    assertEquals(2, testSink.getWritingCalls());
    // closed explicitly by the merger and implicitly by PDF box
    assertEquals(4, testSink.getCloseCalls());
  }

  @Test
  @EnabledOnOs(value = OS.WINDOWS, disabledReason = "on Unix final size is few bytes less")
  void split_complex_document() {
    var multiPage = new TestSource("wfrw-ii-figures-and-tables.pdf");
    var testSink = new TestSink();

    splitter.split(multiPage, testSink);

    var knownPageSizes = List.of(
        68643, 96934, 50784, 141184, 107684, 67333, 94891, 132892, 168068, 143445, 102770,
        144425, 111671, 113513, 104010, 148316, 104181, 140941, 202150, 97582, 94488, 50652
    );
    assertEquals(knownPageSizes.size(), testSink.getWritingCalls());
    for (int i = 0; i < knownPageSizes.size(); i++) {
      assertEquals(knownPageSizes.get(i), testSink.getBytesWritten(i).length);
    }
  }
}