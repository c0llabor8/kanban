package com.c0llabor8.kanban.util;

import com.c0llabor8.kanban.model.Membership;
import com.c0llabor8.kanban.model.Project;
import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseUser;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

/**
 * This class is a singleton instance that store all memberships for a given project
 */
public class MemberProvider {

  private static MemberProvider instance;
  private HashMap<String, List<ParseUser>> memberMap;
  private HashMap<String, HashMap<String, ParseUser>> usernameSetMap;

  private MemberProvider() {
    memberMap = new HashMap<>();
    usernameSetMap = new HashMap<>();
  }

  public static MemberProvider getInstance() {
    if (instance == null) {
      instance = new MemberProvider();
    }

    return instance;
  }

  public static ParseUser getParseUser(Project project, String username) {
    return project == null ? null : getInstance().getUsernameMap(project).get(username);
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
      getUsernameMap(project).clear();

      for (Membership membership : objects) {
        membership.getUser().fetchIfNeededInBackground(
            (GetCallback<ParseUser>) (user, err) -> {
              getMemberList(project).add(user);
              getUsernameMap(project).put(user.getUsername(), user);
            }
        );
      }

      Collections.sort(getMemberList(project), new SortByUsername());
      callback.done(objects, null);
    });
  }

  /**
   * Returns a list of all memberships for a given project
   *
   * @param project project to get memberships for
   * @return List of all memberships for a specific project
   */
  public List<ParseUser> getMemberList(Project project) {
    String hash = (project == null) ? null : project.getObjectId();
    // Avoid getting a null list of memberships
    if (memberMap.get(hash) == null) {
      memberMap.put(hash, new ArrayList<>());
    }

    return memberMap.get(hash);
  }

  /**
   * Returns a list of all memberships for a given project
   *
   * @param project project to get memberships for
   * @return List of all memberships for a specific project
   */
  public List<String> getUsernameList(Project project) {
    List<String> result = new ArrayList<>(getUsernameMap(project).keySet());
    Collections.sort(result);
    return result;
  }

  /**
   * Returns a list of all memberships for a given project
   *
   * @param project project to get memberships for
   * @return List of all memberships for a specific project
   */
  public HashMap<String, ParseUser> getUsernameMap(Project project) {
    String hash = (project == null) ? null : project.getObjectId();
    // Avoid getting a null list of memberships
    if (usernameSetMap.get(hash) == null) {
      usernameSetMap.put(hash, new HashMap<>());
    }

    return usernameSetMap.get(hash);
  }

  public class SortByUsername implements Comparator<ParseUser> {

    @Override
    public int compare(ParseUser parseUser, ParseUser o) {
      return parseUser.getUsername().compareTo(o.getUsername());
    }
  }
}
