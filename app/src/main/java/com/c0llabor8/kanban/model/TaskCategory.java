package com.c0llabor8.kanban.model;

import com.c0llabor8.kanban.model.query.TaskQuery;
import com.parse.DeleteCallback;
import com.parse.ParseClassName;
import com.parse.ParseObject;
import com.parse.SaveCallback;
import java.util.Comparator;

@ParseClassName("TaskCategory")
public class TaskCategory extends ParseObject {

  public static String KEY_PROJECT = "project";
  public static String KEY_TITLE = "title";
  public static String KEY_ORDER = "order";

  public static void create(String title, Project project, SaveCallback callback) {
    new TaskCategory().setTitle(title).setProject(project).setOrder(Integer.MAX_VALUE)
        .saveInBackground(callback);
  }

  public Project getProject() {
    return (Project) getParseObject(KEY_PROJECT);
  }

  public TaskCategory setProject(Project project) {
    put(KEY_PROJECT, project);
    return this;
  }

  public void delete(DeleteCallback callback) {
    TaskCategory category = this;
    TaskQuery query = new TaskQuery().whereCategoryEquals(category);

    query.findInBackground((tasks, e) -> {
      for (Task task : tasks) {
        task.removeCategory();
        task.saveInBackground();
      }

      category.deleteInBackground(callback);
    });
  }

  public int getOrder() {
    return getInt(KEY_ORDER);
  }

  public TaskCategory setOrder(int order) {
    put(KEY_ORDER, order);
    return this;
  }

  public String getTitle() {
    return getString(KEY_TITLE);
  }

  public TaskCategory setTitle(String title) {
    put(KEY_TITLE, title);
    return this;
  }

  @Override
  public boolean equals(Object obj) {
    if (obj instanceof TaskCategory) {
      return ((TaskCategory) obj).getObjectId().equals(this.getObjectId());
    }

    return false;
  }

  public static class SortComparator implements Comparator<TaskCategory> {

    @Override
    public int compare(TaskCategory category, TaskCategory o) {
      int result = Integer.compare(category.getOrder(), o.getOrder());

      if (result == 0) {
        return category.getTitle().compareTo(o.getTitle());
      }

      return result;
    }
  }
}