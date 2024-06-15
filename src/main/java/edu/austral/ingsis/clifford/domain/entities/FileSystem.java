package edu.austral.ingsis.clifford.domain.entities;

import edu.austral.ingsis.clifford.domain.actions.Cd;
import edu.austral.ingsis.clifford.domain.actions.Ls;
import edu.austral.ingsis.clifford.domain.actions.MkDir;
import edu.austral.ingsis.clifford.domain.actions.Pwd;
import edu.austral.ingsis.clifford.domain.actions.Rm;
import edu.austral.ingsis.clifford.domain.actions.Touch;
import edu.austral.ingsis.clifford.domain.data.Command;
import edu.austral.ingsis.clifford.domain.interfaces.FileSystemAction;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FileSystem {
  private Directory root;
  private Directory current;

  private List<FileSystemAction> actionList;
  private Map<String, FileSystemAction> actions = new HashMap<>();

  public FileSystem() {
    this.root = Directory.getRootDirectory();
    this.current = root;

    actionList = List.of(new Cd(), new Ls(), new MkDir(), new Pwd(), new Rm(), new Touch());
    actionList.forEach(action -> actions.put(action.getName(), action));
  }

  public String execute(String command) {
    Command c = new Command(command);
    FileSystemAction action = actions.get(c.getName());

    if (action == null) {
      return "Error";
    }
    return action.execute(c, this);
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
