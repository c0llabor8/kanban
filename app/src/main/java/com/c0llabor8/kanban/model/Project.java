package com.c0llabor8.kanban.model;

import android.util.Log;
import com.parse.FindCallback;
import com.parse.ParseClassName;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@ParseClassName("Project")
public class Project<callback> extends ParseObject {

  public static final String KEY_NAME = "name";
  public static final String KEY_MEMBERS = "members";
  public static final String KEY_TASKS = "tasks";
  public static final String KEY_DEADLINE = "deadline";

  public static void queryUserProjects(FindCallback<Project> callback) {
    Membership.Query query = new Membership.Query();
    query.whereUserEquals(ParseUser.getCurrentUser());

    query.findInBackground((memberships, e) -> {
      if (e != null) {
        callback.done(null, e);
        return;
      }

      List<Project> projects = new ArrayList<>();

      for (Membership membership : memberships) {
        projects.add(membership.getProject());
      }

      callback.done(projects, null);
    });
  }

  public void getAllTasks() {
    Task.Query query = new Task.Query();
    query.whereProjectEquals(this);

    query.findInBackground((tasks, e) -> {
      if (e != null) {
        e.printStackTrace();
        return;
      }

      List<Project> task = new ArrayList<>();
      for (Task projectTasks : tasks) {
        task.add(projectTasks.getProject());
      }
    });
  }

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

  public static class Query extends ParseQuery<Project> {

    public Query() {
      super(Project.class);
    }
  }

}
