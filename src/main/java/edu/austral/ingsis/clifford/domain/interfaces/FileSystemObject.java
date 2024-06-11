package edu.austral.ingsis.clifford.domain.interfaces;

import edu.austral.ingsis.clifford.domain.entities.Directory;
import java.time.Instant;
import java.util.List;

public interface FileSystemObject extends Comparable<FileSystemObject> {
  String getName();

  Instant getCreationDate();

  default boolean isRoot() {
    return getParent() == null;
  }

  void setParent(Directory parent);

  Directory getParent();

  default boolean hasChildren() {
    return getChildren() != null;
  }

  List<FileSystemObject> getChildren();

  @Override
  default int compareTo(FileSystemObject other) {
    return this.getCreationDate().compareTo(other.getCreationDate());
  }
}
