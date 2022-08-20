package dev.noid.toolbox.opdf.pdfbox;

import dev.noid.toolbox.opdf.api.DataSource;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

public class TestSource implements DataSource {

  private final String resourceName;
  private final AtomicInteger readingCount = new AtomicInteger();
  private final AtomicInteger closeCount = new AtomicInteger();

  TestSource(String resourceName) {
    this.resourceName = resourceName;
  }

  @Override
  public InputStream getReading() {
    var stream = getClass().getClassLoader().getResourceAsStream(resourceName);
    Objects.requireNonNull(stream, "Test resource not found: " + resourceName);
    readingCount.incrementAndGet();
    return new BufferedInputStream(stream) {
      @Override
      public void close() throws IOException {
        super.close();
        closeCount.incrementAndGet();
      }
    };
  }

  int getReadingCalls() {
    return readingCount.get();
  }

  int getCloseCalls() {
    return closeCount.get();
  }
}
