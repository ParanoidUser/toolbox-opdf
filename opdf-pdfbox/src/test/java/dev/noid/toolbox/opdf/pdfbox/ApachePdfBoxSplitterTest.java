package dev.noid.toolbox.opdf.pdfbox;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ApachePdfBoxSplitterTest {

  private ApachePdfBoxSplitter splitter;

  @BeforeEach
  void setUp() {
    splitter = new ApachePdfBoxSplitter();
  }

  @Test
  void split_document_into_two_pages() throws Exception {
    List<ByteArrayOutputStream> results = new ArrayList<>();
    try (var twoPages = getClass().getClassLoader().getResourceAsStream("two-blank-pages.pdf")) {
      splitter.split(twoPages, getStreamFactory(results));
    }
    assertEquals(2, results.size());
    assertEquals(833, results.get(0).size());
    assertEquals(833, results.get(1).size());
  }

  private Supplier<OutputStream> getStreamFactory(List<ByteArrayOutputStream> registry) {
    return () -> {
      var stream = new ByteArrayOutputStream();
      registry.add(stream);
      return stream;
    };
  }
}