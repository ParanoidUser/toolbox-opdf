package dev.noid.toolbox.opdf.spi;

import dev.noid.toolbox.opdf.api.DataSplitter;

public interface DataSplitterFactory {

  DataSplitter getSplitter();
}
