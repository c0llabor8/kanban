package com.c0llabor8.kanban.model;

import com.parse.ParseClassName;
import com.parse.ParseObject;

@ParseClassName("TaskCategory")
public class TaskCategory extends ParseObject {

  public static String KEY_PROJECT = "project";
  public static String KEY_TITLE = "title";
  public static String KEY_ORDER = "order";

  public Project getProject() {
    return (Project) getParseObject(KEY_PROJECT);
  }

  public void setProject(Project project) {
    put(KEY_PROJECT, project);
  }

  public int getOrder() {
    return getInt(KEY_ORDER);
  }

  public void setOrder(int order) {
    put(KEY_ORDER, order);
  }

  public String getTitle() {
    return getString(KEY_TITLE);
  }

  public void setTitle(String title) {
    put(KEY_TITLE, title);
  }
}