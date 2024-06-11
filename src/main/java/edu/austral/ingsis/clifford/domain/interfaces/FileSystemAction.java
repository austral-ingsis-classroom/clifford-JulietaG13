package edu.austral.ingsis.clifford.domain.interfaces;

import edu.austral.ingsis.clifford.domain.entities.FileSystem;

import java.util.List;

public interface FileSystemAction {
    String getName();

    String execute(List<String> command, FileSystem fileSystem);

    boolean isCommandValid(List<String> command);
}
