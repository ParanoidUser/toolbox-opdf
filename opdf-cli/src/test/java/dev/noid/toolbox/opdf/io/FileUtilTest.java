package dev.noid.toolbox.opdf.io;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class FileUtilTest {

  @ParameterizedTest
  @CsvSource(textBlock = """
          dir1\\dir2\\file.txt, file
          dir1\\dir2\\file.   , file
          dir1\\dir2\\file    , file
          dir1\\dir2\\.txt    , .txt
          dir1/dir2/file.txt  , file
          dir1/dir2/file.     , file
          dir1/dir2/file      , file
          """)
  void extract_file_name_without_extension(String filePath, String expected) {
    Path path = Paths.get(filePath);
    String name = FileUtil.getNameWithoutExtension(path);
    assertEquals(expected, name);
  }

  @ParameterizedTest
  @CsvSource(textBlock = """
          dir1\\dir2\\file.txt, .txt
          dir1\\dir2\\.txt    , ''
          dir1\\dir2\\.       , ''
          dir1/dir2/file.txt  , .txt
          dir1/dir2/.txt      , ''
          dir1/dir2/.         , ''
          """)
  void extract_file_extension(String filePath, String expected) {
    Path path = Paths.get(filePath);
    String name = FileUtil.getExtension(path);
    assertEquals(expected, name);
  }
}