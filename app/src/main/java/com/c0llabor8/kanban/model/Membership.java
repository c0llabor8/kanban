package com.c0llabor8.kanban.model;


import com.parse.ParseClassName;
import com.parse.ParseObject;
import com.parse.ParseUser;

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

}
