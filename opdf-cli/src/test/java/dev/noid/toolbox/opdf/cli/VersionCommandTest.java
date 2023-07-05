package dev.noid.toolbox.opdf.cli;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class VersionCommandTest {

  @Test
  void show_version() {
    StringBuilder output = new StringBuilder();
    new VersionCommand(output::append).run();
    assertEquals("unknown", output.toString());
  }
}