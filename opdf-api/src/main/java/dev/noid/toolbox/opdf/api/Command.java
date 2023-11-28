package dev.noid.toolbox.opdf.api;

public interface Command {

  void execute() throws ExecutionFailed;

  class ExecutionFailed extends RuntimeException {

    public ExecutionFailed(String message, Throwable cause) {
      super(message, cause);
    }
  }
}
