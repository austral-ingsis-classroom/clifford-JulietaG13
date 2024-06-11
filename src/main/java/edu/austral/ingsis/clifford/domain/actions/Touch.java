package edu.austral.ingsis.clifford.domain.actions;

import edu.austral.ingsis.clifford.domain.entities.Directory;
import edu.austral.ingsis.clifford.domain.entities.File;
import edu.austral.ingsis.clifford.domain.entities.FileSystem;
import edu.austral.ingsis.clifford.domain.interfaces.FileSystemAction;
import edu.austral.ingsis.clifford.domain.interfaces.FileSystemObject;
import edu.austral.ingsis.clifford.domain.services.Util;
import java.util.List;

public class Touch implements FileSystemAction {
  @Override
  public String getName() {
    return "touch";
  }

  @Override
  public String execute(List<String> command, FileSystem fileSystem) {
    if (!isCommandValid(command)) {
      throw new IllegalArgumentException();
    }
    Directory current = fileSystem.getCurrent();

    String name = command.get(1);

    try {
      FileSystemObject previous = Util.findByName(current, name);
      current.removeChild(previous);
    } catch (Exception ignored) {
    }

    File newFile = new File(name);
    current.addChild(newFile);
    return getPrint(name);
  }

  @Override
  public boolean isCommandValid(List<String> command) {
    return !command.isEmpty()
        && command.get(0).equals(getName())
        && command.size() == 2
        && isParamValid(command.get(1));
  }

  private boolean isParamValid(String name) {
    return !File.isNameInvalid(name);
  }

  private String getPrint(String name) {
    return "'" + name + "' file created";
  }
}
