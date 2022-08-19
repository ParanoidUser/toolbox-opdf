package dev.noid.toolbox.opdf.io;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Supplier;

public class NamingStrategy {

  public static NamingStrategy ordinary(String prefix, String suffix) {
    AtomicInteger sid = new AtomicInteger();
    return custom(prefix, suffix, sid::getAndIncrement);
  }

  public static NamingStrategy custom(String prefix, String suffix, Supplier<Object> generator) {
    return new NamingStrategy(prefix, suffix, generator);
  }

  private final String prefix;
  private final String suffix;
  private final Supplier<Object> generator;

  NamingStrategy(String prefix, String suffix, Supplier<Object> generator) {
    this.prefix = prefix;
    this.suffix = suffix;
    this.generator = generator;
  }

  public String getName() {
    Object middle = generator.get();
    return String.join("", prefix, String.valueOf(middle), suffix);
  }
}
