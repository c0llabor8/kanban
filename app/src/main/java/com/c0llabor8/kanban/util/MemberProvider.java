package com.c0llabor8.kanban.util;

import com.c0llabor8.kanban.model.Membership;
import com.c0llabor8.kanban.model.Project;
import com.parse.FindCallback;
import com.parse.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * This class is a singleton instance that store all memberships for a given project
 */
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

  /**
   * Finds all membership database instances for a given project and stores them in a map of Project
   * to List<Membership>
   *
   * @param project project to be used as a key
   * @param callback called once the queries are complete
   */
  public void updateMembers(Project project, FindCallback<Membership> callback) {
    // Can't search for a membership without a project
    if (project == null) {
      callback.done(null, null);
      return;
    }

    // Execute the query then call our callback method once done
    project.getAllMembers((List<Membership> objects, ParseException e) -> {
      if (e != null) {
        callback.done(null, e);
        return;
      }

      getMemberList(project).clear();
      getMemberList(project).addAll(objects);
      callback.done(objects, null);
    });
  }

  /**
   * Returns a list of all memberships for a given project
   *
   * @param project project to get memberships for
   * @return List of all memberships for a specific project
   */
  public List<Membership> getMemberList(Project project) {
    // Avoid getting a null list of memberships
    if (memberMap.get(project) == null) {
      memberMap.put(project, new ArrayList<>());
    }

    return memberMap.get(project);
  }
}
