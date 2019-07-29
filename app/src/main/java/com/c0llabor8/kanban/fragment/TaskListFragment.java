package com.c0llabor8.kanban.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.c0llabor8.kanban.R;
import com.c0llabor8.kanban.adapter.TaskListAdapter;
import com.c0llabor8.kanban.databinding.FragmentTaskListBinding;
import com.c0llabor8.kanban.fragment.base.BaseTaskFragment;
import com.c0llabor8.kanban.model.Project;
import com.c0llabor8.kanban.util.TaskProvider;

public class TaskListFragment extends BaseTaskFragment {

  private TaskListAdapter listAdapter;
  private FragmentTaskListBinding binding;

  public static TaskListFragment newInstance(Project project) {
    Bundle args = new Bundle();
    args.putString("title", "Tasks");
    args.putParcelable("project", project);

    TaskListFragment fragment = new TaskListFragment();
    fragment.setArguments(args);
    return fragment;
  }

  @Override
  public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
  }

  @Nullable
  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {

    if (project == null) {
      listAdapter = new TaskListAdapter(TaskProvider.getInstance().getTasks(project));
    } else {
      listAdapter = new TaskListAdapter(
          TaskProvider.getInstance().getCategorizedTasks(project),
          true
      );
    }

    binding = DataBindingUtil.inflate(inflater, R.layout.fragment_task_list, container, false);
    return binding.getRoot();
  }

  @Override
  public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);

    binding.rvTasks.setLayoutManager(new LinearLayoutManager(getContext()));
    binding.rvTasks.setAdapter(listAdapter);
  }

  @Override
  public void onTaskRefresh() {
    if (project == null) {
      TaskProvider.getInstance().updateTasks(project,
          (objects, e) -> listAdapter.notifyDataSetChanged());
    } else {
      listAdapter.notifyDataSetChanged();
    }
  }
}
