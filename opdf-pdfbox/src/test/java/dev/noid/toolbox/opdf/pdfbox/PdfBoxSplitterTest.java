package dev.noid.toolbox.opdf.pdfbox;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;

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
    Splitter pdfSplitter = new Splitter();
    MemoryUsageSetting inMemory = MemoryUsageSetting.setupMainMemoryOnly();
    splitter = new PdfBoxSplitter(pdfSplitter, inMemory);
  }

  @Test
  void split_document_into_two_pages() {
    TestSource twoPages = new TestSource("two-text-pages.pdf");
    TestSink testSink = new TestSink();

    splitter.split(twoPages, testSink);

    assertEquals(2, testSink.getWritingCalls());
    assertEquals(1, PdfBoxUtil.getPageCount(testSink.getBytesWritten(0)));
    assertEquals(1, PdfBoxUtil.getPageCount(testSink.getBytesWritten(1)));
  }

  @Test
  @EnabledOnOs(value = OS.WINDOWS, disabledReason = "on Unix final size is few bytes less")
  void split_result_has_concistent_size() {
    TestSource twoPages = new TestSource("two-text-pages.pdf");
    TestSink testSink = new TestSink();

    splitter.split(twoPages, testSink);

    assertEquals(2, testSink.getWritingCalls());
    assertEquals(1245, testSink.getBytesWritten(0).length);
    assertEquals(1208, testSink.getBytesWritten(1).length);
  }

  @Test
  void split_closes_all_resources_when_succeed() {
    TestSource twoPages = new TestSource("two-text-pages.pdf");
    TestSink testSink = new TestSink();

    splitter.split(twoPages, testSink);

    assertEquals(1, twoPages.getReadingCalls());
    assertEquals(1, twoPages.getCloseCalls());

    assertEquals(2, testSink.getWritingCalls());
    // closed explicitly by the merger and implicitly by PDF box
    assertEquals(4, testSink.getCloseCalls());
  }

  @Test
  @EnabledOnOs(value = OS.WINDOWS, disabledReason = "on Unix final size is few bytes less")
  void split_complex_document() {
    TestSource multiPage = new TestSource("wfrw-ii-figures-and-tables.pdf");
    TestSink testSink = new TestSink();

    splitter.split(multiPage, testSink);

    List<Integer> knownPageSizes = List.of(
        68643, 96934, 50784, 141184, 107684, 67333, 94891, 132892, 168068, 143445, 102770,
        144425, 111671, 113513, 104010, 148316, 104181, 140941, 202150, 97582, 94488, 50652
    );
    assertEquals(knownPageSizes.size(), testSink.getWritingCalls());
    for (int i = 0; i < knownPageSizes.size(); i++) {
      assertEquals(knownPageSizes.get(i), testSink.getBytesWritten(i).length);
    }
  }

  @Test
  void split_error_when_document_missing() {
    TestSource badSource = new TestSource("not-found.pdf");
    TestSink testSink = new TestSink();

    Exception error = assertThrows(IllegalArgumentException.class, () -> splitter.split(badSource, testSink));
    assertEquals("Cannot split document", error.getMessage());
    assertEquals("Test resource not found: not-found.pdf", error.getCause().getMessage());
  }

  @Test
  void split_error_when_sink_failed() {
    TestSource twoPages = new TestSource("two-text-pages.pdf");
    TestSink badSink = mock(TestSink.class);
    doThrow(new RuntimeException("Test sink problem")).when(badSink).getWriting();

    Exception error = assertThrows(IllegalArgumentException.class, () -> splitter.split(twoPages, badSink));
    assertEquals("Cannot split document", error.getMessage());
    assertEquals("Test sink problem", error.getCause().getMessage());
  }
}