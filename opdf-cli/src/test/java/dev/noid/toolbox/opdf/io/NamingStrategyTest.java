package dev.noid.toolbox.opdf.io;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import org.junit.jupiter.api.Test;

class NamingStrategyTest {

  @Test
  void ordinary_naming_strategy() {
    var naming = NamingStrategy.ordinary("file", ".txt");
    assertEquals("file0.txt", naming.getName());
    assertEquals("file1.txt", naming.getName());
    assertEquals("file2.txt", naming.getName());
  }

  @Test
  void time_naming_strategy() {
    var stamps = List.of("220819-105047", "220819-105048", "220819-105050").iterator();
    var naming = NamingStrategy.custom("file_", ".txt", stamps::next);
    assertEquals("file_220819-105047.txt", naming.getName());
    assertEquals("file_220819-105048.txt", naming.getName());
    assertEquals("file_220819-105050.txt", naming.getName());
  }
}