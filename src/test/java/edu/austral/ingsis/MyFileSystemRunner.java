package edu.austral.ingsis;

import edu.austral.ingsis.clifford.domain.entities.FileSystem;
import java.util.ArrayList;
import java.util.List;

public class MyFileSystemRunner implements FileSystemRunner {
  FileSystem fileSystem = new FileSystem();

  @Override
  public List<String> executeCommands(List<String> commands) {
    List<String> result = new ArrayList<>();
    commands.forEach(
        command -> {
          result.add(fileSystem.execute(command));
        });
    return result;
  }
}
