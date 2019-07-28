package com.c0llabor8.kanban.model.query;

import static com.c0llabor8.kanban.model.Task.KEY_CATEGORY;
import static com.c0llabor8.kanban.model.Task.KEY_COMPLETED;
import static com.c0llabor8.kanban.model.Task.KEY_DESCRIPTION;
import static com.c0llabor8.kanban.model.Task.KEY_ESTIMATE;
import static com.c0llabor8.kanban.model.Task.KEY_PROJECT;
import static com.c0llabor8.kanban.model.Task.KEY_TITLE;

import com.c0llabor8.kanban.model.Assignment;
import com.c0llabor8.kanban.model.Project;
import com.c0llabor8.kanban.model.Task;
import com.parse.FindCallback;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import java.util.ArrayList;
import java.util.List;

public class TaskQuery extends ParseQuery<Task> {

  public TaskQuery() {
    super(Task.class);
    include(KEY_DESCRIPTION);
    include(KEY_TITLE);
    include(KEY_PROJECT);
    include(KEY_ESTIMATE);
    include(KEY_COMPLETED);
    include(KEY_CATEGORY);
    include("category.title");
    include("category.order");
  }

  public static void queryUserTasks(FindCallback<Task> callback) {
    Assignment.Query query = new Assignment.Query();

    query.whereUserEquals(ParseUser.getCurrentUser()).findInBackground((assignments, e) -> {
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

  public TaskQuery whereProjectEquals(Project project) {
    whereEqualTo(KEY_PROJECT, project);
    return this;
  }
}
