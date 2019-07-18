package com.c0llabor8.kanban.activity;

import android.os.Bundle;
import android.view.MenuItem;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import com.c0llabor8.kanban.R;
import com.c0llabor8.kanban.adapter.ProjectPagerAdapter;
import com.c0llabor8.kanban.databinding.ActivityMainBinding;
import com.c0llabor8.kanban.fragment.BasicFragment;
import com.c0llabor8.kanban.fragment.BottomSheetNavFragment;
import com.google.android.material.snackbar.Snackbar;

public class MainActivity extends AppCompatActivity {

  ActivityMainBinding binding;
  ProjectPagerAdapter pagerAdapter;

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

    Fragment[] fragments = {
        BasicFragment.newInstance()
    };

    pagerAdapter = new ProjectPagerAdapter(getSupportFragmentManager(), fragments);

    binding.pager.setAdapter(pagerAdapter);
    binding.tabs.setupWithViewPager(binding.pager, true);

    setSupportActionBar(binding.bar);
  }

  /*
   * Sets the text for TextView(R.id.title) within the BottomAppBar
   *
   * @param title text to display in as the title
   * */
  @Override
  public void setTitle(CharSequence title) {
    binding.title.setText(title);
  }

  @Override
  public boolean onOptionsItemSelected(@NonNull MenuItem item) {

    // If the menu on the bottom app bar is clicked
    if (item.getItemId() == android.R.id.home) {
      BottomSheetNavFragment.newInstance().show(getSupportFragmentManager(), "");
      return true;
    }

    return super.onOptionsItemSelected(item);
  }
}
