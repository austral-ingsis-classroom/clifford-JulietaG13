package edu.austral.ingsis.clifford.domain.actions;

import edu.austral.ingsis.clifford.domain.entities.Directory;
import edu.austral.ingsis.clifford.domain.entities.FileSystem;
import edu.austral.ingsis.clifford.domain.interfaces.FileSystemAction;

import java.util.List;

public class MkDir implements FileSystemAction {
    @Override
    public String getName() {
        return "mkdir";
    }

    @Override
    public String execute(List<String> command, FileSystem fileSystem) {
        if (!isCommandValid(command)) {
            throw new IllegalArgumentException();
        }

        Directory current = fileSystem.getCurrent();
        Directory newDir = new Directory(command.get(1));

        current.addChild(newDir);

        return getPrint(newDir.getName());
    }

    @Override
    public boolean isCommandValid(List<String> command) {
        return !command.isEmpty()
                && command.getFirst().equals(getName())
                && command.size() == 2
                && isParamValid(command.get(1));
    }

    private boolean isParamValid(String name) {
        return !Directory.isNameInvalid(name);
    }

    private String getPrint(String name) {
        return "'" + name + "' directory created";
    }
}
