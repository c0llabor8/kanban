package com.c0llabor8.kanban.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.c0llabor8.kanban.model.Membership;
import java.util.List;

public class CompletedTaskAdapter extends RecyclerView.Adapter<CompletedTaskAdapter.ViewHolder> {

  public CompletedTaskAdapter(List<Membership> memberList) {

  }

  public CompletedTaskAdapter(Context context, List<Membership> memberList) {

  }

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
