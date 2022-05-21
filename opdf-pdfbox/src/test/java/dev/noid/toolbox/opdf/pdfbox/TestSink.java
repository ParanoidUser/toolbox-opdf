package dev.noid.toolbox.opdf.pdfbox;

import dev.noid.toolbox.opdf.api.DataSink;
import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.util.LinkedList;
import org.mockito.Mockito;

public class TestSink implements DataSink {

  private final LinkedList<ByteArrayOutputStream> writings = new LinkedList<>();

  @Override
  public OutputStream getWriting() {
    var stream = new ByteArrayOutputStream();
    stream = Mockito.spy(stream);
    writings.add(stream);
    return stream;
  }

  int size() {
    return writings.size();
  }

  OutputStream getWritingRef(int index) {
    return writings.get(index);
  }

  byte[] getWritingBytes(int index) {
    return writings.get(index).toByteArray();
  }
}
