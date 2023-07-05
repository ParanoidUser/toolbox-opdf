package dev.noid.toolbox.opdf.cli;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

class MergeCommandTest {

  @Test
  void merge_files_in_dir(@TempDir Path temp) throws Exception {
    Path file1 = temp.resolve("file1.txt");
    Files.writeString(file1, "Hello");
    Path file2 = temp.resolve("file2.txt");
    Files.writeString(file2, " & ");
    Path file3 = temp.resolve("file3.txt");
    Files.writeString(file3, "Goodbye");
    Path result = temp.resolve("merged.pdf");

    assertFalse(Files.isRegularFile(result));
    new MergeCommand(temp).run();
    assertTrue(Files.isRegularFile(result));
    assertEquals("Hello & Goodbye", Files.readString(result));
  }

  @Test
  void skip_when_path_not_dir(@TempDir Path temp) throws Exception {
    Path file = temp.resolve("file.txt");
    Files.writeString(file, "Hello");

    MergeCommand command = new MergeCommand(file);
    assertThrows(IllegalArgumentException.class, command::run);
  }

  @Test
  void fail_when_dir_not_found(@TempDir Path temp) {
    Path notFound = temp.resolve("not_found");
    MergeCommand command = new MergeCommand(notFound);
    assertThrows(IllegalArgumentException.class, command::run);
  }
}