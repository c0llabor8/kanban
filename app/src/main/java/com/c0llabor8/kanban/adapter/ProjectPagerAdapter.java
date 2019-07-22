package com.c0llabor8.kanban.adapter;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import com.c0llabor8.kanban.fragment.TaskListFragment;

public class ProjectPagerAdapter extends FragmentPagerAdapter {

  private Fragment[] fragments;

  public ProjectPagerAdapter(@NonNull FragmentManager fm, Bundle taskBundle) {
    super(fm, FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);

    TaskListFragment taskListFragment =
        TaskListFragment.newInstance(taskBundle);

    fragments = new Fragment[]{
        taskListFragment
    };
  }

  @NonNull
  @Override
  public Fragment getItem(int pos) {
    return fragments[pos];
  }

  @Override
  public int getCount() {
    return fragments.length;
  }

  @Nullable
  @Override
  public CharSequence getPageTitle(int pos) {
    return fragments[pos].getArguments().getString("title", "");
  }
}
