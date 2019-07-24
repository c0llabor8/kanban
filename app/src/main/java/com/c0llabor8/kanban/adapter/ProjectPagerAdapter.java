package com.c0llabor8.kanban.adapter;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import com.c0llabor8.kanban.fragment.ProgressFragment;
import com.c0llabor8.kanban.fragment.TaskListFragment;
import com.c0llabor8.kanban.fragment.TimelineFragment;

/*
 * Version of the pager to page through fragments as a set of tabs
 */
public class ProjectPagerAdapter extends FragmentPagerAdapter {

  private Bundle taskBundle;

  /*
   * Passes the tasks as a Bundle into a new instance
   * Makes an array of fragments to place the taskListFragment
   */
  public ProjectPagerAdapter(@NonNull FragmentManager fm, Bundle taskBundle) {
    super(fm, FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);

    this.taskBundle = taskBundle;
  }

  /*
   * Returns value of the fragment at that position
   */
  @NonNull
  @Override
  public Fragment getItem(int pos) {
    switch (pos) {
      case 0:
        return TaskListFragment.newInstance(taskBundle);
      case 1:
        return TimelineFragment.newInstance(taskBundle);
      case 2:
        return ProgressFragment.newInstance(taskBundle);
      default:
        return TaskListFragment.newInstance();
    }
  }

  /*
   *  Returns the length of the fragment
   */
  @Override
  public int getCount() {
    return 3;
  }

  /*
   * Returns the page title
   */
  @Nullable
  @Override
  public CharSequence getPageTitle(int pos) {
    switch (pos) {
      case 0:
        return "Tasks";
      case 1:
        return "Timeline";
      case 2:
        return "Progress";
      default:
        return "";
    }
  }
}
