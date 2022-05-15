package dev.noid.toolbox.opdf.spi;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.function.Supplier;

public interface PdfSplitter {

  void split(InputStream docSource, Supplier<OutputStream> destSupplier) throws IOException;
}
