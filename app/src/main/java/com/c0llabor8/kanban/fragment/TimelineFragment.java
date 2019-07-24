package com.c0llabor8.kanban.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.c0llabor8.kanban.R;
import com.c0llabor8.kanban.adapter.TimelineAdapter;
import com.c0llabor8.kanban.databinding.FragmentTaskListBinding;
import com.c0llabor8.kanban.databinding.FragmentTimelineBinding;
import com.c0llabor8.kanban.fragment.base.BaseTaskFragment;
import com.c0llabor8.kanban.model.Task;
import java.util.List;

public class TimelineFragment extends Fragment {

  private TimelineAdapter timelineAdapter;
  private FragmentTimelineBinding binding;
  private List<Task> tasks;

  public static TimelineFragment newInstance(Bundle args) {
    args.putString("title", "Timeline");

    TimelineFragment fragment = new TimelineFragment();
    fragment.setArguments(args);
    return fragment;
  }

  @Nullable
  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {
    binding = DataBindingUtil.inflate(inflater, R.layout.fragment_timeline,
        container, false);
    return binding.getRoot();
  }

  @Override
  public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    timelineAdapter = new TimelineAdapter(tasks);
    binding.rvTimeline.setLayoutManager(new LinearLayoutManager(getContext()));
    binding.rvTimeline.setAdapter(timelineAdapter);
  }
}
