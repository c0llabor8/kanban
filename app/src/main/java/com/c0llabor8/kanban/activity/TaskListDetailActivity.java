package com.c0llabor8.kanban.activity;

import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.c0llabor8.kanban.R;
import com.c0llabor8.kanban.adapter.TaskDetailAdapter;
import com.c0llabor8.kanban.model.Project;
import com.c0llabor8.kanban.model.Task;
import com.parse.GetCallback;
import com.parse.ParseException;

public class TaskListDetailActivity extends AppCompatActivity {


  private TextView title;
  private TextView description;
  private TextView projectAssignment;
  private ImageView priorityLevel;
  private Task task;
  RecyclerView rvComments;
  TaskDetailAdapter adapter;


  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_task_list_detail);

    //setting up recyclerView for comments
    adapter = new TaskDetailAdapter();
    rvComments = (RecyclerView) findViewById(R.id.rvComments);
    rvComments.setLayoutManager(new LinearLayoutManager(this));
    rvComments.setAdapter(adapter);

    //getting task title, description, etc. to be displayed
    task = getIntent().getParcelableExtra(Task.class.getSimpleName());
    title = findViewById(R.id.tvTitle);
    description = findViewById(R.id.tvDescription);
    projectAssignment = findViewById(R.id.tvAssignment);
    priorityLevel = findViewById(R.id.ivPriorityLevel);

    title.setText(task.getTitle());
    description.setText(task.getDescription());
    if (task.getProject() != null) {
      task.getProject().fetchIfNeededInBackground(new GetCallback<Project>() {
        @Override
        public void done(Project project, ParseException e) {
          projectAssignment.setText(project.getName());
        }
      });
    }

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
