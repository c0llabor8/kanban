package com.c0llabor8.kanban.model;


import android.media.MediaMetadata;
import com.parse.ParseClassName;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;

@ParseClassName("Membership")
public class Membership extends ParseObject {

  public static final String KEY_USER = "user";
  public static final String KEY_PROJECT = "project";

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

  public static void join(ParseUser user, Project project, SaveCallback callback) {
    Membership membership = new Membership()
        .setUser(user)
        .setProject(project);

    membership.saveInBackground(callback);
  }

  public static class Query extends ParseQuery<Membership> {

    public Query() {
      super(Membership.class);
      include(KEY_USER);
      include(KEY_PROJECT);
    }

    public Query whereUserEquals(ParseUser user) {
      whereEqualTo(KEY_USER, user);
      return this;
    }

    public Query whereProjectEquals(Project project) {
      whereEqualTo(KEY_PROJECT, project);
      return this;
    }
  }
}
