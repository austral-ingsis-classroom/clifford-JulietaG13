package edu.austral.ingsis.clifford.domain.actions;

import edu.austral.ingsis.clifford.domain.data.Command;
import edu.austral.ingsis.clifford.domain.entities.Directory;
import edu.austral.ingsis.clifford.domain.entities.FileSystem;
import edu.austral.ingsis.clifford.domain.interfaces.FileSystemAction;
import java.util.Set;

public class Pwd implements FileSystemAction {
  @Override
  public String getName() {
    return "pwd";
  }

  @Override
  public int getNumberOfArgs() {
    return 0;
  }

  @Override
  public Set<String> getValidOptions() {
    return Set.of();
  }

  @Override
  public String execute(Command command, FileSystem fileSystem) {
    if (isCommandInvalid(command)) {
      throw new IllegalArgumentException("Invalid command");
    }
    return getPath(fileSystem.getCurrent());
  }

  @Override
  public String getPrint(String str) {
    return "";
  }

  private String getPath(Directory current) {
    if (current.isRoot()) {
      return "";
    }
    return getPath(current.getParent()) + "/" + current.getName();
  }
}
