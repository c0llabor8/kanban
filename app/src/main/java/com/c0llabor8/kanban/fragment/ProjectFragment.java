package com.c0llabor8.kanban.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import com.c0llabor8.kanban.R;
import com.c0llabor8.kanban.adapter.ProjectPagerAdapter;
import com.c0llabor8.kanban.databinding.FragmentProjectBinding;
import com.c0llabor8.kanban.fragment.base.BaseTaskFragment;
import com.c0llabor8.kanban.model.Project;
import com.c0llabor8.kanban.model.Task;
import com.parse.FindCallback;
import com.parse.ParseException;
import java.util.ArrayList;
import java.util.List;

public class ProjectFragment extends Fragment {

  private Project project;
  private FragmentProjectBinding binding;
  private ProjectPagerAdapter pagerAdapter;
  private ArrayList<Task> taskList = new ArrayList<>();

  public static ProjectFragment newInstance(Project project) {

    Bundle args = new Bundle();

    args.putParcelable("project", project);

    ProjectFragment fragment = new ProjectFragment();
    fragment.setArguments(args);
    return fragment;
  }

  @Override
  public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    project = getArguments().getParcelable("project");
  }

  @Nullable
  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {

    binding = DataBindingUtil.inflate(inflater, R.layout.fragment_project, container, false);

    // Initialize the pagination with an array of fragments
    pagerAdapter = new ProjectPagerAdapter(getChildFragmentManager(), new Fragment[]{
        TaskListFragment.newInstance()
    });

    project.getAllTasks(new FindCallback<Task>() {
      @Override
      public void done(List<Task> objects, ParseException e) {
        taskList.addAll(objects);
        pagerAdapter.notifyDataSetChanged();
      }
    });

    return binding.getRoot();
  }

  @Override
  public void onAttachFragment(@NonNull Fragment childFragment) {
    if (childFragment instanceof BaseTaskFragment) {
      BaseTaskFragment fragment = (BaseTaskFragment) childFragment;
      fragment.setTasks(taskList);
    }
  }

  @Override
  public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    binding.pager.setAdapter(pagerAdapter);
    binding.tabs.setupWithViewPager(binding.pager, true);
  }
}
