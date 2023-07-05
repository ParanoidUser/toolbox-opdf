package dev.noid.toolbox.opdf.cli;

public class Opdf {

  public static void main(String[] args) {
    new CommandFactory(System.out::println).getCommand(args).run();
  }
}