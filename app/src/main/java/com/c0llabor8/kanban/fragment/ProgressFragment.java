package com.c0llabor8.kanban.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
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

public class ProgressFragment extends BaseTaskFragment {

  private FragmentProjectBinding binding;
  private Project project;
  private ProjectPagerAdapter pagerAdapter;


  //TODO: Create an instance
  public static ProgressFragment newInstance() {

    return newInstance();
  }

  @Nullable
  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {

    binding = DataBindingUtil.inflate(inflater, R.layout.fragment_progress, container, false);
    return binding.getRoot();
    // Do I need an adapter? yes
    // get the total # of tasks from a given project

    // count the # of tasks I got done vs the ones that are left in a given project
  }

  public void setTasksDone() {
    // Get the new value from user checking off tasks

    // Update the old value

  }

  private void updateProgress() {
    // Calculate the slice size and update the pie chart
    //binding.
    //ProgressBar pieChart = findViewById(R.id.stats_progressbar); //Change into binding view
    //int progress
    //pieChart.setProgress(progress);
  }
}
