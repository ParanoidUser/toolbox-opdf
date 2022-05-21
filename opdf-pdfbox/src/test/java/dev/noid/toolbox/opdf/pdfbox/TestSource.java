package dev.noid.toolbox.opdf.pdfbox;

import dev.noid.toolbox.opdf.api.DataSource;
import java.io.InputStream;
import java.util.Objects;
import org.mockito.Mockito;

public class TestSource implements DataSource {

  private final String resourceName;
  private InputStream readingRef;

  public TestSource(String resourceName) {
    this.resourceName = resourceName;
  }

  @Override
  public InputStream getReading() {
    var stream = getClass().getClassLoader().getResourceAsStream(resourceName);
    Objects.requireNonNull(stream, "Test resource not found: " + resourceName);
    readingRef = Mockito.spy(stream);
    return readingRef;
  }

  InputStream getReadingRef() {
    return readingRef;
  }
}
