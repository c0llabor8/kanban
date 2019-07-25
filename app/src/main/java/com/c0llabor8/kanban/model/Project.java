package com.c0llabor8.kanban.model;

import com.parse.DeleteCallback;
import com.parse.FindCallback;
import com.parse.ParseClassName;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@ParseClassName("Project")
public class Project extends ParseObject {

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

  public static void create(String title, SaveCallback callback) {
    Project project = new Project()
        .setTitle(title)
        .setTasks(0)
        .setMembers(1);

    project.saveInBackground((ParseException e) -> {
      if (e != null) {
        e.printStackTrace();
        return;
      }

      Membership.join(ParseUser.getCurrentUser(), project, callback);
    });
  }

  public void rename(String name, SaveCallback callback) {
    this.setTitle(name).saveInBackground(callback);
  }

  public void invite(String email, SaveCallback callback) {
    Project project = this;
    ParseUser.getQuery().whereEqualTo("email", email).findInBackground(
        (List<ParseUser> objects, ParseException e) -> {
          if (e != null) {
            e.printStackTrace();
            return;
          }

          Membership.join(objects.get(0), project, callback);
          project.increment(KEY_MEMBERS, 1);
          project.saveInBackground();
        });
  }

  public void leave(DeleteCallback callback) {
    Membership.Query query = new Membership.Query();
    query.whereUserEquals(ParseUser.getCurrentUser());
    query.whereProjectEquals(this);

    query.findInBackground((objects, e) -> {
      if (e != null) {
        callback.done(e);
      }

      objects.get(0).deleteInBackground(err -> {
        if (err != null) {
          callback.done(err);
        }

        this.increment(KEY_MEMBERS, -1);

        if (this.getMembers() == 0) {
          this.deleteEventually();
        }

        callback.done(null);
      });
    });
  }

  public void getAllTasks(FindCallback<Task> callback) {
    (new Task.Query()).whereProjectEquals(this)
        .findInBackground(callback);
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
