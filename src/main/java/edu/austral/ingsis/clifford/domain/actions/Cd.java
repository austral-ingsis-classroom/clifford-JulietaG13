package edu.austral.ingsis.clifford.domain.actions;

import edu.austral.ingsis.clifford.domain.data.Command;
import edu.austral.ingsis.clifford.domain.entities.Directory;
import edu.austral.ingsis.clifford.domain.entities.FileSystem;
import edu.austral.ingsis.clifford.domain.interfaces.FileSystemAction;
import edu.austral.ingsis.clifford.domain.interfaces.FileSystemObject;
import edu.austral.ingsis.clifford.domain.services.Util;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

public class Cd implements FileSystemAction {
  private static final int PATH = 0;

  private Set<String> options = Set.of();

  @Override
  public String getName() {
    return "cd";
  }

  @Override
  public int getNumberOfArgs() {
    return 1;
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

    String pathStr = command.getArgs().get(PATH);

    try {
      List<String> path = decodePath(pathStr);
      Directory newCurrent = getNewCurrent(path, fileSystem);
      fileSystem.setCurrent(newCurrent);

      return getPrint(newCurrent.getName());
    } catch (Exception ignored) {
      return "'" + pathStr + "' directory does not exist";
    }
  }

  @Override
  public String getPrint(String dir) {
    return "moved to directory '" + dir + "'";
  }

  private List<String> decodePath(String path) {
    // Arrays.stream(path.split("/")).toList()
    String[] parts = path.split("/");
    return Arrays.asList(parts);
  }

  private Directory getNewCurrent(List<String> path, FileSystem fileSystem) {
    Directory current = fileSystem.getCurrent();
    if (path.isEmpty()) {
      return fileSystem.getRoot();
    }
    /*
    target =
        switch (path.get(0)) {
          case ".." -> current.getParent() == null ? current : current.getParent();
          case "." -> current;
          case "" -> moveTo(path, current, 1);
          default -> moveTo(path, fileSystem.getRoot(), 0);
        };
     */
    String first = path.get(0);

    if (first.equals("..")) {
      return current.getParent() == null ? current : current.getParent();
    }
    if (first.equals(".")) {
      return current;
    }
    if (first.equals("")) {
      return moveTo(path, current, 1);
    }
    return moveTo(path, fileSystem.getRoot(), 0);
  }

  private Directory moveTo(List<String> path, Directory current, int level) {
    if (level == path.size() - 1) {
      FileSystemObject obj = Util.findByName(current, path.get(level));
      if (obj.hasChildren()) {
        return (Directory) obj;
      }
      throw new IllegalArgumentException();
    }

    FileSystemObject next = Util.findByName(current, path.get(level));
    if (next.hasChildren()) {
      return moveTo(path, (Directory) next, level + 1);
    }
    throw new IllegalArgumentException();
  }
}
