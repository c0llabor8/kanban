package com.c0llabor8.kanban.model;

import com.c0llabor8.kanban.model.query.MembershipQuery;
import com.c0llabor8.kanban.model.query.TaskQuery;
import com.parse.DeleteCallback;
import com.parse.FindCallback;
import com.parse.ParseClassName;
import com.parse.ParseException;
import com.parse.ParseObject;
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

    MembershipQuery query = new MembershipQuery();
    query.whereUserEquals(ParseUser.getCurrentUser()).findInBackground((memberships, e) -> {
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
        callback.done(e);
        return;
      }

      Membership.invite(ParseUser.getCurrentUser().getUsername(), project, callback);
    });
  }

  public void getAllMembers(FindCallback<Membership> callback) {
    MembershipQuery query = new MembershipQuery().whereProjectEquals(this);
    query.findInBackground(callback);
  }

  public void rename(String name, SaveCallback callback) {
    this.setTitle(name).saveInBackground(callback);
  }

  /**
   * Have the current user leave this project
   *
   * @param callback called once the project is left
   */
  public void leave(DeleteCallback callback) {
    MembershipQuery query = new MembershipQuery();

    query.whereUserEquals(ParseUser.getCurrentUser())
        .whereProjectEquals(this)
        .findInBackground((List<Membership> memberships, ParseException e) -> {
          if (e != null) {
            callback.done(e);
          }

          for (Membership membership : memberships) {
            Membership.leave(membership, err -> {
              if (err != null) {
                callback.done(err);
                return;
              }

              this.increment(KEY_MEMBERS, -1);

              if (this.getMembers() == 0) {
                this.deleteEventually();
              }

              callback.done(null);
            });
          }
        });
  }

  /**
   * Get all tasks within the project
   *
   * @param callback called with the resulting task list
   */
  public void getAllTasks(FindCallback<Task> callback) {
    TaskQuery query = new TaskQuery();
    query.whereProjectEquals(this).findInBackground(callback);
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
}
