package dev.noid.toolbox.opdf.pdfbox;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

import java.util.List;
import org.apache.pdfbox.io.MemoryUsageSetting;
import org.apache.pdfbox.multipdf.PDFMergerUtility;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PdfBoxMergerTest {

  private PdfBoxMerger merger;

  @BeforeEach
  void setUp() {
    PDFMergerUtility pdfMerger = new PDFMergerUtility();
    MemoryUsageSetting inMemory = MemoryUsageSetting.setupMainMemoryOnly();
    merger = new PdfBoxMerger(pdfMerger, inMemory);
  }

  @Test
  void merge_two_document_copies_into_one() {
    TestSource page1 = new TestSource("text-page-1.pdf");
    TestSource page2 = new TestSource("text-page-2.pdf");
    TestSink testSink = new TestSink();

    merger.merge(List.of(page1, page2), testSink);

    assertEquals(1, testSink.getWritingCalls());
    assertEquals(2, PdfBoxUtil.getPageCount(testSink.getBytesWritten(0)));
  }

  @Test
  void merge_result_has_consistent_size() {
    TestSource page1 = new TestSource("text-page-1.pdf");
    TestSource page2 = new TestSource("text-page-2.pdf");
    TestSink testSink = new TestSink();

    merger.merge(List.of(page1, page2), testSink);

    assertEquals(1, testSink.getWritingCalls());
    assertEquals(1551, testSink.getBytesWritten(0).length);
  }

  @Test
  void merge_closes_all_resources() {
    TestSource page1 = new TestSource("text-page-1.pdf");
    TestSource page2 = new TestSource("text-page-2.pdf");
    TestSink testSink = new TestSink();

    merger.merge(List.of(page1, page2), testSink);

    assertEquals(1, page1.getReadingCalls());
    assertEquals(1, page1.getCloseCalls());

    assertEquals(1, page2.getReadingCalls());
    assertEquals(1, page2.getCloseCalls());

    assertEquals(1, testSink.getWritingCalls());
    assertEquals(1, testSink.getCloseCalls());
  }

  @Test
  void merge_error_when_document_missing() {
    List<TestSource> badSources = List.of(new TestSource("not-found.pdf"));
    TestSink testSink = new TestSink();

    Exception error = assertThrows(IllegalArgumentException.class, () -> merger.merge(badSources, testSink));
    assertEquals("Cannot add source for merging", error.getMessage());
    assertEquals("Test resource not found: not-found.pdf", error.getCause().getMessage());
  }

  @Test
  void merge_error_when_sink_failed() {
    List<TestSource> multiSource = List.of(new TestSource("text-page-1.pdf"), new TestSource("text-page-2.pdf"));
    TestSink badSink = mock(TestSink.class);
    doThrow(new RuntimeException("Test sink problem")).when(badSink).getWriting();

    Exception error = assertThrows(IllegalArgumentException.class, () -> merger.merge(multiSource, badSink));
    assertEquals("Cannot merge documents", error.getMessage());
    assertEquals("Test sink problem", error.getCause().getMessage());
  }

  @Test
  void merge_error_when_chain_close() throws Exception {
    TestSource goodSource = new TestSource("text-page-1.pdf");
    try (var goodReading = goodSource.getReading()) {
      var badReading = spy(goodReading);
      doThrow(new RuntimeException("Test source problem")).when(badReading).close();

      TestSource fakeSource = spy(goodSource);
      doReturn(badReading).when(fakeSource).getReading();

      TestSource anotherSource = new TestSource("text-page-2.pdf");
      List<TestSource> multiSource = List.of(fakeSource, anotherSource);

      TestSink testSink = new TestSink();
      merger.merge(multiSource, testSink);

      assertEquals(0, fakeSource.getCloseCalls()); // test failure
      assertEquals(1, anotherSource.getCloseCalls());
      assertEquals(1, testSink.getCloseCalls());
    }
  }
}