package com.c0llabor8.kanban.fragment.sheet;

import android.view.MenuItem;
import android.view.SubMenu;

public interface ProjectSheetListener {

  // Callback to populate projects into the submenu
  void onPrepareProjectMenu(SubMenu subMenu);

  boolean onProjectItemSelected(MenuItem item);
}