package com.c0llabor8.kanban.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.c0llabor8.kanban.R;

public class BasicFragment extends Fragment {

  // newInstance constructor for creating fragment with arguments
  public static BasicFragment newInstance() {

    Bundle args = new Bundle();

    args.putString("title", "BasicFragment");

    BasicFragment fragment = new BasicFragment();
    fragment.setArguments(args);
    return fragment;
  }

  // Inflate the view for the fragment based on layout XML
  @Nullable
  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {
    //return super.onCreateView(inflater, container, savedInstanceState);
    return inflater.inflate(R.layout.fragment_basic, container, false);

  }

}
