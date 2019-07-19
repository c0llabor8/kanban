package com.c0llabor8.kanban.adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import com.c0llabor8.kanban.fragment.TaskListFragment;
import com.c0llabor8.kanban.model.Project;

public class ProjectPagerAdapter extends FragmentPagerAdapter {

  private Fragment[] frags;

  public ProjectPagerAdapter(@NonNull FragmentManager fm, Project project) {
    super(fm, FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);

    if (project == null) {
      frags = new Fragment[]{
          TaskListFragment.newInstance()
      };
    }
  }

  @NonNull
  @Override
  public Fragment getItem(int pos) {
    return frags[pos];
  }

  @Override
  public int getCount() {
    return frags.length;
  }

  @Nullable
  @Override
  public CharSequence getPageTitle(int pos) {
    return frags[pos].getArguments().getString("title", "");
  }
}
