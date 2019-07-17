package com.c0llabor8.kanban.model;

import com.parse.ParseClassName;
import com.parse.ParseObject;

@ParseClassName("Task")
public class Task extends ParseObject {

  public static final String KEY_TITLE = "title";
  public static final String KEY_DESCRIPTION = "description";
  public static final String KEY_ESTIMATE = "estimate";
  public static final String KEY_PRIORITY = "priority";
  public static final String KEY_PROJECT = "project";


  // public default constructor
  public Task() {
    super();
  }

  public String getTitle() {
    return getString(KEY_TITLE);
  }

  public void setTitle(String title) {
    put(KEY_TITLE, title);
  }

  public String getDescription() {
    return getString(KEY_DESCRIPTION);
  }

  public void setDescription(String description) {
    put(KEY_DESCRIPTION, description);
  }

  public String getEstimate() {
    return getString(KEY_ESTIMATE);
  }

  public void setEstimate(String estimate) {
    put(KEY_ESTIMATE, estimate);
  }

  public int getPriority() {
    return getInt(KEY_PRIORITY);
  }

  public void setPriority(String priority) {
    put(KEY_PRIORITY, priority);
  }

  public ParseObject getProject() {
    return getParseObject(KEY_PROJECT);
  }

  public void setProject(ParseObject project) {
    put(KEY_PROJECT, project);
  }

}
