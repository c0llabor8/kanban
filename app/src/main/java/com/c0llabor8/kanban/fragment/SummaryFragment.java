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
import com.anychart.APIlib;
import com.anychart.AnyChart;
import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.chart.common.dataentry.ValueDataEntry;
import com.anychart.charts.Cartesian;
import com.anychart.charts.Pie;
import com.anychart.core.cartesian.series.Column;
import com.anychart.data.Mapping;
import com.anychart.data.Set;
import com.anychart.enums.Anchor;
import com.anychart.enums.HoverMode;
import com.anychart.enums.Position;
import com.anychart.enums.TooltipPositionMode;
import com.c0llabor8.kanban.R;
import com.c0llabor8.kanban.adapter.MemberProfileAdapter;
import com.c0llabor8.kanban.adapter.TaskListAdapter;
import com.c0llabor8.kanban.databinding.FragmentSummaryBinding;
import com.c0llabor8.kanban.fragment.base.BaseTaskFragment;
import com.c0llabor8.kanban.model.Project;
import com.c0llabor8.kanban.model.TaskCategory;
import com.c0llabor8.kanban.util.MemberProvider;
import com.c0llabor8.kanban.util.TaskProvider;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class SummaryFragment extends BaseTaskFragment {

  Cartesian cartesian;
  private List<DataEntry> data = new ArrayList<>();

  private TaskListAdapter taskListAdapter;
  private MemberProfileAdapter memberProfileAdapter;
  private FragmentSummaryBinding binding;

  public static SummaryFragment newInstance(Project project) {
    Bundle args = new Bundle();
    args.putString("title", "Summary");
    args.putParcelable("project", project);

    SummaryFragment fragment = new SummaryFragment();
    fragment.setArguments(args);
    return fragment;
  }

  @Nullable
  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {

    memberProfileAdapter =
        new MemberProfileAdapter(getActivity(),
            MemberProvider.getInstance().getMemberList(project));
    taskListAdapter =
        new TaskListAdapter(TaskProvider.getInstance().getCompletedTasks(project));

    // Inflate the layout for this fragment
    binding = DataBindingUtil.inflate(inflater, R.layout.fragment_summary,
        container, false);

    cartesian = AnyChart.column();
    cartesian.animation(false);

    return binding.getRoot();
  }

  @Override
  public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    // Sets the LinearLayoutManager horizontally
    binding.rvMembers.setLayoutManager(new LinearLayoutManager(getContext(),
        LinearLayoutManager.HORIZONTAL, false));
    // Set up the RecyclerView
    binding.rvMembers.setAdapter(memberProfileAdapter);

    binding.pieChart.setChart(cartesian);

    updateTaskCounts();
    updateChart();
  }

  private void updateChart() {
    binding.pieChart.clear();

    for (TaskCategory category : TaskProvider.getInstance().getCategories(project)) {
      data.add(new ValueDataEntry(category.getTitle(),
          TaskProvider.getTaskCategoryCount(project, category)));
    }

    cartesian.data(data);
    binding.pieChart.setChart(cartesian);
  }

  @Override
  public void setSwipeRefresh(SwipeRefreshLayout layout) {}

  @Override
  public void onTaskRefresh() {
    updateChart();
    updateTaskCounts();
    taskListAdapter.notifyDataSetChanged();
    memberProfileAdapter.notifyDataSetChanged();
  }

  private void updateTaskCounts() {
    binding.tvCompleted.setText(String.format(
        Locale.getDefault(), "%d",
        TaskProvider.getInstance().getCompletedTasks(project).size()
    ));

    binding.tvIncomplete.setText(String.format(
        Locale.getDefault(), "%d",
        TaskProvider.getInstance().getTasks(project).size()
    ));
  }
}
