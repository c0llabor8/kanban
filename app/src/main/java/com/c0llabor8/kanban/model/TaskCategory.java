package com.c0llabor8.kanban.model;

import com.parse.ParseClassName;
import com.parse.ParseObject;
import com.parse.ParseQuery;

@ParseClassName("TaskCategory")
public class TaskCategory extends ParseObject {
  public static String KEY_PROJECT = "project";
  public static String KEY_TITLE = "title";
  public static String KEY_ORDER = "order";

  public void setProject(Project project) {
    put(KEY_PROJECT, project);
  }

  public Project getProject() {
    return (Project) getParseObject(KEY_PROJECT);
  }

  public void setOrder(int order) {
    put(KEY_ORDER, order);
  }

  public int getOrder() {
    return getInt(KEY_ORDER);
  }

  public void setTitle(String title) {
    put(KEY_TITLE, title);
  }

  public String getTitle() {
    return getString(KEY_TITLE);
  }

  public static class Query extends ParseQuery<TaskCategory> {

    public Query() {
      super(TaskCategory.class);
      include(KEY_TITLE);
      include(KEY_PROJECT);
    }

    public Query whereProjectEquals(Project project) {
      whereEqualTo(KEY_PROJECT, project);
      return this;
    }
  }
}