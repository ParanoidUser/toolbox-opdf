package dev.noid.toolbox.opdf.api;

import java.io.Serializable;
import java.util.Optional;
import java.util.ServiceLoader;

public interface Outcome extends AutoCloseable {

  static void ofBlob(Serializable data) {
    try (Outcome storage = getInstance()) {
      storage.publish(data);
    } catch (Exception cause) {
      throw new RuntimeException("Store failed", cause);
    }
  }

  private static Outcome getInstance() {
    Optional<Outcome> maybe = ServiceLoader.load(Outcome.class).findFirst();
    if (maybe.isEmpty()) {
      throw new RuntimeException("Storage not found");
    }
    return maybe.get();
  }

  void publish(Serializable data);
}
