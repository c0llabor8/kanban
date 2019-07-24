package com.c0llabor8.kanban.model;

import com.parse.FindCallback;
import com.parse.ParseClassName;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import java.util.ArrayList;
import java.util.List;

@ParseClassName("Task")
public class Task extends ParseObject {

  public static final String KEY_TITLE = "title";
  public static final String KEY_DESCRIPTION = "description";
  public static final String KEY_ESTIMATE = "estimate";
  public static final String KEY_PRIORITY = "priority";
  public static final String KEY_PROJECT = "project";
  public static final String KEY_COMPLETED = "completed";

  public static void queryUserTasks(FindCallback<Task> callback) {
    Assignment.Query query = new Assignment.Query();
    query.whereUserEquals(ParseUser.getCurrentUser());

    query.findInBackground((assignments, e) -> {
      if (e != null) {
        callback.done(null, e);
        return;
      }

      List<Task> tasks = new ArrayList<>();

      for (Assignment assignment : assignments) {
        tasks.add(assignment.getTask());
      }
      callback.done(tasks, null);
    });
  }

  public void setCompleted() {
    put(KEY_COMPLETED, true);
  }

  public void setInomplete() {
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

  public Project getProject() {
    return (Project) getParseObject(KEY_PROJECT);
  }

  public void setProject(ParseObject project) {
    put(KEY_PROJECT, project);
  }

  public static class Query extends ParseQuery<Task> {

    public Query() {
      super(Task.class);
      include(KEY_DESCRIPTION);
      include(KEY_TITLE);
      include(KEY_PROJECT);
    }

    public Query sortAscending() {
      addAscendingOrder(KEY_ESTIMATE);
      return this;
    }

    public Query whereProjectEquals(Project project) {
      whereEqualTo(KEY_PROJECT, project);
      return this;
    }
  }

}
