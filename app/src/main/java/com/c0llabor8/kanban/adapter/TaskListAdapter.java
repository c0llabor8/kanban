package com.c0llabor8.kanban.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;
import com.c0llabor8.kanban.R;
import com.c0llabor8.kanban.databinding.ListItemTaskBinding;
import com.c0llabor8.kanban.model.Task;
import java.util.List;

public class TaskListAdapter extends RecyclerView.Adapter<TaskListAdapter.ViewHolder> {

  private List<Task> tasks;

  public TaskListAdapter(List<Task> tasks) {
    this.tasks = tasks;
  }

  @NonNull
  @Override
  public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    LayoutInflater inflater = LayoutInflater.from(parent.getContext());

    ListItemTaskBinding binding =
        DataBindingUtil.inflate(inflater, R.layout.list_item_task, parent, false);

    return new ViewHolder(binding);
  }

  @Override
  public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
    final Task task = tasks.get(position);
    holder.update(task);
  }

  @Override
  public int getItemCount() {
    return tasks.size();
  }

  class ViewHolder extends RecyclerView.ViewHolder {

    private ListItemTaskBinding binding;

    ViewHolder(ListItemTaskBinding binding) {
      super(binding.getRoot());
      this.binding = binding;
    }

    void update(Task task) {
      binding.tvTitle.setText(task.getTitle());
      binding.tvDescription.setText(task.getDescription());
      binding.tvEstimate.setText(task.getEstimate());
    }

  }
}
