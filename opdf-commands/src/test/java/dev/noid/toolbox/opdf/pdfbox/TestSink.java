package dev.noid.toolbox.opdf.pdfbox;

import dev.noid.toolbox.opdf.api.DataSink;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.LinkedList;
import java.util.concurrent.atomic.AtomicInteger;

public class TestSink implements DataSink {

  private final LinkedList<ByteArrayOutputStream> writings = new LinkedList<>();
  private final AtomicInteger closeCount = new AtomicInteger();

  @Override
  public OutputStream getWriting() {
    ByteArrayOutputStream stream = new ByteArrayOutputStream() {
      @Override
      public void close() throws IOException {
        super.close();
        closeCount.incrementAndGet();
      }
    };
    writings.add(stream);
    return stream;
  }

  int getWritingCalls() {
    return writings.size();
  }

  int getCloseCalls() {
    return closeCount.get();
  }

  byte[] getBytesWritten(int index) {
    return writings.get(index).toByteArray();
  }
}
