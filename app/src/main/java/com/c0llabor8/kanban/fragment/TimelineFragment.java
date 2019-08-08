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
import com.c0llabor8.kanban.R;
import com.c0llabor8.kanban.adapter.TimelineAdapter;
import com.c0llabor8.kanban.databinding.FragmentTimelineBinding;
import com.c0llabor8.kanban.fragment.base.BaseTaskFragment;
import com.c0llabor8.kanban.model.Project;
import com.c0llabor8.kanban.util.TaskProvider;

public class TimelineFragment extends BaseTaskFragment {

  private TimelineAdapter timelineAdapter;
  private FragmentTimelineBinding binding;

  public static TimelineFragment newInstance(Project project) {
    Bundle args = new Bundle();
    args.putString("title", "Timeline");
    args.putParcelable("project", project);

    TimelineFragment fragment = new TimelineFragment();
    fragment.setArguments(args);
    return fragment;
  }

  @Override
  public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    timelineAdapter = new TimelineAdapter(TaskProvider.getInstance().getTasks(project));
  }

  @Nullable
  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {

    binding = DataBindingUtil.inflate(inflater, R.layout.fragment_timeline, container, false);
    return binding.getRoot();
  }

  @Override
  public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    binding.rvTimeline.setLayoutManager(new LinearLayoutManager(getContext()));
    binding.rvTimeline.setAdapter(timelineAdapter);
    binding.executePendingBindings();
  }

  @Override
  public void setSwipeRefresh(SwipeRefreshLayout layout) {}

  @Override
  public void onTaskRefresh() {
    timelineAdapter.notifyDataSetChanged();
  }
}
