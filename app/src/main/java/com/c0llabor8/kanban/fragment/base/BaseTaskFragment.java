package com.c0llabor8.kanban.fragment.base;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.c0llabor8.kanban.fragment.dialog.NewTaskDialog.TaskRefreshListener;
import com.c0llabor8.kanban.model.Project;

public abstract class BaseTaskFragment extends Fragment implements TaskRefreshListener {

  protected Project project;

  @Override
  public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    project = getArguments().getParcelable("project");
  }

  @Override
  public void onResume() {
    super.onResume();
    onTaskRefresh();
  }
}
