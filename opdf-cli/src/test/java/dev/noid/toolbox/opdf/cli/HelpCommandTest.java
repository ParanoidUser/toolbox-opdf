package dev.noid.toolbox.opdf.cli;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class HelpCommandTest {

  @Test
  void help_content() {
    StringBuilder output = new StringBuilder();
    new HelpCommand(output::append).run();

    String content = output.toString();
    assertTrue(content.startsWith("Open PDF Tool"));
    assertEquals(237, content.length());
  }
}