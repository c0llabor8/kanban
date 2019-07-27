package com.c0llabor8.kanban.util;

import com.c0llabor8.kanban.model.Project;
import com.c0llabor8.kanban.model.Task;
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
  private HashMap<Project, HashMap<String, Integer>> categoryMap;

  private TaskProvider() {
    taskMap = new HashMap<>();
    categoryMap = new HashMap<>();
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

        getTasks(null).clear();
        getTasks(null).addAll(tasks);
        callback.done(tasks, null);
      });

      return;
    }

    // Else we get all tasks for the specified project
    project.getAllTasks((List<Task> tasks, ParseException e) -> {
      if (e != null) {
        callback.done(null, e);
        return;
      }

      getTasks(project).clear();
      getCategoryCounts(project).clear();
      Collections.sort(getTasks(project), Task.taskComparator());

      // For each task add them to the project's list and keep a count of each project task category
      for (int i = 0; i < tasks.size(); i++) {
        Task task = tasks.get(i);
        getTasks(project).add(task);
        String categoryId = (task.getCategory() == null) ? null : task.getCategory().getObjectId();

        Integer count = getCategoryCounts(project).get(categoryId);

        if (count == null) {
          getCategoryCounts(project).put(categoryId, 1);
          continue;
        }

        getCategoryCounts(project).put(categoryId, count + 1);
      }

      callback.done(tasks, null);
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
   * Gets all cached categories and counts for a given project
   *
   * @param project Project to get all categories for
   * @return A HashMap of Category to Category task count
   */
  public HashMap<String, Integer> getCategoryCounts(Project project) {
    // Avoid getting a null map of category counts
    if (categoryMap.get(project) == null) {
      categoryMap.put(project, new HashMap<>());
    }

    return categoryMap.get(project);
  }
}
