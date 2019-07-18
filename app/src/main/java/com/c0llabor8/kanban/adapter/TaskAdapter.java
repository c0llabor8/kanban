package com.c0llabor8.kanban.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.c0llabor8.kanban.R;
import com.c0llabor8.kanban.model.Task;
import com.parse.ParseQuery;
import java.util.ArrayList;
import java.util.List;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.ViewHolder> {

  private ArrayList<Task> tasks;
  private Context context;

  public TaskAdapter(Context context, ArrayList<Task> tasks) {
    this.context = context;
    this.tasks = tasks;
  }

  @NonNull
  @Override
  public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    View view = LayoutInflater.from(context).inflate(R.layout.task_item, parent, false);
    return new ViewHolder(view);
  }

  @Override
  public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
    final Task task = tasks.get(position);
    holder.bind(task);
  }

  @Override
  public int getItemCount() {
    return tasks.size();
  }

  public static class ViewHolder extends RecyclerView.ViewHolder {

    private TextView tvTitle;
    private TextView tvDescription;
    private TextView tvEstimate;

    public ViewHolder(@NonNull View itemView) {
      super(itemView);
      tvTitle = itemView.findViewById(R.id.tvTitle);
      tvDescription = itemView.findViewById(R.id.tvDescription);
      tvEstimate = itemView.findViewById(R.id.tvEstimate);
    }

    public void bind(Task task) {
      tvTitle.setText(task.getTitle());
      tvDescription.setText(task.getDescription());
      tvEstimate.setText(task.getEstimate());
    }

  }
}
