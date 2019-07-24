package com.c0llabor8.kanban.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.c0llabor8.kanban.R;
import com.c0llabor8.kanban.adapter.ProjectPagerAdapter;
import com.c0llabor8.kanban.databinding.FragmentProjectBinding;
import com.c0llabor8.kanban.fragment.base.BaseTaskFragment;
import com.c0llabor8.kanban.model.Project;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import java.util.ArrayList;

public class ProgressFragment extends BaseTaskFragment {

  private FragmentProjectBinding binding;
  private PieChart mChart;

  public ProgressFragment() {
    // Required empty public constructor
  }
  /* *
   * Creates an instance
   */
  public static ProgressFragment newInstance() {
    return newInstance(new Bundle());
  }

  private static ProgressFragment newInstance(Bundle args) {
    args.putString("title", "Tasks");

    ProgressFragment fragment = new ProgressFragment();
    fragment.setArguments(args);
    return fragment;
  }

  @Nullable
  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {

    // Inflate the layout for this fragment
    binding = DataBindingUtil.inflate(inflater, R.layout.fragment_progress, container, false);
    // reference the piechart with binding view:  PieChart pieChart = (PieChart) view.findViewById
    // (R.id.chart);
    drawChart();
    return binding.getRoot();
  }

  @Override
  public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    //pagerAdapter = new ProjectPagerAdapter();
    //binding..setLayoutManager(new LinearLayoutManager(getContext()));
    //binding..setAdapter(pagerAdapter);
    //PieChart pieChart = findViewById(R.id.pieChart);
  }

  // Calculate the slice size and update the pie chart
  // Returns data and passes that data inside model
  private PieData drawChart() {
    // .invalidate() may make it update
    // Get the new value from user checking off tasks
    // Update the old value
    mChart.setUsePercentValues(true);

    ArrayList<PieEntry> pieData = new ArrayList<>();
    pieData.add(new PieEntry(40f, "Tasks completed", taskList.size()));
    pieData.add(new PieEntry(60f, "", 1));


    PieDataSet dataSet = new PieDataSet(pieData,"");
    PieData data = new PieData(dataSet);

    data.setValueFormatter(new PercentFormatter());
    mChart.setData(data);
    mChart.setDrawHoleEnabled(true);
    mChart.setTransparentCircleRadius(58f);
    mChart.setHoleRadius(58f);
    dataSet.setColors(ColorTemplate.VORDIPLOM_COLORS);
    data.setValueTextSize(13f);
    data.setValueTextColor(Color.DKGRAY);
    return data;
  }
}

