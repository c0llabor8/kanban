package com.c0llabor8.kanban.model.query;

import static com.c0llabor8.kanban.model.Assignment.KEY_PROJECT;
import static com.c0llabor8.kanban.model.Assignment.KEY_TASK;
import static com.c0llabor8.kanban.model.Assignment.KEY_USER;

import com.c0llabor8.kanban.model.Assignment;
import com.c0llabor8.kanban.model.Project;
import com.c0llabor8.kanban.model.Task;
import com.parse.ParseQuery;
import com.parse.ParseUser;

public class AssignmentQuery extends ParseQuery<Assignment> {

  public AssignmentQuery() {
    super(Assignment.class);
    include(KEY_USER);
    include(KEY_TASK);
    include(KEY_PROJECT);
  }

  public AssignmentQuery whereUserEquals(ParseUser user) {
    whereEqualTo(KEY_USER, user);
    return this;
  }

  public AssignmentQuery whereTaskEquals(Task task) {
    whereEqualTo(KEY_TASK, task);
    return this;
  }

  public AssignmentQuery whereProjectEquals(Project project) {
    whereEqualTo(KEY_PROJECT, project);
    return this;
  }
}