package com.c0llabor8.kanban.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.c0llabor8.kanban.R;
import com.c0llabor8.kanban.databinding.ListItemTaskBinding;
import com.c0llabor8.kanban.model.Task;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

public class TimelineAdapter extends RecyclerView.Adapter<TimelineAdapter.ViewHolder> {

  private static final int VIEW_TYPE_TOP = 0;
  private static final int VIEW_TYPE_MIDDLE = 1;
  private static final int VIEW_TYPE_BOTTOM = 2;
  private TextView title;
  private TextView description;
  private TextView date;

  private List<Task> tasks;

  public TimelineAdapter(List<Task> tasks) {
    this.tasks = tasks;
  }


  @NonNull
  @Override
  public TimelineAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    LayoutInflater inflater = LayoutInflater.from(parent.getContext());
    ListItemTaskBinding binding =
        ListItemTaskBinding.inflate(inflater, parent, false);

    return new ViewHolder(binding);

  }

  @Override
  public void onBindViewHolder(@NonNull TimelineAdapter.ViewHolder holder, int position) {
    final Task task = tasks.get(position);
    // Populate all tasks
    populateTasks(task);
    switch (holder.getItemViewType()) {
      case VIEW_TYPE_TOP:
        // The top of the line has to be rounded
        holder.itemLine.setBackgroundResource(R.drawable.line_bg_top);
        break;
      case VIEW_TYPE_MIDDLE:
        // but a drawable can be used to make the cap rounded also here
        holder.itemLine.setBackgroundResource(R.drawable.line_bg_middle);
        break;
      case VIEW_TYPE_BOTTOM:
        //also rounded the bottom
        holder.itemLine.setBackgroundResource(R.drawable.line_bg_bottom);
        break;
    }

  }


  @Override
  public int getItemViewType(int position) {
    if (position == 0) {
      return VIEW_TYPE_TOP;
    } else if (position == tasks.size() - 1) {
      return VIEW_TYPE_BOTTOM;
    }
    return VIEW_TYPE_MIDDLE;
  }


  @Override
  public int getItemCount() {
    return tasks.size();
  }

  class ViewHolder extends RecyclerView.ViewHolder {

    FrameLayout itemLine;

    public ViewHolder(ListItemTaskBinding binding) {
      super(binding.getRoot());
      title = itemView.findViewById(R.id.item_title);
      description = itemView.findViewById(R.id.item_description);
      itemLine = (FrameLayout) itemView.findViewById(R.id.item_line);
      date = itemView.findViewById(R.id.item_date);
    }


  }
  public void populateTasks(Task task) {
    title.setText(task.getTitle());
    description.setText(task.getDescription());
    date.setText(updateTime(task.getEstimate()));

  }
  private String updateTime(long estimate) {
    Date date = new Date(estimate);
    String format = "MM/dd/yy";
    SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.getDefault());
    sdf.setTimeZone(TimeZone.getDefault());
    String formattedDate = sdf.format(date);
    return formattedDate;
  }
}
