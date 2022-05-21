package dev.noid.toolbox.opdf.spi;

import dev.noid.toolbox.opdf.api.DataMerger;

public interface DataMergerFactory {

  DataMerger getMerger();
}
