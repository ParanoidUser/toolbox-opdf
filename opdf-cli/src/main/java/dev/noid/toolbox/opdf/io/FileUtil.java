package dev.noid.toolbox.opdf.io;

import java.nio.file.Path;

public class FileUtil {

  public static String getNameWithoutExtension(Path filePath) {
    Path file = filePath.getFileName();
    String fileName = file.toString();

    int extensionAt = indexOfExtension(file);
    if (extensionAt < 1) {
      return fileName;
    }
    return fileName.substring(0, extensionAt);
  }

  public static String getExtension(Path filePath) {
    Path file = filePath.getFileName();
    String fileName = file.toString();

    int extensionAt = indexOfExtension(file);
    if (extensionAt < 1 || extensionAt == fileName.length()) {
      return "";
    }
    return fileName.substring(extensionAt);
  }

  public static int indexOfExtension(Path filePath) {
    String fileName = filePath.getFileName().toString();
    return fileName.lastIndexOf('.');
  }
}
