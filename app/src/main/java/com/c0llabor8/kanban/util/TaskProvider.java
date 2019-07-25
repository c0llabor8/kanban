package com.c0llabor8.kanban.util;

import com.c0llabor8.kanban.model.Project;
import com.c0llabor8.kanban.model.Task;
import com.parse.FindCallback;
import com.parse.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class TaskProvider {

  private static TaskProvider instance;
  private HashMap<Project, List<Task>> taskMap;

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

        taskMap.put(project, objects);
        callback.done(objects, null);
      });

      return;
    }

    project.getAllTasks((List<Task> objects, ParseException e) -> {
      if (e != null) {
        callback.done(null, e);
        return;
      }

      taskMap.put(project, objects);
      callback.done(objects, null);
    });
  }

  public List<Task> getTasks(Project project) {
    List<Task> taskList = taskMap.get(project);

    if (taskList == null) {
      taskList = new ArrayList<>();
    }

    return taskList;
  }

  public void saveTask(Project project, Task task) {
    List<Task> taskList = taskMap.get(project);

    if (taskList == null) {
      taskList = new ArrayList<>();
    }

    taskList.add(task);
  }
}
