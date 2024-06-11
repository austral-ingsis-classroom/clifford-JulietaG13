package edu.austral.ingsis.clifford.domain.actions;

import edu.austral.ingsis.clifford.domain.entities.Directory;
import edu.austral.ingsis.clifford.domain.entities.FileSystem;
import edu.austral.ingsis.clifford.domain.interfaces.FileSystemAction;
import edu.austral.ingsis.clifford.domain.interfaces.FileSystemObject;
import edu.austral.ingsis.clifford.domain.services.Util;

import java.util.Arrays;
import java.util.List;

public class Cd implements FileSystemAction {
    @Override
    public String getName() {
        return "cd";
    }

    @Override
    public String execute(List<String> command, FileSystem fileSystem) {
        if (!isCommandValid(command)) {
            throw new IllegalArgumentException();
        }

        Directory target;

        try {
            List<String> path = Arrays.stream(command.get(1).split("/")).toList();

            if (command.get(1).equals("/")) {
                target = fileSystem.getRoot();
            } else {
                Directory current = fileSystem.getCurrent();
                target = switch (path.getFirst()) {
                    case ".." -> current.getParent() == null ? current : current.getParent();
                    case "." -> current;
                    case "" -> moveTo(path, current, 1);
                    default -> moveTo(path, fileSystem.getRoot(), 0);
                };
            }
        } catch (IllegalArgumentException ignored) {
            return "'" + command.get(1) + "' directory does not exist";
        }

        fileSystem.setCurrent(target);
        return getPrint(target.getName());
    }

    @Override
    public boolean isCommandValid(List<String> command) {
        return !command.isEmpty()
                && command.getFirst().equals(getName())
                && command.size() == 2;
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

    private String getPrint(String dir) {
        return "moved to directory '" + dir + "'";
    }
}
