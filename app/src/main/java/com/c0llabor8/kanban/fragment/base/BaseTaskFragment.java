package com.c0llabor8.kanban.fragment.base;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.c0llabor8.kanban.model.Task;
import java.util.ArrayList;
import java.util.List;

public abstract class BaseTaskFragment extends Fragment {

  protected boolean isPersonal;
  protected List<Task> taskList;

  @Override
  public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    taskList = getArguments().getParcelableArrayList("tasks");

    if (taskList == null) {
      isPersonal = true;
      taskList = new ArrayList<>();
    }
  }
}
