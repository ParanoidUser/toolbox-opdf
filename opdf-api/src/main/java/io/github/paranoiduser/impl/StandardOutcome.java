package io.github.paranoiduser.impl;

import dev.noid.toolbox.opdf.api.Outcome;
import java.io.Serializable;

public class StandardOutcome implements Outcome {

  @Override
  public void publish(Serializable data) {
    System.out.println(data);
  }

  @Override
  public void close() throws Exception {

  }
}
