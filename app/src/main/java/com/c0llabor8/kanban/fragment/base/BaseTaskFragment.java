package com.c0llabor8.kanban.fragment.base;

import androidx.fragment.app.Fragment;
import com.c0llabor8.kanban.model.Task;
import java.util.List;

public abstract class BaseTaskFragment extends Fragment {
  protected List<Task> taskList;

  public void setTasks(List<Task> tasks) {
    taskList = tasks;
  }

  public abstract void onTasksUpdated();
}
