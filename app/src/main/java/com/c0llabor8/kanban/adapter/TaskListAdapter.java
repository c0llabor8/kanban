package com.c0llabor8.kanban.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.c0llabor8.kanban.databinding.ListItemTaskBinding;
import com.c0llabor8.kanban.model.Task;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

/*
 * This class is the adapter used to display tasks into a task list
 * */

public class TaskListAdapter extends RecyclerView.Adapter<TaskListAdapter.ViewHolder> {

  private Boolean headers;
  private List<Task> tasks;

  /*
   * Constructor method stores a reference to a list of tasks to adapt into a RecyclerView
   * */
  public TaskListAdapter(List<Task> tasks) {
    this(tasks, false);
  }

  public TaskListAdapter(List<Task> tasks, Boolean headers) {
    this.headers = headers;
    this.tasks = tasks;
  }

  /*
   * Inflates the view binding object then stores it into the ViewHolder
   * */
  @NonNull
  @Override
  public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    LayoutInflater inflater = LayoutInflater.from(parent.getContext());

    ListItemTaskBinding binding =
        ListItemTaskBinding.inflate(inflater, parent, false);

    return new ViewHolder(binding);
  }

  /*
   * Bind the task at the list position to the list item view
   * */
  @Override
  public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
    final Task task = tasks.get(position);
    holder.update(task);
  }

  @Override
  public int getItemCount() {
    return tasks.size();
  }

  /*
   * This method return the formatted date string of a timestamp
   * */
  public String updateTime(Long unixTime) {
    Date date = new Date(unixTime);
    String format = "MM/dd/yy";
    SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.getDefault());
    sdf.setTimeZone(TimeZone.getDefault());
    String formattedDate = sdf.format(date);
    return formattedDate;
  }

  /*
   * This class is used to store the view bindings for each of the list items
   * */
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
}
