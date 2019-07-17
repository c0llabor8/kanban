package com.c0llabor8.kanban.model;

import com.parse.ParseObject;
import com.parse.ParseUser;

public class Assignment extends ParseObject {
  public static final String KEY_USER = "user";
  public static final String KEY_TASK = "task";

  public ParseUser getUser() {
    return getParseUser(KEY_USER);
  }

  public void setUser(ParseUser user) {
    put(KEY_USER, user);
  }

  public ParseObject getTask() {
    return getParseObject(KEY_TASK);
  }

  public void setTask(ParseObject task) {
    put(KEY_TASK, task);
  }
}
