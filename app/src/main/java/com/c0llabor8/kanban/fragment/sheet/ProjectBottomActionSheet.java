package com.c0llabor8.kanban.fragment.sheet;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import com.c0llabor8.kanban.R;
import com.c0llabor8.kanban.databinding.SheetBottomNavBinding;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class ProjectBottomActionSheet extends BottomSheetDialogFragment {
  private SheetBottomNavBinding binding;
  private ProjectSheetListener listener;

  public static ProjectBottomActionSheet newInstance() {
    Bundle args = new Bundle();

    ProjectBottomActionSheet fragment = new ProjectBottomActionSheet();
    fragment.setArguments(args);
    return fragment;
  }

  @Nullable
  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {

    binding = DataBindingUtil.inflate(
        inflater,
        R.layout.sheet_bottom_nav,
        container,
        false
    );

    return binding.getRoot();
  }

  @Override
  public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    binding.navigationView.inflateMenu(R.menu.menu_project_action);

    // Listen for navigation item clicks using the listener from the listening class
    binding.navigationView.setNavigationItemSelectedListener(
        item -> listener.onProjectItemSelected(item));
  }

  // Set the class that will be listening to this menu
  public void setProjectNavigationListener(ProjectSheetListener listener) {
    this.listener = listener;
  }
}
