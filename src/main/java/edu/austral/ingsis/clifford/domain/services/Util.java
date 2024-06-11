package edu.austral.ingsis.clifford.domain.services;

import edu.austral.ingsis.clifford.domain.entities.Directory;
import edu.austral.ingsis.clifford.domain.interfaces.FileSystemObject;

public class Util {
    public static FileSystemObject findByName(Directory current, String name) {
        for (FileSystemObject child : current.getChildren()) {
            if (child.getName().equals(name)) {
                return child;
            }
        }
        throw new IllegalArgumentException();
    }
}
