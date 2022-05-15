package dev.noid.toolbox.opdf.core;

public class TaskExecutionException extends RuntimeException {

  public TaskExecutionException(String message) {
    super(message);
  }

  public TaskExecutionException(String message, Throwable cause) {
    super(message, cause);
  }
}
