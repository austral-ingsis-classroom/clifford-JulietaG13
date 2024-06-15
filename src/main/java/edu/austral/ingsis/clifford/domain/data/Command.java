package edu.austral.ingsis.clifford.domain.data;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Command {
  private String name;
  private List<String> args;
  private Set<String> options;

  public Command(String command) {
    parse(command);
  }

  public Command(String name, List<String> args, Set<String> options) {
    this.name = name;
    this.args = args;
    this.options = options;
  }

  private void parse(String command) {
    String[] split = command.split(" ");
    if (split.length == 0) {
      throw new IllegalArgumentException();
    }
    this.name = split[0];

    List<String> args = new ArrayList<>(split.length);
    Set<String> options = new HashSet<>(split.length);
    for (int i = 1; i < split.length; i++) {
      if (split[i].startsWith("--")) {
        options.add(split[i].substring(2));
      } else {
        args.add(split[i]);
      }
    }

    this.args = args;
    this.options = options;
  }

  public String getName() {
    return name;
  }

  public List<String> getArgs() {
    return Collections.unmodifiableList(args);
  }

  public Set<String> getOptions() {
    return Collections.unmodifiableSet(options);
  }
}
