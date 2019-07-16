package com.c0llabor8.kanban.model;

import com.parse.ParseClassName;
import com.parse.ParseObject;
import com.parse.ParseQuery;

@ParseClassName("Task")
public class Task extends ParseObject {

  public static class Query extends ParseQuery<Task> {

    public Query() {
      super(Task.class);
    }
  }

}
