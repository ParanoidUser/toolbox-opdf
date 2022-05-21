package dev.noid.toolbox.opdf.spi;

import java.util.ServiceLoader;

public final class DataFactoryProvider {

  public static DataMergerFactory getMergerFactory() {
    return getDataFactory(DataMergerFactory.class);
  }

  public static DataSplitterFactory getSplitterFactory() {
    return getDataFactory(DataSplitterFactory.class);
  }

  private static <T> T getDataFactory(Class<T> type) {
    return ServiceLoader.load(type)
        .findFirst()
        .orElseThrow(() -> new NoClassDefFoundError(
            "Cannot find any implementations of factory class: " + type));
  }
}
