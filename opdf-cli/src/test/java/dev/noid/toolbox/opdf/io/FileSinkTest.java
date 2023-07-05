package dev.noid.toolbox.opdf.io;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledOnOs;
import org.junit.jupiter.api.condition.OS;
import org.junit.jupiter.api.io.TempDir;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.DosFileAttributeView;

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
  @EnabledOnOs(value = OS.WINDOWS, disabledReason = "Dos specific file attributes")
  void writing_to_read_only_file_sink(@TempDir Path temp) throws Exception {
    Path tempFile = temp.resolve("read-only.txt");
    Files.createFile(tempFile);

    DosFileAttributeView attrs = Files.getFileAttributeView(tempFile, DosFileAttributeView.class);
    attrs.setReadOnly(true);

    FileSink sink = FileSink.of(tempFile);
    assertThrows(IllegalArgumentException.class, sink::getWriting);
  }
}