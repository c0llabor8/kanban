package com.c0llabor8.kanban.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import com.c0llabor8.kanban.R;
import com.c0llabor8.kanban.adapter.TaskListAdapter;
import com.c0llabor8.kanban.databinding.FragmentTaskListBinding;
import com.c0llabor8.kanban.fragment.base.BaseTaskFragment;
import com.c0llabor8.kanban.model.Project;
import com.c0llabor8.kanban.util.TaskProvider;

public class TaskListFragment extends BaseTaskFragment {

  SwipeRefreshLayout swipeRefreshLayout;
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

  @Nullable
  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {

    binding = DataBindingUtil.inflate(inflater, R.layout.fragment_task_list, container, false);

    if (project == null) {
      listAdapter = new TaskListAdapter(TaskProvider.getInstance().getTasks(project));
    } else {
      listAdapter = new TaskListAdapter(
          TaskProvider.getInstance().getCategorizedTasks(project),
          true
      );
    }

    return binding.getRoot();
  }

  @Override
  public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);

    binding.rvTasks.setLayoutManager(new LinearLayoutManager(getContext()));
    binding.rvTasks.setAdapter(listAdapter);
  }

  @Override
  public void setSwipeRefresh(SwipeRefreshLayout layout) {
    if (project == null) {
      swipeRefreshLayout = layout;
    }
  }

  private void updateState() {
    if (listAdapter.getItemCount() == 0) {

      if (project == null) {
        binding.artwork.setImageResource(R.drawable.empty_state_list);
      } else {
        binding.artwork.setImageResource(R.drawable.empty_state_project_list);
      }

      binding.rvTasks.setVisibility(View.GONE);
      binding.artwork.setVisibility(View.VISIBLE);
    } else {
      binding.artwork.setImageDrawable(null);
      binding.rvTasks.setVisibility(View.VISIBLE);
      binding.artwork.setVisibility(View.GONE);
    }
  }

  @Override
  public void onTaskRefresh() {
    if (project == null) {
      TaskProvider.getInstance().updateTasks(project,
          (objects, e) -> {
            listAdapter.notifyDataSetChanged();
            swipeRefreshLayout.setRefreshing(false);
            updateState();
          });
    } else {
      listAdapter.notifyDataSetChanged();
      updateState();
    }
  }
}
