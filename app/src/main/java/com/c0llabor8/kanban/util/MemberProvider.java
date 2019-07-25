package com.c0llabor8.kanban.util;

import com.c0llabor8.kanban.model.Project;
import com.c0llabor8.kanban.model.Task;
import com.parse.FindCallback;
import com.parse.ParseUser;
import java.util.HashMap;
import java.util.Set;

public class MemberProvider {

  private static MemberProvider instance;
  private HashMap<Project, Set<ParseUser>> userMap;

  private MemberProvider() {
    userMap = new HashMap<>();
  }

  public static MemberProvider getInstance() {
    if (instance == null) {
      instance = new MemberProvider();
    }

    return instance;
  }

  public void updateMembers(Project project, FindCallback<Task> callback) {
    // TODO: Get all member for a task
  }
}
