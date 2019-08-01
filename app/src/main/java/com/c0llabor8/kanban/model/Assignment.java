package com.c0llabor8.kanban.model;

import com.parse.ParseClassName;
import com.parse.ParseObject;
import com.parse.ParseUser;

@ParseClassName("Assignment")
public class Assignment extends ParseObject {

  public static final String KEY_USER = "user";
  public static final String KEY_TASK = "task";
  public static final String KEY_PROJECT = "project";

  public ParseUser getUser() {
    return getParseUser(KEY_USER);
  }

  public Assignment setUser(ParseUser user) {
    put(KEY_USER, user);
    return this;
  }

  public Assignment setProject(Project project) {
    put(KEY_PROJECT, project);
    return this;
  }

  public Task getTask() {
    return (Task) getParseObject(KEY_TASK);
  }

  public Assignment setTask(Task task) {
    put(KEY_TASK, task);
    return this;
  }
}
