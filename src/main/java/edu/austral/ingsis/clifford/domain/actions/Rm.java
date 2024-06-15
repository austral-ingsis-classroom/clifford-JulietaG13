package edu.austral.ingsis.clifford.domain.actions;

import edu.austral.ingsis.clifford.domain.data.Command;
import edu.austral.ingsis.clifford.domain.entities.Directory;
import edu.austral.ingsis.clifford.domain.entities.File;
import edu.austral.ingsis.clifford.domain.entities.FileSystem;
import edu.austral.ingsis.clifford.domain.interfaces.FileSystemAction;
import edu.austral.ingsis.clifford.domain.interfaces.FileSystemObject;
import java.util.Set;

public class Rm implements FileSystemAction {
  private static final int NAME = 0;

  private Set<String> options = Set.of("recursive");

  @Override
  public String getName() {
    return "rm";
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

    Directory current = fileSystem.getCurrent();
    String name = command.getArgs().get(NAME);

    for (FileSystemObject fso : current.getChildren()) {
      if (fso.getName().equals(name)) {
        if (fso instanceof Directory) {
          return removeDir((Directory) fso, current, command.getOptions());
        }
        if (fso instanceof File) {
          return removeFile((File) fso, current);
        }
      }
    }

    throw new IllegalArgumentException("No such file or directory");
  }

  @Override
  public String getPrint(String name) {
    return "'" + name + "' removed";
  }

  private boolean isRecursive(Set<String> options) {
    return options.contains("recursive");
  }

  private String removeFile(File file, Directory current) {
    current.removeChild(file);
    return getPrint(file.getName());
  }

  private String removeDir(Directory dir, Directory current, Set<String> options) {
    if (isRecursive(options)) {
      current.removeChild(dir);
      return getPrint(dir.getName());
    }
    return "cannot remove '" + dir.getName() + "', is a directory";
  }
}
