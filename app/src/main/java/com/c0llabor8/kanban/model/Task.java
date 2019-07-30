package com.c0llabor8.kanban.model;

import com.parse.ParseClassName;
import com.parse.ParseObject;
import java.util.Comparator;

@ParseClassName("Task")
public class Task extends ParseObject {

  public static final String KEY_TITLE = "title";
  public static final String KEY_DESCRIPTION = "description";
  public static final String KEY_ESTIMATE = "estimate";
  public static final String KEY_PRIORITY = "priority";
  public static final String KEY_PROJECT = "project";
  public static final String KEY_COMPLETED = "completed";
  public static final String KEY_CATEGORY = "category";

  public void setCompleted() {
    put(KEY_COMPLETED, true);
  }

  public void setIncomplete() {
    put(KEY_COMPLETED, false);
  }

  public boolean getCompleted() {
    return getBoolean(KEY_COMPLETED);
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

  public long getEstimate() {
    return getLong(KEY_ESTIMATE);
  }

  public void setEstimate(long estimate) {
    put(KEY_ESTIMATE, estimate);
  }

  public int getPriority() {
    return getInt(KEY_PRIORITY);
  }

  public void setPriority(int priority) {
    put(KEY_PRIORITY, priority);
  }

  public TaskCategory getCategory() {
    return (TaskCategory) getParseObject(KEY_CATEGORY);
  }

  public Task setCategory(TaskCategory category) {
    put(KEY_CATEGORY, category);
    return this;
  }

  public void removeCategory() {
    remove(KEY_CATEGORY);
  }

  public Project getProject() {
    return (Project) getParseObject(KEY_PROJECT);
  }

  public void setProject(ParseObject project) {
    put(KEY_PROJECT, project);
  }

  public static class SortByCategory implements Comparator<Task> {

    @Override
    public int compare(Task task, Task o) {
      TaskCategory category = task.getCategory();
      TaskCategory oCategory = o.getCategory();

      if (category == null && oCategory == null) {
        return Long.compare(task.getEstimate(), o.getEstimate());
      }

      if (category == null || oCategory == null) {
        return (category == null) ? -1 : 1;
      }

      int result = Integer.compare(category.getOrder(), oCategory.getOrder());

      if (result == 0) {
        result = category.getTitle().compareTo(oCategory.getTitle());

        if (result == 0) {
          result = Long.compare(task.getEstimate(), o.getEstimate());
        }
      }

      return result;
    }
  }

  public static class SortByDeadline implements Comparator<Task> {

    @Override
    public int compare(Task task, Task o) {
      return Long.compare(task.getEstimate(), o.getEstimate());
    }
  }
}
