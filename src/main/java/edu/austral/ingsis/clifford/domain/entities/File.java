package edu.austral.ingsis.clifford.domain.entities;

import edu.austral.ingsis.clifford.domain.interfaces.FileSystemObject;

import java.time.Instant;
import java.util.List;

public class File implements FileSystemObject {
    private final Instant creationDate;
    private String name;
    private Directory parent;

    public File(String name) {
        if (isNameInvalid(name)) {
            throw new IllegalArgumentException("Invalid file name: " + name);
        }

        this.creationDate = Instant.now();
        this.name = name;
    }

    public static boolean isNameInvalid(String name) {
        return name == null || name.contains("/") || name.contains(" ");
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Instant getCreationDate() {
        return creationDate;
    }

    @Override
    public Directory getParent() {
        return parent;
    }

    @Override
    public void setParent(Directory parent) {
        this.parent = parent;
    }

    @Override
    public List<FileSystemObject> getChildren() {
        return null;
    }
}
