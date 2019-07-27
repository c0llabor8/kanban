package com.c0llabor8.kanban.model;


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
  public static void invite(String email, Project project, SaveCallback callback) {
    ParseUser.getQuery().whereEqualTo("email", email).findInBackground(
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
