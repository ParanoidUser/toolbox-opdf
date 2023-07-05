package dev.noid.toolbox.opdf.cli;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CommandFactoryTest {

  private CommandFactory factory;

  @BeforeEach
  void setUp() {
    factory = new CommandFactory(s -> {});
  }

  @Test
  void help_command() {
    Runnable command = factory.getCommand("help");
    assertTrue(command instanceof HelpCommand);
  }

  @Test
  void version_command() {
    Runnable command = factory.getCommand("version");
    assertTrue(command instanceof VersionCommand);
  }

  @Test
  void merge_command() {
    Runnable command = factory.getCommand("merge", "/dir");
    assertTrue(command instanceof MergeCommand);
  }

  @Test
  void split_command() {
    Runnable command = factory.getCommand("split", "/test.pdf");
    assertTrue(command instanceof SplitCommand);
  }

  @Test
  void unknown_command() {
    Runnable command = factory.getCommand("test");
    assertTrue(command instanceof HelpCommand);
  }

  @Test
  void extra_arguments() {
    Runnable command = factory.getCommand("help", "help", "help!");
    assertTrue(command instanceof HelpCommand);
  }

  @Test
  void missing_required_argument() {
    assertThrows(IllegalArgumentException.class, () -> factory.getCommand("merge"));
  }
}