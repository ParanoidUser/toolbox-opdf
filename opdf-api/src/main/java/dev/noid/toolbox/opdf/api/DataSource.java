package dev.noid.toolbox.opdf.api;

import java.io.InputStream;

/**
 * The reader analog to a {@link DataSink}. DataSource encapsulates a source from where input data
 * can be read.
 */
public interface DataSource {

  /**
   * Opens a data source to read.
   *
   * @return a new input stream
   */
  InputStream getReading();
}
