package edu.austral.ingsis.clifford.domain.interfaces;

import edu.austral.ingsis.clifford.domain.data.Command;
import edu.austral.ingsis.clifford.domain.entities.FileSystem;
import java.util.Set;

public interface FileSystemAction {
  String getName();

  int getNumberOfArgs();

  Set<String> getValidOptions();

  String execute(Command command, FileSystem fileSystem);

  String getPrint(String str);

  default boolean isCommandValid(Command command) {
    return command.getName().equals(getName())
        && getValidOptions().containsAll(command.getOptions())
        && getNumberOfArgs() == command.getArgs().size();
  }

  default boolean isCommandInvalid(Command command) {
    return !isCommandValid(command);
  }
}
