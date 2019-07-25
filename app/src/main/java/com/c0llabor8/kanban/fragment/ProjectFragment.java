package com.c0llabor8.kanban.fragment;

import android.content.Context;
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
import com.c0llabor8.kanban.fragment.dialog.NewTaskDialog.TaskRefreshListener;
import com.c0llabor8.kanban.model.Project;
import com.c0llabor8.kanban.model.Task;
import com.c0llabor8.kanban.util.TaskProvider;
import com.parse.ParseException;
import java.util.List;

public class ProjectFragment extends Fragment implements TaskRefreshListener {

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
    return binding.getRoot();
  }

  @Override
  public void onAttach(@NonNull Context context) {
    super.onAttach(context);

    Bundle projectBundle = new Bundle();
    projectBundle.putParcelable("project", project);
    pagerAdapter = new ProjectPagerAdapter(getChildFragmentManager(), project);
  }

  @Override
  public void onResume() {
    super.onResume();
    onTaskRefresh();
  }

  @Override
  public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);

    binding.pager.setAdapter(pagerAdapter);
    binding.tabs.setupWithViewPager(binding.pager, true);
  }

  @Override
  public void onTaskRefresh() {
    TaskProvider.getInstance().updateTasks(project, (objects, e) -> {
      for (int i = 0; i < pagerAdapter.getCount(); i++) {
        Fragment fragment = pagerAdapter.getItem(i);

        if (fragment instanceof TaskRefreshListener) {
          TaskRefreshListener taskFragment = (TaskRefreshListener) fragment;
          taskFragment.onTaskRefresh();
        }
      }
    });
  }
}
