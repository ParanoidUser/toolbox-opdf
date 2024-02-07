package io.github.paranoiduser.impl;

import dev.noid.toolbox.opdf.api.Argument;
import dev.noid.toolbox.opdf.api.Cmd;
import dev.noid.toolbox.opdf.api.Outcome;
import java.nio.file.Files;
import java.nio.file.Path;

@Cmd(name = "size")
public class SizeCommand implements Runnable {

  @Argument
  Path file;

  public void run() {
    try {
      long size = Files.size(file);
      Outcome.ofBlob(size);
    } catch (Exception cause) {
      throw new RuntimeException(cause);
    }
  }
}