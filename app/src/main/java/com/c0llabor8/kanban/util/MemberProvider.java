package com.c0llabor8.kanban.util;

import com.c0llabor8.kanban.model.Membership;
import com.c0llabor8.kanban.model.Project;
import com.parse.FindCallback;
import com.parse.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MemberProvider {

  private static MemberProvider instance;
  private HashMap<Project, List<Membership>> memberMap;

  private MemberProvider() {
    memberMap = new HashMap<>();
  }

  public static MemberProvider getInstance() {
    if (instance == null) {
      instance = new MemberProvider();
    }

    return instance;
  }

  public void updateMembers(Project project, FindCallback<Membership> callback) {
    if (project == null) {
      callback.done(null, null);
      return;
    }

    project.getAllMembers((List<Membership> objects, ParseException e) -> {
      if (e != null) {
        callback.done(null, e);
        return;
      }

      getMemberList(project).addAll(objects);
      callback.done(objects, null);
    });
  }

  public List<Membership> getMemberList(Project project) {
    if (memberMap.get(project) == null) {
      memberMap.put(project, new ArrayList<>());
    }

    return memberMap.get(project);
  }
}
