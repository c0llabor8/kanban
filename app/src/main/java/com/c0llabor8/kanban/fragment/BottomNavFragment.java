package com.c0llabor8.kanban.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.SubMenu;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import com.c0llabor8.kanban.R;
import com.c0llabor8.kanban.databinding.FragmentBottomNavBinding;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.navigation.NavigationView.OnNavigationItemSelectedListener;

public class BottomNavFragment extends BottomSheetDialogFragment {

  private FragmentBottomNavBinding binding;
  private BottomNavSheetListener listener;

  public static BottomNavFragment newInstance() {
    Bundle args = new Bundle();

    BottomNavFragment fragment = new BottomNavFragment();
    fragment.setArguments(args);
    return fragment;
  }

  @Nullable
  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {

    binding = DataBindingUtil.inflate(
        inflater,
        R.layout.fragment_bottom_nav,
        container,
        false
    );

    return binding.getRoot();
  }

  @Override
  public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    binding.navigationView.inflateMenu(R.menu.menu_main);

    // Once the view is created
    listener.populateProjects(
        binding.navigationView.getMenu().findItem(R.id.project_list).getSubMenu()
    );

    binding.navigationView.setNavigationItemSelectedListener(listener.onBottomNavItemSelected());
  }

  public void setListener(BottomNavSheetListener listener) {
    this.listener = listener;
  }

  public interface BottomNavSheetListener {

    // Callback to populate projects into the submenu
    void populateProjects(SubMenu subMenu);

    OnNavigationItemSelectedListener onBottomNavItemSelected();
  }
}
