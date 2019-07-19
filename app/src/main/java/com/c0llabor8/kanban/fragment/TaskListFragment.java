package com.c0llabor8.kanban.fragment;

import android.os.Bundle;
import android.util.Log;
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

public class TaskListFragment extends BaseTaskFragment {

  private TaskListAdapter listAdapter;
  private FragmentTaskListBinding binding;

  public static TaskListFragment newInstance() {
    Bundle args = new Bundle();

    args.putString("title", "Tasks");

    TaskListFragment fragment = new TaskListFragment();
    fragment.setArguments(args);
    return fragment;
  }

  @Nullable
  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {

    listAdapter = new TaskListAdapter(taskList);
    binding = DataBindingUtil.inflate(inflater, R.layout.fragment_task_list, container, false);
    return binding.getRoot();
  }

  @Override
  public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    binding.rvTasks.setLayoutManager(new LinearLayoutManager(getContext()));
    binding.rvTasks.setAdapter(listAdapter);
  }

  @Override
  public void onTasksUpdated() {
    Log.i("TAG", "notified " + taskList.size());
    listAdapter.notifyDataSetChanged();
  }
}
