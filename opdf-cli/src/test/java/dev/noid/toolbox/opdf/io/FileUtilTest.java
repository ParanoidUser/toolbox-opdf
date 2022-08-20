package dev.noid.toolbox.opdf.io;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.nio.file.Path;
import java.nio.file.Paths;
import org.junit.jupiter.api.Test;

class FileUtilTest {

  @Test
  void extract_file_name_without_extension() {
    Path path = Paths.get("dir1", "dir2", "file.txt");
    String name = FileUtil.getNameWithoutExtension(path);
    assertEquals("file", name);
  }

  @Test
  void extract_file_extension() {
    Path path = Paths.get("dir1", "dir2", "file.txt");
    String name = FileUtil.getExtension(path);
    assertEquals(".txt", name);
  }
}