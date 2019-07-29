package com.c0llabor8.kanban.activity;

import android.os.Bundle;
import android.os.Parcel;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.c0llabor8.kanban.R;
import com.c0llabor8.kanban.model.Project;
import com.c0llabor8.kanban.model.Task;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import org.parceler.Parcels;

public class TaskListDetailActivity extends AppCompatActivity{


  private TextView title;
  private TextView description;
  private TextView projectAssignment;
  private ImageView priorityLevel;
  private Task task;

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_task_list_detail);

    task = (Task) Parcels.unwrap(getIntent().getParcelableExtra(Task.class.getSimpleName()));


    title = findViewById(R.id.tvTitle);
    description = findViewById(R.id.tvDescription);
    projectAssignment = findViewById(R.id.tvAssignment);
    priorityLevel = findViewById(R.id.ivPriorityLevel);


    title.setText(task.getTitle());
    description.setText(task.getDescription());
    task.getProject().fetchIfNeededInBackground(new GetCallback<Project>() {
      @Override
      public void done(Project project, ParseException e) {
        projectAssignment.setText(project.getName());
      }
    });
    setPriorityLevel();
  }

  /**
   * sets the different icons based on priority level
   */
  private void setPriorityLevel() {
    if (task.getPriority() == 0) {
      priorityLevel.setImageResource(R.drawable.circle_priority_low);
    } else if (task.getPriority() == 1) {
      priorityLevel.setImageResource(R.drawable.circle_priority_medium);
    } else {
      priorityLevel.setImageResource(R.drawable.circle_priority_high);
    }
  }
}
