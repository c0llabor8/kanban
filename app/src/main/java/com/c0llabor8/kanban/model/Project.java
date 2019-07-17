package com.c0llabor8.kanban.model;

import com.parse.ParseObject;
import java.util.Date;

public class Project extends ParseObject {

  public static final String KEY_NAME = "name";
  public static final String KEY_MEMBERS = "members";
  public static final String KEY_TASKS = "tasks";
  public static final String KEY_DEADLINE = "deadline";


  public String getName() {
    return getString(KEY_NAME);
  }
  public void setTitle(String name) {
    put(KEY_NAME, name);
  }

  public int getMembers() {
    return getInt(KEY_MEMBERS);
  }

  public void setMembers(String members) {
    put(KEY_MEMBERS, members);
  }

  public int getTasks() {
    return getInt(KEY_TASKS);
  }

  public void setTasks(String estimate) {
    put(KEY_TASKS, estimate);
  }

  public Date getDeadline() {
    return getDate(KEY_DEADLINE);
  }

  public void setDeadline(String deadline) {
    put(KEY_DEADLINE, deadline);
  }
}
