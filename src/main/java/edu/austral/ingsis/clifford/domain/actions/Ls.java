package edu.austral.ingsis.clifford.domain.actions;

import edu.austral.ingsis.clifford.domain.entities.FileSystem;
import edu.austral.ingsis.clifford.domain.interfaces.FileSystemAction;
import edu.austral.ingsis.clifford.domain.interfaces.FileSystemObject;
import java.util.Collections;
import java.util.List;

public class Ls implements FileSystemAction {

  @Override
  public String getName() {
    return "ls";
  }

  @Override
  public String execute(List<String> command, FileSystem fileSystem) {
    if (!isCommandValid(command)) {
      throw new IllegalArgumentException();
    }
    boolean reversed = false;
    if (command.size() != 1) {
      reversed = isReversedOption(command.get(1));
    }
    List<FileSystemObject> children = fileSystem.getCurrent().getChildren();
    if (reversed) {
      Collections.reverse(children);
    }
    return getPrint(children);
  }

  @Override
  public boolean isCommandValid(List<String> command) {
    return !command.isEmpty()
        && command.get(0).equals(getName())
        && ((command.size() == 2 && isOptionValid(command.get(1))) || command.size() == 1);
  }

  private boolean isOptionValid(String option) {
    return option.startsWith("--ord");
  }

  private boolean isReversedOption(String option) {
    return option.endsWith("desc");
  }

  private String getPrint(List<FileSystemObject> objects) {
    if (objects.isEmpty()) {
      return "";
    }
    StringBuilder sb = new StringBuilder();
    objects.forEach(o -> sb.append(o.getName()).append(" "));
    sb.deleteCharAt(sb.length() - 1);
    return sb.toString();
  }
}
