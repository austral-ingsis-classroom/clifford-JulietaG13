package edu.austral.ingsis.clifford.domain.actions;

import edu.austral.ingsis.clifford.domain.entities.Directory;
import edu.austral.ingsis.clifford.domain.entities.File;
import edu.austral.ingsis.clifford.domain.entities.FileSystem;
import edu.austral.ingsis.clifford.domain.interfaces.FileSystemAction;
import edu.austral.ingsis.clifford.domain.interfaces.FileSystemObject;
import edu.austral.ingsis.clifford.domain.services.Util;
import java.util.List;

public class Rm implements FileSystemAction {
  private final List<String> options = List.of("--recursive");

  @Override
  public String getName() {
    return "rm";
  }

  @Override
  public String execute(List<String> command, FileSystem fileSystem) {
    if (!isCommandValid(command)) {
      throw new IllegalArgumentException();
    }

    switch (command.size()) {
      case 2: 
        name = command.get(1);
        break;
      case 3: 
        name = command.get(2);
        break;
      default:
        throw new IllegalArgumentException();
    }

    Directory current = fileSystem.getCurrent();
    FileSystemObject obj = Util.findByName(current, name);

    if (obj instanceof File) {
      return removeFile((File) obj, current);
    } else if (obj instanceof Directory) {
      return removeDir((Directory) obj, current, isRecursive(command));
    }
    throw new IllegalArgumentException();
  }

  @Override
  public boolean isCommandValid(List<String> command) {
    return !command.isEmpty()
        && command.get(0).equals(getName())
        && ((command.size() == 3 && isOptionValid(command.get(1))) || command.size() == 2);
  }

  private boolean isOptionValid(String option) {
    return options.contains(option);
  }

  private boolean isRecursive(List<String> command) {
    if (command.size() == 2) {
      return false;
    }
    return command.get(1).equals("--recursive");
  }

  private String removeFile(File file, Directory current) {
    current.removeChild(file);
    return getPrint(file.getName());
  }

  private String removeDir(Directory dir, Directory current, boolean isRecursive) {
    if (isRecursive) {
      current.removeChild(dir);
      return getPrint(dir.getName());
    }
    return "cannot remove '" + dir.getName() + "', is a directory";
  }

  private String getPrint(String name) {
    return "'" + name + "' removed";
  }
}
