package com.c0llabor8.kanban.util;

import android.view.SubMenu;
import com.google.android.material.navigation.NavigationView.OnNavigationItemSelectedListener;

public interface ProjectActivityInterface {

  // Callback to populate projects into the submenu
  void populateProjects(SubMenu subMenu);

  // OnNavigationItemSelectedListener provided by the listening class
  OnNavigationItemSelectedListener onBottomNavItemSelected();

  // Tell the activity to load all projects the user is subscribed to
  void loadProjects();
}
