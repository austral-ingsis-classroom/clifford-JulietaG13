package edu.austral.ingsis.clifford.domain.actions;

import edu.austral.ingsis.clifford.domain.data.Command;
import edu.austral.ingsis.clifford.domain.entities.Directory;
import edu.austral.ingsis.clifford.domain.entities.File;
import edu.austral.ingsis.clifford.domain.entities.FileSystem;
import edu.austral.ingsis.clifford.domain.interfaces.FileSystemAction;
import edu.austral.ingsis.clifford.domain.interfaces.FileSystemObject;
import java.util.Set;

public class Touch implements FileSystemAction {
  public static final int NAME = 0;

  @Override
  public String getName() {
    return "touch";
  }

  @Override
  public int getNumberOfArgs() {
    return 1;
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

    Directory current = fileSystem.getCurrent();
    String name = command.getArgs().get(NAME);

    for (FileSystemObject fso : current.getChildren()) {
      if (name.equals(fso.getName())) {
        current.removeChild(fso);
        break;
      }
    }

    File newFile = new File(name);
    current.addChild(newFile);

    return getPrint(newFile.getName());
  }

  @Override
  public String getPrint(String name) {
    return "'" + name + "' file created";
  }
}
