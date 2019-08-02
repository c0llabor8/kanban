package com.c0llabor8.kanban.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.anychart.APIlib;
import com.anychart.AnyChart;
import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.chart.common.dataentry.ValueDataEntry;
import com.anychart.charts.Cartesian;
import com.anychart.core.cartesian.series.Column;
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
    //binding.rvTasksCompleted.setLayoutManager(new LinearLayoutManager(getContext()));
    //binding.rvTasksCompleted.setAdapter(taskListAdapter);

    binding.tvCompleted.setText(String.format(
        Locale.getDefault(), "%d",
        TaskProvider.getInstance().getCompletedTasks(project).size()
    ));

    binding.tvIncomplete.setText(String.format(
        Locale.getDefault(), "%d",
        TaskProvider.getInstance().getTasks(project).size()
    ));

    binding.barChart.setProgressBar(binding.progressBar);

    // Creates a chart
    Cartesian cartesian = AnyChart.column();

    // Create data
    List<DataEntry> data = new ArrayList<>();
    for (TaskCategory category : TaskProvider.getInstance().getCategories(project)) {
      data.add(new ValueDataEntry(category.getTitle(), TaskProvider.getTaskCategoryCount(project, category
          )));
    }

    // Creates a column series and sets the data
    Column column = cartesian.column(data);
    //cartesian.barGroupsPadding(10);
    column.tooltip()
        .titleFormat("{%X}")
        .position(Position.CENTER_BOTTOM)
        .anchor(Anchor.CENTER_BOTTOM)
        .offsetX(0d)
        .offsetY(5d)
        .format("{%Value} {%groupsSeparator:}");

    cartesian.animation(true);
    cartesian.title("Tasks Category Overview");

    cartesian.yScale().minimum(0d);
    cartesian.yAxis(0).labels().format("{%Value}{groupsSeparator: }");
    cartesian.yAxis(0).title("Number of Tasks");
    cartesian.xAxis(0).title("Task Categories");

    cartesian.tooltip().positionMode(TooltipPositionMode.POINT);
    cartesian.interactivity().hoverMode(HoverMode.BY_X);

    binding.barChart.setChart(cartesian);
    APIlib.getInstance().setActiveAnyChartView(binding.barChart);
  }

  @Override
  public void onTaskRefresh() {
    taskListAdapter.notifyDataSetChanged();
    memberProfileAdapter.notifyDataSetChanged();
  }
}
