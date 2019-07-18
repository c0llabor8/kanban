package com.c0llabor8.kanban.model;

import com.parse.ParseClassName;
import com.parse.ParseObject;
import java.util.Date;

@ParseClassName("Project")
public class Project extends ParseObject {

  public static final String KEY_NAME = "name";
  public static final String KEY_MEMBERS = "members";
  public static final String KEY_TASKS = "tasks";
  public static final String KEY_DEADLINE = "deadline";


  public String getName() {
    return getString(KEY_NAME);
  }

  public Project setTitle(String name) {
    put(KEY_NAME, name);
    return this;
  }

  public int getMembers() {
    return getInt(KEY_MEMBERS);
  }

  public Project setMembers(int count) {
    put(KEY_MEMBERS, count);
    return this;
  }

  public int getTasks() {
    return getInt(KEY_TASKS);
  }

  public Project setTasks(int count) {
    put(KEY_TASKS, count);
    return this;
  }

  public Date getDeadline() {
    return getDate(KEY_DEADLINE);
  }

  public void setDeadline(String deadline) {
    put(KEY_DEADLINE, deadline);
  }
}
