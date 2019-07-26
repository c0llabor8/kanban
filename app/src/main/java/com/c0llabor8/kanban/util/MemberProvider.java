package com.c0llabor8.kanban.util;

import androidx.annotation.NonNull;
import com.c0llabor8.kanban.model.Membership;
import com.c0llabor8.kanban.model.Project;
import com.parse.FindCallback;
import com.parse.ParseException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class MemberProvider {

  private static MemberProvider instance;
  private HashMap<Project, Set<Membership>> memberMap;

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

      getMemberSet(project).addAll(objects);
      callback.done(objects, null);
    });
  }

  public Set<Membership> getMemberSet(Project project) {
    if (memberMap.get(project) == null) {
      memberMap.put(project, new HashSet<>());
    }

    return memberMap.get(project);
  }
}
