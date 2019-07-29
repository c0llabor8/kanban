package com.c0llabor8.kanban.util;

import com.c0llabor8.kanban.model.Project;
import com.c0llabor8.kanban.model.Task;
import com.c0llabor8.kanban.model.TaskCategory;
import com.c0llabor8.kanban.model.query.TaskCategoryQuery;
import com.c0llabor8.kanban.model.query.TaskQuery;
import com.parse.FindCallback;
import com.parse.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

/**
 * This class is a singleton instance that stores all Tasks for each project and their categories
 */
public class TaskProvider {

  private static TaskProvider instance;
  private HashMap<Project, List<Task>> taskMap;
  private HashMap<Project, List<Task>> categorizedTaskMap;
  private HashMap<Project, List<TaskCategory>> taskCategoryMap;

  private TaskProvider() {
    taskMap = new HashMap<>();
    categorizedTaskMap = new HashMap<>();
    taskCategoryMap = new HashMap<>();
  }

  public static TaskProvider getInstance() {
    if (instance == null) {
      instance = new TaskProvider();
    }

    return instance;
  }

  /**
   * Query for all tasks given a project, then caches them in a list along with the count of all
   * tasks within a given TaskCategory
   *
   * @param project project to get tasks for
   * @param callback called once all tasks found and cached
   */
  public void updateTasks(Project project, FindCallback<Task> callback) {
    // When we aren't in a project scope, simply get all user tasks
    if (project == null) {
      TaskQuery.queryUserTasks((List<Task> tasks, ParseException e) -> {
        if (e != null) {
          callback.done(null, e);
          return;
        }

        // Clear all tasks within the project then insert and sort the newly fetched ones
        getTasks(null).clear();
        getTasks(null).addAll(tasks);
        Collections.sort(getTasks(null), new Task.SortByDeadline());
        callback.done(getTasks(null), null);
      });

      return;
    }

    // Else we get all tasks for the specified project
    project.getAllTasks((List<Task> tasks, ParseException e) -> {
      if (e != null) {
        callback.done(null, e);
        return;
      }

      // Clear all tasks within the project then insert and sort the newly fetched ones
      getTasks(project).clear();
      getTasks(project).addAll(tasks);
      getCategorizedTasks(project).clear();
      getCategorizedTasks(project).addAll(tasks);

      // Sort the tasks with category priority and deadline priority
      Collections.sort(getTasks(project), new Task.SortByDeadline());
      Collections.sort(getCategorizedTasks(project), new Task.SortByCategory());
      callback.done(getTasks(project), null);
    });
  }

  /**
   * Query for all categories within a project scope
   *
   * @param project project to get categories for
   * @param callback called once categories have been cached
   */
  public void updateCategories(Project project, FindCallback<TaskCategory> callback) {
    if (project == null) {
      callback.done(null, null);
      return;
    }

    TaskCategoryQuery query = new TaskCategoryQuery();
    query.whereProjectEquals(project).findInBackground((objects, e) -> {
      getCategories(project).clear();
      getCategories(project).addAll(objects);
      Collections.sort(getCategories(project), new TaskCategory.SortComparator());

      callback.done(getCategories(project), null);
    });
  }

  /**
   * Gets all cached tasks for a given project
   *
   * @param project Project to get all tasks for
   * @return A list of all tasks given a project
   */
  public List<Task> getTasks(Project project) {
    // Avoid getting a null list of tasks
    if (taskMap.get(project) == null) {
      taskMap.put(project, new ArrayList<>());
    }

    return taskMap.get(project);
  }

  /**
   * Gets all cached tasks for a given project in categorized order
   *
   * @param project Project to get all tasks for
   * @return A list of all tasks given a project
   */
  public List<Task> getCategorizedTasks(Project project) {
    // Avoid getting a null list of tasks
    if (categorizedTaskMap.get(project) == null) {
      categorizedTaskMap.put(project, new ArrayList<>());
    }

    return categorizedTaskMap.get(project);
  }

  public List<TaskCategory> getCategories(Project project) {
    if (taskCategoryMap.get(project) == null) {
      taskCategoryMap.put(project, new ArrayList<>());
    }

    return taskCategoryMap.get(project);
  }
}
