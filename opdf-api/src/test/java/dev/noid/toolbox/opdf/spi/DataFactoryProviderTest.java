package dev.noid.toolbox.opdf.spi;

import static dev.noid.toolbox.opdf.spi.DataFactoryProvider.getInstance;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.lang.reflect.Constructor;
import org.junit.jupiter.api.Test;

class DataFactoryProviderTest {

  @Test
  void lookup_default_merger_factory() {
    assertEquals(NoopDataFactory.class, getInstance(DataMergerFactory.class).getClass());
  }

  @Test
  void lookup_default_splitter_factory() {
    assertEquals(NoopDataFactory.class, getInstance(DataSplitterFactory.class).getClass());
  }

  @Test
  void lookup_unknown_data_factory() {
    var error = assertThrows(NoClassDefFoundError.class, () -> getInstance(UnknownDataFactory.class));
    assertEquals("Cannot find any implementations of factory class: " + UnknownDataFactory.class, error.getMessage());
  }

  @Test
  void prohibit_instance_creation() {
    try {
      Constructor<DataFactoryProvider> constructor = DataFactoryProvider.class.getDeclaredConstructor();
      constructor.setAccessible(true);
      constructor.newInstance();
    } catch (Exception cause) {
      assertEquals(UnsupportedOperationException.class, cause.getCause().getClass());
    }
  }
}
