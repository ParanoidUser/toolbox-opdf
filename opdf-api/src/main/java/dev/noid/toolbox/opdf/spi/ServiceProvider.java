package dev.noid.toolbox.opdf.spi;

import java.util.Iterator;
import java.util.ServiceLoader;

public class ServiceProvider {

  public static <T> Iterable<T> findAll(Class<T> serviceClass) {
    Iterator<T> iterator = ServiceLoader.load(serviceClass).iterator();
    if (!iterator.hasNext()) {
      throw new NoClassDefFoundError("Cannot find any implementations of class: " + serviceClass);
    }
    return () -> iterator;
  }

  public static <T> T findFirst(Class<T> serviceClass) {
    return findAll(serviceClass).iterator().next();
  }
}
