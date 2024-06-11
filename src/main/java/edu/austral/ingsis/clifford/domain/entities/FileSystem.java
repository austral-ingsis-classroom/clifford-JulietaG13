package edu.austral.ingsis.clifford.domain.entities;

import edu.austral.ingsis.clifford.domain.interfaces.FileSystemAction;
import edu.austral.ingsis.clifford.domain.services.Parser;
import java.util.List;

public class FileSystem {
  private List<FileSystemAction> actions;
  private Directory root;
  private Directory current;

  public FileSystem(List<FileSystemAction> actions) {
    this.actions = actions;
    this.root = Directory.getRootDirectory();
    this.current = root;
  }

  public String execute(String command) {
    List<String> parsed = Parser.parse(command);

    for (FileSystemAction a : actions) {
      if (a.isCommandValid(parsed)) {
        return a.execute(parsed, this);
      }
    }
    return "";
  }

  public Directory getRoot() {
    return root;
  }

  public void setCurrent(Directory current) {
    this.current = current;
  }

  public Directory getCurrent() {
    return current;
  }
}
