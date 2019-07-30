package com.c0llabor8.kanban.model.query;

import com.c0llabor8.kanban.model.Project;
import com.c0llabor8.kanban.model.TaskCategory;
import com.parse.ParseQuery;

public class TaskCategoryQuery extends ParseQuery<TaskCategory> {

  public TaskCategoryQuery() {
    super(TaskCategory.class);
    include(TaskCategory.KEY_ORDER);
    include(TaskCategory.KEY_TITLE);
    include(TaskCategory.KEY_PROJECT);
    orderByAscending(TaskCategory.KEY_ORDER);
  }

  public TaskCategoryQuery whereProjectEquals(Project project) {
    whereEqualTo(TaskCategory.KEY_PROJECT, project);
    return this;
  }
}
