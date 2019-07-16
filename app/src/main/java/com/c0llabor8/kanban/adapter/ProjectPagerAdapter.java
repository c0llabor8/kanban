package com.c0llabor8.kanban.adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class ProjectPagerAdapter extends FragmentPagerAdapter {

  private Fragment[] frags;

  public ProjectPagerAdapter(@NonNull FragmentManager fm, Fragment[] frags) {
    super(fm, FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
    this.frags = frags;
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
    return frags[pos].getTag();
  }
}
