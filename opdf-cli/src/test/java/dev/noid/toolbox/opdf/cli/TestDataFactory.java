package dev.noid.toolbox.opdf.cli;

import dev.noid.toolbox.opdf.api.DataMerger;
import dev.noid.toolbox.opdf.api.DataSource;
import dev.noid.toolbox.opdf.api.DataSplitter;
import dev.noid.toolbox.opdf.spi.DataMergerFactory;
import dev.noid.toolbox.opdf.spi.DataSplitterFactory;

import java.io.OutputStream;

public class TestDataFactory implements DataMergerFactory, DataSplitterFactory {

  @Override
  public DataMerger getMerger() {
    return (sources, sink) -> {
      try (var out = sink.getWriting()) {
        for (DataSource source : sources) {
          try (var in = source.getReading()) {
            in.transferTo(out);
          }
        }
      } catch (Exception cause) {
        throw new RuntimeException(cause);
      }
    };
  }

  @Override
  public DataSplitter getSplitter() {
    return (source, sink) -> {
      try (var in = source.getReading()) {
        OutputStream out = null;
        for (byte b : in.readAllBytes()) {
          if (out == null) {
            out = sink.getWriting();
          }
          if (b != '+') {
            out.write(b);
          } else {
            out.flush();
            out.close();
            out = null;
          }
        }
        if (out != null) {
          out.close();
        }
      } catch (Exception cause) {
        throw new RuntimeException(cause);
      }
    };
  }
}
