package io.github.paranoiduser.impl;

import dev.noid.toolbox.opdf.api.Cmd;
import dev.noid.toolbox.opdf.api.Outcome;

@Cmd(name = "version")
public class VersionCommand implements Runnable {

  @Override
  public void run() {
    Outcome.ofBlob("v1.0");
  }
}