package edu.austral.ingsis.clifford.domain.actions;

import edu.austral.ingsis.clifford.domain.entities.Directory;
import edu.austral.ingsis.clifford.domain.entities.FileSystem;
import edu.austral.ingsis.clifford.domain.interfaces.FileSystemAction;
import java.util.List;

public class Pwd implements FileSystemAction {
  @Override
  public String getName() {
    return "pwd";
  }

  @Override
  public String execute(List<String> command, FileSystem fileSystem) {
    if (!isCommandValid(command)) {
      throw new IllegalArgumentException();
    }
    return getPath(fileSystem.getCurrent());
  }

  private String getPath(Directory current) {
    if (current.isRoot()) {
      return "";
    }
    return getPath(current.getParent()) + "/" + current.getName();
  }

  @Override
  public boolean isCommandValid(List<String> command) {
    return command.size() == 1 && command.get(0).equals(getName());
  }
}
