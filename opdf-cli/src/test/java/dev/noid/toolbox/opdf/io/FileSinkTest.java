package dev.noid.toolbox.opdf.io;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

class FileSinkTest {

  @Test
  void write_to_file_sink(@TempDir Path temp) throws Exception {
    Path tempFile = temp.resolve("file.txt");

    FileSink sink = FileSink.of(tempFile);
    try (var stream = sink.getWriting()) {
      stream.write(new byte[]{'H', 'e', 'l', 'l', 'o', '!'});
    }
    assertEquals("Hello!", Files.readString(tempFile));
  }

  @Test
  void writing_to_broken_file_sink(@TempDir Path temp) {
    Path tempFile = temp.resolve("bro/ken.txt");

    FileSink sink = FileSink.of(tempFile);
    assertThrows(IllegalArgumentException.class, sink::getWriting);
  }
}