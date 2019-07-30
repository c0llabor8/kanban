package com.c0llabor8.kanban.model;

import com.parse.ParseClassName;
import com.parse.ParseObject;
import com.parse.ParseUser;

@ParseClassName("Assignment")
public class Assignment extends ParseObject {

  public static final String KEY_USER = "user";
  public static final String KEY_TASK = "task";

  public ParseUser getUser() {
    return getParseUser(KEY_USER);
  }

  public void setUser(ParseUser user) {
    put(KEY_USER, user);
  }

  public Task getTask() {
    return (Task) getParseObject(KEY_TASK);
  }

  public void setTask(Task task) {
    put(KEY_TASK, task);
  }
}
