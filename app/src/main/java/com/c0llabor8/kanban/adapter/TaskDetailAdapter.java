package com.c0llabor8.kanban.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.c0llabor8.kanban.R;
import java.util.List;

public class TaskDetailAdapter extends RecyclerView.Adapter<TaskDetailAdapter.ViewHolder> {

  //list of comments
  // List<Comments> comments;
  Context context;

  //public TaskDetailAdapter(List<Comments> comments) {
  //  this.comments = comments;
  //}
  @Override
  public void onBindViewHolder(@NonNull TaskDetailAdapter.ViewHolder holder, int position,
      @NonNull List<Object> payloads) {
    super.onBindViewHolder(holder, position, payloads);
  }

  @Override
  public int getItemCount() {
    return 0;
  }

  @NonNull
  @Override
  public TaskDetailAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    //get the context and create the inflater
    context = parent.getContext();
    LayoutInflater inflater = LayoutInflater.from(context);
    //create the view using the item_movie layout
    View commentView = inflater.inflate(R.layout.task_comments, parent, false);
    //return a new ViewHolder
    return new ViewHolder(commentView);

  }

  @Override
  public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

  }


  public class ViewHolder extends RecyclerView.ViewHolder {

    EditText comment;
    ImageView profileImage;
    TextView testing;

    public ViewHolder(@NonNull View itemView) {
      super(itemView);
      comment = itemView.findViewById(R.id.etComment);
      profileImage = itemView.findViewById(R.id.ivProfileImage);
      testing = itemView.findViewById(R.id.tvRandom);
    }
  }
}
