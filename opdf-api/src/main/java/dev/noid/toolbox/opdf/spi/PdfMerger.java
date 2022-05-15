package dev.noid.toolbox.opdf.spi;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public interface PdfMerger {

  void merge(Iterable<InputStream> docSources, OutputStream mergeResult) throws IOException;
}
