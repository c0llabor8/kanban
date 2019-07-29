package com.c0llabor8.kanban.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.c0llabor8.kanban.databinding.ListItemTaskBinding;
import com.c0llabor8.kanban.model.Task;
import com.c0llabor8.kanban.model.TaskCategory;
import com.c0llabor8.kanban.util.DateTimeUtils;
import com.parse.GetCallback;
import java.util.List;

/*
 * This class is the adapter used to display tasks into a task list
 * */

public class TaskListAdapter extends RecyclerView.Adapter<TaskListAdapter.ViewHolder> {

  private static final int VIEW_WITH_HEADER = 0;
  private static final int VIEW_WITHOUT_HEADER = 1;

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

    return new ViewHolder(binding, viewType);
  }

  @Override
  public int getItemViewType(int position) {
    final Task task = tasks.get(position);
    final TaskCategory category = task.getCategory();

    if (category != null) {
      if (position == 0 || !category.equals(tasks.get(position - 1).getCategory())) {
        return VIEW_WITH_HEADER;
      }
    }

    return VIEW_WITHOUT_HEADER;
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
   * This class is used to store the view bindings for each of the list items
   * */
  class ViewHolder extends RecyclerView.ViewHolder {

    private int type;
    private ListItemTaskBinding binding;

    ViewHolder(ListItemTaskBinding binding, int type) {
      super(binding.getRoot());
      this.binding = binding;
      this.type = type;
    }

    void update(Task task) {
      binding.tvTitle.setText(task.getTitle());
      binding.tvDescription.setText(task.getDescription());
      binding.tvEstimate.setText(DateTimeUtils.getDateString(task.getEstimate()));

      if (type == VIEW_WITH_HEADER && headers) {
        task.getCategory().fetchIfNeededInBackground((GetCallback<TaskCategory>) (category, e) -> {
          binding.sectionHeader.setText(category.getTitle());
          binding.sectionHeader.setVisibility(View.VISIBLE);
        });
      }
    }
  }
}
