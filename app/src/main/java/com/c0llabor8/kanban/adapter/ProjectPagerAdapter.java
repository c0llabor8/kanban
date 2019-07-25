package com.c0llabor8.kanban.adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import com.c0llabor8.kanban.fragment.ProgressFragment;
import com.c0llabor8.kanban.fragment.TaskListFragment;
import com.c0llabor8.kanban.fragment.TimelineFragment;
import com.c0llabor8.kanban.model.Project;

/*
 * Version of the pager to page through fragments as a set of tabs
 */
public class ProjectPagerAdapter extends FragmentPagerAdapter {

  private Fragment[] pages;

  /*
   * Passes the tasks as a Bundle into a new instance
   * Makes an array of fragments to place the taskListFragment
   */
  public ProjectPagerAdapter(@NonNull FragmentManager fm, Project project) {
    super(fm, FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);

    pages = new Fragment[]{
        TaskListFragment.newInstance(project),
        TimelineFragment.newInstance(project),
        ProgressFragment.newInstance(project)
    };
  }

  /*
   * Returns value of the fragment at that position
   */
  @NonNull
  @Override
  public Fragment getItem(int pos) {
    return pages[pos];
  }

  /*
   *  Returns the length of the fragment
   */
  @Override
  public int getCount() {
    return pages.length;
  }

  /*
   * Returns the page title
   */
  @Nullable
  @Override
  public CharSequence getPageTitle(int pos) {
    return getItem(pos).getArguments().getString("title", "");
  }
}
