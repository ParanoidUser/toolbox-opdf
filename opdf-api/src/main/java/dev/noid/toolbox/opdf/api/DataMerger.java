package dev.noid.toolbox.opdf.api;

public interface DataMerger {

  void merge(Iterable<? extends DataSource> sources, DataSink sink);
}
