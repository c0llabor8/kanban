package com.c0llabor8.kanban.model;

import com.parse.ParseClassName;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import java.util.Comparator;

@ParseClassName("Task")
public class Task extends ParseObject {

  public static final String KEY_TITLE = "title";
  public static final String KEY_DESCRIPTION = "description";
  public static final String KEY_ESTIMATE = "estimate";
  public static final String KEY_PROJECT = "project";
  public static final String KEY_COMPLETED = "completed";
  public static final String KEY_CATEGORY = "category";

  public static void createNew(String title, String description, long estimate,
      ParseUser user, Project project, TaskCategory category, SaveCallback callback) {

    Task task =
        new Task().setTitle(title).setDescription(description).setEstimate(estimate);

    if (project != null) {
      task.setProject(project);
    }

    if (category != null) {
      task.setCategory(category);
    }

    task.saveInBackground(e -> {
      Assignment assignment = new Assignment().setTask(task).setUser((user == null) ?
          ParseUser.getCurrentUser() : user);

      if (project != null) {
        assignment.setProject(project);
      }

      assignment.saveInBackground(callback);
    });
  }

  public Task setCompleted() {
    put(KEY_COMPLETED, true);
    return this;
  }

  public Task setIncomplete() {
    put(KEY_COMPLETED, false);
    return this;
  }

  public boolean getCompleted() {
    return getBoolean(KEY_COMPLETED);
  }

  public String getTitle() {
    return getString(KEY_TITLE);
  }

  public Task setTitle(String title) {
    put(KEY_TITLE, title);
    return this;
  }

  public String getDescription() {
    return getString(KEY_DESCRIPTION);
  }

  public Task setDescription(String description) {
    put(KEY_DESCRIPTION, description);
    return this;
  }

  public long getEstimate() {
    return getLong(KEY_ESTIMATE);
  }

  public Task setEstimate(long estimate) {
    put(KEY_ESTIMATE, estimate);
    return this;
  }

  public TaskCategory getCategory() {
    return (TaskCategory) getParseObject(KEY_CATEGORY);
  }

  public Task setCategory(TaskCategory category) {
    put(KEY_CATEGORY, category);
    return this;
  }

  public Task removeCategory() {
    remove(KEY_CATEGORY);
    return this;
  }

  public Project getProject() {
    return (Project) getParseObject(KEY_PROJECT);
  }

  public Task setProject(ParseObject project) {
    put(KEY_PROJECT, project);
    return this;
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
