package dev.noid.toolbox.opdf.cli;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class OpdfTest {

  @Test
  void entrypoint() {
    var buffer = new ByteArrayOutputStream();
    System.setOut(new PrintStream(buffer));
    Opdf.main(new String[]{"version"});
    assertEquals("unknown", buffer.toString());
  }
}