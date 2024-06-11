package edu.austral.ingsis.clifford.domain.entities;

import edu.austral.ingsis.clifford.domain.interfaces.FileSystemObject;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class Directory implements FileSystemObject {
  private final Instant creationDate;
  private String name;
  private Directory parent;
  private List<FileSystemObject> children = new ArrayList<>();

  public Directory(String name) {
    if (isNameInvalid(name)) {
      throw new IllegalArgumentException("Invalid file name: " + name);
    }

    this.creationDate = Instant.now();
    this.name = name;
  }

  private Directory() { // root
    this.name = "/";
    this.creationDate = Instant.now();
    this.parent = null;
  }

  public static Directory getRootDirectory() {
    return new Directory();
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
  public void setParent(Directory parent) {
    this.parent = parent;
  }

  @Override
  public Directory getParent() {
    return parent;
  }

  @Override
  public List<FileSystemObject> getChildren() {
    return children;
  }

  public void addChild(FileSystemObject child) {
    child.setParent(this);
    children.add(child);
  }

  public void removeChild(FileSystemObject child) {
    child.setParent(null);
    children.remove(child);
  }
}
