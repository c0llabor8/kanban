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
import com.c0llabor8.kanban.fragment.dialog.NewTaskDialog.TaskCreatedListener;
import com.c0llabor8.kanban.model.Project;
import com.c0llabor8.kanban.model.Task;
import com.parse.FindCallback;
import com.parse.ParseException;
import java.util.ArrayList;
import java.util.List;

public class ProjectFragment extends Fragment implements TaskCreatedListener {

  private Project project;
  private FragmentProjectBinding binding;
  private ProjectPagerAdapter pagerAdapter;

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

    project.getAllTasks((tasks, e) -> {
      Bundle taskBundle = new Bundle();
      taskBundle.putParcelableArrayList("tasks", new ArrayList<>(tasks));

      pagerAdapter = new ProjectPagerAdapter(getChildFragmentManager(), taskBundle);
      binding.pager.setAdapter(pagerAdapter);
      binding.tabs.setupWithViewPager(binding.pager, true);
    });

    return binding.getRoot();
  }

  @Override
  public void onTaskCreated(Project project) {

  }
}
