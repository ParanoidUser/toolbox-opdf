package dev.noid.toolbox.opdf.api;

import java.io.OutputStream;

/**
 * The writer analog to a {@link DataSource}. DataSink encapsulates a destination to where output
 * can be written.
 */
public interface DataSink {

  /**
   * Opens or creates a data sink to write.
   *
   * @return a new output stream
   */
  OutputStream getWriting();
}
