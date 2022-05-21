package dev.noid.toolbox.opdf.api;

public interface DataMerger {

  void merge(Iterable<DataSource> sources, DataSink sink);
}
