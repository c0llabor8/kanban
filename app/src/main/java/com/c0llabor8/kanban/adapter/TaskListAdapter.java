package com.c0llabor8.kanban.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.EditText;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.c0llabor8.kanban.databinding.ListItemTaskBinding;
import com.c0llabor8.kanban.model.Task;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

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
        ListItemTaskBinding.inflate(inflater, parent, false);

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

      binding.tvEstimate.setText(updateTime(task.getEstimate()));
    }

  }

  public String updateTime(Long unixTime) {
    Date date = new Date(unixTime);
    String format = "MM/dd/yy";
    SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.getDefault());
    sdf.setTimeZone(TimeZone.getDefault());
    String formattedDate = sdf.format(date);
    return formattedDate;
  }

  /**
   * long unixSeconds = 1372339860;
   * // convert seconds to milliseconds
   * Date date = new java.util.Date(unixSeconds*1000L);
   * // the format of your date
   * SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss z");
   * // give a timezone reference for formatting (see comment at the bottom)
   * sdf.setTimeZone(java.util.TimeZone.getTimeZone("GMT-4"));
   * String formattedDate = sdf.format(date);
   * System.out.println(formattedDate);
   */
}
