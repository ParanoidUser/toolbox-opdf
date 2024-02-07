package dev.noid.toolbox.opdf.api;

import java.lang.reflect.Field;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.ServiceLoader;

public class CommandExecutor {

  public static void main(String[] args) throws Exception {
//    new CommandExecutor().execute(new String[]{"size", "C:\\Users\\dmitfedo\\Downloads\\AddDetails.zip"});
//    new CommandExecutor().execute(new String[]{"version"});
    Package[] packages = Package.getPackages();
    for (var pckg : packages) {
      System.out.println(pckg.getName());
    }

    ClassLoader classLoader = CommandExecutor.class.getClassLoader();
  }

  private final Map<String, Runnable> commands = new HashMap<>();

  public CommandExecutor() {
    ServiceLoader<Runnable> runnables = ServiceLoader.load(Runnable.class);
    for (Runnable runnable : runnables) {
      if (runnable.getClass().isAnnotationPresent(Cmd.class)) {
        Cmd cmd = runnable.getClass().getAnnotation(Cmd.class);
        commands.put(cmd.name().toLowerCase(), runnable);
      }
    }
  }

  public void execute(String[] args) throws Exception {
    Runnable runnable = commands.get(args[0].toLowerCase());

    Field[] fields = runnable.getClass().getDeclaredFields();
    for (Field field : fields) {
      if (field.isAnnotationPresent(Argument.class)) {

        Argument argument = field.getAnnotation(Argument.class);
        if (argument.name().isEmpty()) {
          field.setAccessible(true);
          field.set(runnable, Path.of(args[1]));
        }
      }
    }

    runnable.run();
  }
}
