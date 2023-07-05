package dev.noid.toolbox.opdf.io;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class FileSourceTest {

  @Test
  void read_file_source(@TempDir Path temp) throws Exception {
    Path tempFile = temp.resolve("file.txt");
    Files.writeString(tempFile, "Hello!");

    FileSource source = FileSource.of(tempFile);
    try (var stream = source.getReading()) {
      assertArrayEquals(new byte[]{'H', 'e', 'l', 'l', 'o', '!'}, stream.readAllBytes());
    }
  }

  @Test
  void reading_from_non_existing_file_source() {
    FileSource source = FileSource.of(Path.of("not_found.txt"));
    assertThrows(IllegalArgumentException.class, source::getReading);
  }
}