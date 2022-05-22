package dev.noid.toolbox.opdf.core;

import static org.junit.jupiter.api.Assertions.*;

import dev.noid.toolbox.opdf.common.FileUtil;
import java.nio.file.Path;
import org.junit.jupiter.api.Test;

class FileUtilTest {

  @Test
  void extract_file_name_without_extension() {
    var path = Path.of("dir1", "dir2", "file.txt");
    var name = FileUtil.getNameWithoutExtension(path);
    assertEquals("file", name);
  }

  @Test
  void extract_file_extension() {
    var path = Path.of("dir1", "dir2", "file.txt");
    var name = FileUtil.getExtension(path);
    assertEquals("txt", name);
  }
}