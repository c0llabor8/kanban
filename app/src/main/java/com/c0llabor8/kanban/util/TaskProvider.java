package com.c0llabor8.kanban.util;

import com.c0llabor8.kanban.model.Project;
import com.c0llabor8.kanban.model.Task;
import com.parse.FindCallback;
import com.parse.ParseException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class TaskProvider {

  private static TaskProvider instance;
  private HashMap<Project, Set<Task>> taskMap;

  private TaskProvider() {
    taskMap = new HashMap<>();
  }

  public static TaskProvider getInstance() {
    if (instance == null) {
      instance = new TaskProvider();
    }

    return instance;
  }

  public void updateTasks(Project project, FindCallback<Task> callback) {
    if (project == null) {
      Task.queryUserTasks((List<Task> objects, ParseException e) -> {
        if (e != null) {
          callback.done(null, e);
          return;
        }

        saveTasks(null, objects);
        callback.done(objects, null);
      });

      return;
    }

    project.getAllTasks((List<Task> objects, ParseException e) -> {
      if (e != null) {
        callback.done(null, e);
        return;
      }

      saveTasks(null, objects);
      callback.done(objects, null);
    });
  }

  public Task[] getTasks(Project project) {
    Set<Task> taskSet = taskMap.get(project);

    if (taskSet == null) {
      taskSet = new HashSet<>();
    }

    return taskSet.toArray(new Task[]{});
  }

  public void saveTasks(Project project, List<Task> tasks) {
    taskMap.put(project, new HashSet<>(tasks));
  }
}
