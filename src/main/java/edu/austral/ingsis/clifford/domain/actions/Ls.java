package edu.austral.ingsis.clifford.domain.actions;

import edu.austral.ingsis.clifford.domain.data.Command;
import edu.austral.ingsis.clifford.domain.entities.FileSystem;
import edu.austral.ingsis.clifford.domain.interfaces.FileSystemAction;
import edu.austral.ingsis.clifford.domain.interfaces.FileSystemObject;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class Ls implements FileSystemAction {
  private Set<String> options = Set.of("ord=asc", "ord=desc");

  @Override
  public String getName() {
    return "ls";
  }

  @Override
  public int getNumberOfArgs() {
    return 0;
  }

  @Override
  public Set<String> getValidOptions() {
    return options;
  }

  @Override
  public String execute(Command command, FileSystem fileSystem) {
    if (isCommandInvalid(command)) {
      throw new IllegalArgumentException("Invalid command");
    }

    List<FileSystemObject> fso = fileSystem.getCurrent().getChildren();

    if (isReversed(command.getOptions())) {
      // fso = fso.reversed();
      List<FileSystemObject> reversed = new ArrayList<>(fso.size());
      for (int i = fso.size() - 1; i >= 0; i--) {
        reversed.add(fso.get(i));
      }
      fso = reversed;
    }

    return getPrint(fso);
  }

  @Override
  public String getPrint(String str) {
    return "";
  }

  private String getPrint(List<FileSystemObject> fso) {
    if (fso.isEmpty()) {
      return "";
    }
    StringBuilder sb = new StringBuilder();
    sb.append(fso.get(0).getName());
    for (int i = 1; i < fso.size(); i++) {
      sb.append(" ");
      sb.append(fso.get(i).getName());
    }
    return sb.toString();
  }

  private boolean isReversed(Set<String> options) {
    return options.contains("ord=desc");
  }
}
