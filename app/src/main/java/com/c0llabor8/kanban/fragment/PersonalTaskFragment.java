package com.c0llabor8.kanban.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.c0llabor8.kanban.adapter.ProjectPagerAdapter;
import com.c0llabor8.kanban.databinding.FragmentProjectBinding;
import com.c0llabor8.kanban.fragment.base.BaseTaskFragment;
import com.c0llabor8.kanban.model.Task;
import java.util.ArrayList;

public class PersonalTaskFragment extends Fragment {

  private FragmentProjectBinding binding;
  private ProjectPagerAdapter pagerAdapter;
  private ArrayList<Task> taskList = new ArrayList<>();

  public static PersonalTaskFragment newInstance() {

    Bundle args = new Bundle();

    PersonalTaskFragment fragment = new PersonalTaskFragment();
    fragment.setArguments(args);
    return fragment;
  }

  @Nullable
  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {

    binding = FragmentProjectBinding.inflate(inflater, container, false);

    // Initialize the pagination with an array of fragments
    pagerAdapter = new ProjectPagerAdapter(getChildFragmentManager(), new Fragment[]{
        TaskListFragment.newInstance()
    });

    Task.queryUserTasks((objects, e) -> {
      taskList.addAll(objects);
      ((BaseTaskFragment) pagerAdapter.getItem(0)).onTasksUpdated();
    });

    binding.pager.setAdapter(pagerAdapter);
    binding.tabs.setupWithViewPager(binding.pager, true);

    return binding.getRoot();
  }

  @Override
  public void onAttachFragment(@NonNull Fragment childFragment) {
    if (childFragment instanceof BaseTaskFragment) {
      BaseTaskFragment fragment = (BaseTaskFragment) childFragment;
      fragment.setTasks(taskList);
    }
  }
}
