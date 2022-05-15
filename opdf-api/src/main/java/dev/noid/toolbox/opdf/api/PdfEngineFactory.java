package dev.noid.toolbox.opdf.api;

import java.util.ServiceLoader;

public class PdfEngineFactory {

  public <T> T getEngine(Class<T> type) {
    return ServiceLoader.load(type)
        .findFirst()
        .orElseThrow(() -> new IllegalStateException(
            "Cannot find any implementations of class: " + type));
  }
}
