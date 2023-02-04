package dev.noid.toolbox.opdf.spi;

import dev.noid.toolbox.opdf.api.DataMerger;
import dev.noid.toolbox.opdf.api.DataSplitter;

public class TestDataFactory implements DataMergerFactory, DataSplitterFactory {

  @Override
  public DataMerger getMerger() {
    return (sources, sink) -> {};
  }

  @Override
  public DataSplitter getSplitter() {
    return (source, sink) -> {};
  }
}
