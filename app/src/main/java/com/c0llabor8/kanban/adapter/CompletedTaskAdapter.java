package com.c0llabor8.kanban.adapter;

import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class CompletedTaskAdapter extends RecyclerView.Adapter<CompletedTaskAdapter.ViewHolder> {

  @NonNull
  @Override
  public CompletedTaskAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent,
      int viewType) {
    return null;
  }

  @Override
  public void onBindViewHolder(@NonNull CompletedTaskAdapter.ViewHolder holder, int position) {

  }

  @Override
  public int getItemCount() {
    return 0;
  }

  public class ViewHolder extends RecyclerView.ViewHolder {

    public ViewHolder(@NonNull View itemView) {
      super(itemView);
    }
  }
}
