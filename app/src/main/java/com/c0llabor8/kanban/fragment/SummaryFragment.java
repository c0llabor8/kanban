package com.c0llabor8.kanban.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.c0llabor8.kanban.R;
import com.c0llabor8.kanban.adapter.MemberProfileAdapter;
import com.c0llabor8.kanban.databinding.FragmentSummaryBinding;
import com.c0llabor8.kanban.fragment.base.BaseTaskFragment;
import com.c0llabor8.kanban.model.Project;
import java.util.ArrayList;

public class SummaryFragment extends BaseTaskFragment {

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

  @Override
  public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    //memberProfileAdapter = new MemberProfileAdapter();
  }
  @Nullable
  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {

    // Data to populate the RecyclerView with
    ArrayList<Integer> members = new ArrayList<>();
    //members.add(Membership.
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
    //memberProfileAdapter.setClickListener(this);
  }

  @Override
  public void onTaskRefresh() {
  }
}

