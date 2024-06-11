package edu.austral.ingsis;

import edu.austral.ingsis.clifford.domain.actions.*;
import edu.austral.ingsis.clifford.domain.entities.FileSystem;
import edu.austral.ingsis.clifford.domain.interfaces.FileSystemAction;

import java.util.ArrayList;
import java.util.List;

public class MyFileSystemRunner implements FileSystemRunner {

    List<FileSystemAction> actions = List.of(
            new Cd(),
            new Ls(),
            new MkDir(),
            new Touch(),
            new Rm(),
            new Pwd()
    );
    FileSystem fileSystem = new FileSystem(actions);

    @Override
    public List<String> executeCommands(List<String> commands) {
        List<String> result = new ArrayList<>();
        commands.forEach(command -> {
            result.add(fileSystem.execute(command));
        });
        return result;
    }
}
