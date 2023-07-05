package dev.noid.toolbox.opdf.cli;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

class SplitCommandTest {

  @Test
  void split_file(@TempDir Path temp) throws Exception {
    Path input = temp.resolve("file.txt");
    Files.writeString(input, "Hello+Goodbye!");
    Path result0 = temp.resolve("file0.txt");
    Path result1 = temp.resolve("file1.txt");

    assertFalse(Files.isRegularFile(result0));
    assertFalse(Files.isRegularFile(result1));
    new SplitCommand(input).run();

    assertTrue(Files.isRegularFile(result0));
    assertEquals("Hello", Files.readString(result0));
    assertTrue(Files.isRegularFile(result1));
    assertEquals("Goodbye!", Files.readString(result1));
  }
}