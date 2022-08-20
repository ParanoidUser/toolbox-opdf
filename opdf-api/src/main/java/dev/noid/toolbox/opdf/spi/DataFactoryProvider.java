package dev.noid.toolbox.opdf.spi;

import java.util.Iterator;
import java.util.ServiceLoader;

public final class DataFactoryProvider {

  public static DataMergerFactory getMergerFactory() {
    return getDataFactory(DataMergerFactory.class);
  }

  public static DataSplitterFactory getSplitterFactory() {
    return getDataFactory(DataSplitterFactory.class);
  }

  private static <T> T getDataFactory(Class<T> type) {
    Iterator<T> iterator = ServiceLoader.load(type).iterator();
    if (!iterator.hasNext()) {
      throw new NoClassDefFoundError("Cannot find any implementations of factory class: " + type);
    }
    return iterator.next();
  }
}
