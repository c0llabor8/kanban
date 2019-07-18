package com.c0llabor8.kanban.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.c0llabor8.kanban.R;
import com.c0llabor8.kanban.adapter.TaskAdapter;
import com.c0llabor8.kanban.databinding.FragmentTaskListBinding;
import com.c0llabor8.kanban.model.Assignment;
import com.c0llabor8.kanban.model.Task;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseUser;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TaskListFragment extends Fragment {

  protected TaskAdapter adapter;
  protected ArrayList<Task> mTasks = new ArrayList<>();
  FragmentTaskListBinding binding;

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
    binding = DataBindingUtil.inflate(inflater, R.layout.fragment_task_list, container, false);
    return binding.getRoot();
  }

  @Override
  public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    // Create the data source

    queryAssignments((objects, e) -> {
      for (Assignment assignment : objects) {
        mTasks.add(assignment.getTask());
        setRecyclerView();
      }
    });
  }

  private void setRecyclerView() {
    // Create the adapter
    adapter = new TaskAdapter(getContext(), mTasks);
    // Set the adapter on the recycler view
    binding.rvTasks.setAdapter(adapter);
    // Set the layout manager on the recycler view
    binding.rvTasks.setLayoutManager(new LinearLayoutManager(getContext()));
  }

  private void queryAssignments(FindCallback<Assignment> callback) {
    Assignment.Query assignmentQuery = new Assignment.Query();
    assignmentQuery.whereUserEquals(ParseUser.getCurrentUser());

    assignmentQuery.findInBackground(callback);
  }

  private void queryTask() {
    Task.Query tasksQuery = new Task.Query();
    tasksQuery.sortAscending();
  }

}
