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
import java.util.Map;

/**
 * This class is a singleton instance that stores all Tasks for each project and their categories
 */
public class TaskProvider {

  private static TaskProvider instance;
  private HashMap<String, List<Task>> taskMap;
  private HashMap<String, List<Task>> completedTaskMap;
  private HashMap<String, List<Task>> categorizedTaskMap;

  private HashMap<String, List<TaskCategory>> taskCategoryListMap;
  private HashMap<String, HashMap<String, TaskCategory>> taskCategoryMap;

  private TaskProvider() {
    taskMap = new HashMap<>();
    completedTaskMap = new HashMap<>();
    categorizedTaskMap = new HashMap<>();
    taskCategoryListMap = new HashMap<>();
    taskCategoryMap = new HashMap<>();
  }

  public static TaskProvider getInstance() {
    if (instance == null) {
      instance = new TaskProvider();
    }

    return instance;
  }

  public static TaskCategory getCategory(Project project, String category) {
    return project == null ? null : getInstance().getCategoryMap(project).get(category);
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
        getCompletedTasks(project).clear();

        insertTasks(null, tasks);

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
      getCompletedTasks(project).clear();
      getCategorizedTasks(project).clear();

      insertTasks(project, tasks);

      getCategorizedTasks(project).addAll(getTasks(project));

      // Sort the tasks with category priority and deadline priority
      Collections.sort(getTasks(project), new Task.SortByDeadline());
      Collections.sort(getCompletedTasks(project), new Task.SortByDeadline());
      Collections.sort(getCategorizedTasks(project), new Task.SortByCategory());
      callback.done(getTasks(project), null);
    });
  }

  private void insertTasks(Project project, List<Task> tasks) {
    for (Task task : tasks) {
      if (task.getCompleted()) {
        getCompletedTasks(project).add(task);
      } else {
        getTasks(project).add(task);
      }
    }
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

      for (TaskCategory category : objects) {
        getCategories(project).add(category);
        getCategoryMap(project).put(category.toString(), category);
      }

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
    String hash = (project == null) ? null : project.getObjectId();
    // Avoid getting a null list of tasks
    if (taskMap.get(hash) == null) {
      taskMap.put(hash, new ArrayList<>());
    }

    return taskMap.get(hash);
  }

  /**
   * Gets all cached tasks for a given project in categorized order
   *
   * @param project Project to get all tasks for
   * @return A list of all tasks given a project
   */
  public List<Task> getCategorizedTasks(Project project) {
    String hash = (project == null) ? null : project.getObjectId();
    // Avoid getting a null list of tasks
    if (categorizedTaskMap.get(hash) == null) {
      categorizedTaskMap.put(hash, new ArrayList<>());
    }

    return categorizedTaskMap.get(hash);
  }

  public List<Task> getCompletedTasks(Project project) {
    String hash = (project == null) ? null : project.getObjectId();

    if (completedTaskMap.get(hash) == null) {
      completedTaskMap.put(hash, new ArrayList<>());
    }

    return completedTaskMap.get(hash);
  }

  public List<TaskCategory> getCategories(Project project) {
    String hash = (project == null) ? null : project.getObjectId();

    if (taskCategoryListMap.get(hash) == null) {
      taskCategoryListMap.put(hash, new ArrayList<>());
    }

    return taskCategoryListMap.get(hash);
  }

  public Map<String, TaskCategory> getCategoryMap(Project project) {
    String hash = (project == null) ? null : project.getObjectId();

    if (taskCategoryMap.get(hash) == null) {
      taskCategoryMap.put(hash, new HashMap<>());
    }

    return taskCategoryMap.get(hash);
  }
}
