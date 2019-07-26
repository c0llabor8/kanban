package com.c0llabor8.kanban.util;

import com.c0llabor8.kanban.model.Project;
import com.c0llabor8.kanban.model.Task;
import com.c0llabor8.kanban.model.TaskCategory;
import com.parse.FindCallback;
import com.parse.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class TaskProvider {

  private static TaskProvider instance;
  private HashMap<Project, List<Task>> taskMap;
  private HashMap<Project, HashMap<TaskCategory, Integer>> categoryMap;

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
   * @param project project to get tasks for
   * @param callback called once all tasks found and cached
   */
  public void updateTasks(Project project, FindCallback<Task> callback) {
    if (project == null) {
      Task.queryUserTasks((List<Task> objects, ParseException e) -> {
        if (e != null) {
          callback.done(null, e);
          return;
        }

        getTasks(project).clear();
        getTasks(project).addAll(objects);
        callback.done(objects, null);
      });

      return;
    }

    project.getAllTasks((List<Task> tasks, ParseException e) -> {
      if (e != null) {
        callback.done(null, e);
        return;
      }

      getTasks(project).clear();
      getCategoryCounts(project).clear();

      for (int i = 0; i < tasks.size(); i++) {
        Task task = tasks.get(i);
        getTasks(project).add(task);
        TaskCategory category = task.getCategory();

        Integer count = getCategoryCounts(project).get(category);

        if (count == null) {
          getCategoryCounts(project).put(category, 1);
          continue;
        }

        getCategoryCounts(project).put(category, count + 1);
      }

      callback.done(tasks, null);
    });
  }

  /**
   * Gets all cached tasks for a given project
   * @param project Project to get all tasks for
   * @return A list of all tasks given a project
   */
  public List<Task> getTasks(Project project) {

    if (taskMap.get(project) == null) {
      taskMap.put(project, new ArrayList<>());
    }

    return taskMap.get(project);
  }

  /**
   * Gets all cached categories and counts for a given project
   * @param project Project to get all categories for
   * @return A HashMap of Category to Category task count
   */
  public HashMap<TaskCategory, Integer> getCategoryCounts(Project project) {
    if (categoryMap.get(project) == null) {
      categoryMap.put(project, new HashMap<>());
    }

    return categoryMap.get(project);
  }
}
