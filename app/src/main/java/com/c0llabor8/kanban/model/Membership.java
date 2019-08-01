package com.c0llabor8.kanban.model;


import com.c0llabor8.kanban.model.query.AssignmentQuery;
import com.c0llabor8.kanban.util.MemberProvider;
import com.parse.DeleteCallback;
import com.parse.ParseClassName;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import java.util.List;

@ParseClassName("Membership")
public class Membership extends ParseObject {

  public static final String KEY_USER = "user";
  public static final String KEY_PROJECT = "project";

  /**
   * Invite and add a user to a project by email
   *
   * @param callback called once the user is added
   */
  public static void invite(String username, Project project, SaveCallback callback) {
    if (!MemberProvider.getInstance().getUsernameMap(project).containsKey(username)) {
      ParseUser.getQuery().whereEqualTo("username", username).findInBackground(
          (List<ParseUser> user, ParseException e) -> {
            if (e != null) {
              callback.done(e);
              return;
            }

            if (user.size() == 0) {
              callback.done(new ParseException(0, "Sorry, user not found"));
              return;
            }

            Membership membership = new Membership().setUser(user.get(0)).setProject(project);

            project.increment(Project.KEY_MEMBERS);
            project.saveInBackground();

            membership.saveInBackground(callback);
          });
    } else {
      callback.done(new ParseException(0, "User is already a member"));
    }
  }

  static void leave(Membership membership, DeleteCallback callback) {
    AssignmentQuery query = new AssignmentQuery()
        .whereUserEquals(membership.getUser())
        .whereProjectEquals(membership.getProject());

    query.findInBackground((List<Assignment> objects, ParseException e) -> {
      for (Assignment assignment : objects) {
        assignment.deleteInBackground();
      }

      membership.deleteInBackground(callback);
    });
  }

  public ParseUser getUser() {
    return getParseUser(KEY_USER);
  }

  public Membership setUser(ParseUser user) {
    put(KEY_USER, user);
    return this;
  }

  public Project getProject() {
    return (Project) getParseObject(KEY_PROJECT);
  }

  public Membership setProject(Project project) {
    put(KEY_PROJECT, project);
    return this;
  }
}
