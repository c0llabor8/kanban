package com.c0llabor8.kanban.fragment.base;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.c0llabor8.kanban.model.Project;
import com.c0llabor8.kanban.model.Task;
import com.c0llabor8.kanban.util.TaskProvider;
import com.parse.ParseException;
import java.util.ArrayList;
import java.util.List;

public abstract class BaseTaskFragment extends Fragment {

  protected Project project;
  private List<Task> taskList = new ArrayList<>();

  @Override
  public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    project = getArguments().getParcelable("project");
    reloadTasks();
  }

  protected List<Task> getTaskList() {
    return taskList;
  }

  public void reloadTasks() {
    if (project == null) {
      TaskProvider.getInstance().updateTasks(null,
          (List<Task> objects, ParseException e) -> {
            taskList.clear();
            taskList.addAll(objects);
            onTasksLoaded();
          });
    } else {
      project.getAllTasks((objects, e) -> {
        taskList.clear();
        taskList.addAll(objects);
        onTasksLoaded();
      });
    }
  }

  public abstract void onTasksLoaded();
}
