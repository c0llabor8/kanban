package com.c0llabor8.kanban.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.SubMenu;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import com.c0llabor8.kanban.R;
import com.c0llabor8.kanban.databinding.BottomSheetNavFragmentBinding;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import java.util.TreeMap;

public class BottomSheetNavFragment extends BottomSheetDialogFragment {

  private SubMenu projectSubMenu;
  private BottomSheetNavFragmentBinding binding;

  public static BottomSheetNavFragment newInstance() {
    Bundle args = new Bundle();

    BottomSheetNavFragment fragment = new BottomSheetNavFragment();
    fragment.setArguments(args);
    return fragment;
  }

  @Nullable
  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {

    binding = DataBindingUtil.inflate(
        inflater,
        R.layout.bottom_sheet_nav_fragment,
        container,
        false
    );

    return binding.getRoot();
  }

  @Override
  public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    binding.navigationView.inflateMenu(R.menu.menu_main);
    projectSubMenu = binding.navigationView.getMenu().findItem(R.id.project_list).getSubMenu();
  }

  // TODO: After being fed the list of projects place them into submenu in alphabetical order
  private void populateProjects() {
    String[] projects = getArguments().getStringArray("projects");

    for (int i = 0; i < projects.length; i++) {
      projectSubMenu.add(R.id.project_group, Menu.NONE + i, i, projects[i]);
    }
  }
}
