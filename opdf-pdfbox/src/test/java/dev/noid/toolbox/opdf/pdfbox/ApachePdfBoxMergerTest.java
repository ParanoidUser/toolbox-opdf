package dev.noid.toolbox.opdf.pdfbox;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.ByteArrayOutputStream;
import java.util.List;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ApachePdfBoxMergerTest {

  private ApachePdfBoxMerger merger;

  @BeforeEach
  void setUp() {
    merger = new ApachePdfBoxMerger();
  }

  @Test
  void merge_two_document_copies_into_one() throws Exception {
    try (var page1 = getClass().getClassLoader().getResourceAsStream("one-blank-page.pdf");
        var page2 = getClass().getClassLoader().getResourceAsStream("one-blank-page.pdf")) {

      var result = new ByteArrayOutputStream();
      merger.merge(List.of(page1, page2), result);

      assertEquals(2, PdfUtil.getPageCount(result.toByteArray()));
      assertEquals(1291, result.size());
    }
  }
}