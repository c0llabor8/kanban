package com.c0llabor8.kanban.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.c0llabor8.kanban.R;
import com.c0llabor8.kanban.databinding.FragmentProgressBinding;
import com.c0llabor8.kanban.fragment.base.BaseTaskFragment;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.DefaultValueFormatter;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import java.util.ArrayList;

public class ProgressFragment extends BaseTaskFragment {

  private FragmentProgressBinding binding;

  public ProgressFragment() {
    // Required empty public constructor
  }

  /* *
   * Creates an instance and Constructs a new Bundle to pass
   * into a new fragment and returns that same fragment
   */
  public static ProgressFragment newInstance() {
    return newInstance(new Bundle());
  }

  public static ProgressFragment newInstance(Bundle args) {
    args.putString("title", "Progress");

    ProgressFragment fragment = new ProgressFragment();
    fragment.setArguments(args);
    return fragment;
  }

  @Nullable
  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {

    // Inflate the layout for this fragment
    binding = DataBindingUtil.inflate(inflater, R.layout.fragment_progress,
        container, false);
    binding.rvMembers.setLayoutManager(new LinearLayoutManager(getContext(),
        LinearLayoutManager.HORIZONTAL, false));
    // reference the piechart with binding view
    return binding.getRoot();
  }

  // Calculates the slice size and update the pie chart
  // Returns data and passes that data inside model
  // NOTE: Use an ArrayList is to store x-axis labels.
  //       Pass the X-values Array and dataset into a new object PieData.
  private PieData drawChart() {
    // .invalidate() may make it update
    // Get the new value from user checking off tasks
    // Update the old value
    binding.pieChart.setUsePercentValues(false);

    ArrayList<PieEntry> pieData = new ArrayList<>();
    pieData.add(new PieEntry(40f, "Tasks: " + getTaskList().size(), 2));
    pieData.add(new PieEntry(60f, "", 1));

    PieDataSet dataSet = new PieDataSet(pieData, "");
    PieData data = new PieData(dataSet);

    data.setValueFormatter(new DefaultValueFormatter(getTaskList().size()));
    dataSet.setColors(ColorTemplate.JOYFUL_COLORS);
    binding.pieChart.setData(data); // Creates pieChart with data
    // Enables hole in pieChart
    binding.pieChart.setDrawHoleEnabled(true);
    binding.pieChart.setHoleRadius(58f);
    binding.pieChart.setTransparentCircleRadius(58f);
    data.setValueTextSize(15f);
    data.setValueTextColor(Color.BLACK);
    return data;
  }

  // get the total # of tasks from a given project
  @Override
  public void onTasksLoaded() {
    drawChart();
  }
}

