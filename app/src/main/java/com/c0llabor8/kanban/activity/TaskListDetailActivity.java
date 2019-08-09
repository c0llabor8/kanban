package com.c0llabor8.kanban.activity;

import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.c0llabor8.kanban.R;
import com.c0llabor8.kanban.adapter.TaskDetailAdapter;
import com.c0llabor8.kanban.fragment.dialog.NewTaskDialog;
import com.c0llabor8.kanban.model.Assignment;
import com.c0llabor8.kanban.model.Message;
import com.c0llabor8.kanban.model.Project;
import com.c0llabor8.kanban.model.Task;
import com.c0llabor8.kanban.model.TaskCategory;
import com.c0llabor8.kanban.model.query.AssignmentQuery;
import com.c0llabor8.kanban.util.DateTimeUtils;
import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import java.util.ArrayList;
import java.util.List;

public class TaskListDetailActivity extends AppCompatActivity {

  final Handler handler = new Handler();

  //to create comments
  RecyclerView rvComments;
  TaskDetailAdapter adapter;
  Task task;
  private EditText etComment;
  private Button sendBtn;
  private List<Message> messageList;
  private TextView title;
  private TextView description;
  private TextView projectAssignment;
  private TextView date;
  private TextView category;
  private TextView assignee;

  Runnable pollComments = new Runnable() {
    @Override
    public void run() {
      try {
        populateComments(); //this function can change value of mInterval.
      } finally {
        // 100% guarantee that this always happens, even if
        // your update method throws an exception
        handler.postDelayed(pollComments, 3*1000);
      }
    }
  };

  void startRepeatingTask() {
    pollComments.run();
  }

  void stopRepeatingTask() {
    handler.removeCallbacks(pollComments);
  }

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_task_list_detail);
    Toolbar toolbar = findViewById(R.id.toolbar);
    setSupportActionBar(toolbar);

    adapter = new TaskDetailAdapter(this, messageList);
    rvComments = findViewById(R.id.rvComments);
    //everything needed for comments
    etComment = findViewById(R.id.etComment);
    //textWatcher to make sure etComment is being filled
    etComment.addTextChangedListener(new TextWatcher() {
      @Override
      public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        settingButtonEnabled(charSequence);
      }
      @Override
      public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        settingButtonEnabled(charSequence);
      }
      @Override
      public void afterTextChanged(Editable editable) {
        if (etComment.length() == 0) {
          sendBtn.setEnabled(false);
        } else {
          sendBtn.setEnabled(true);
        }
      }
    });

    sendBtn = findViewById(R.id.btnSend);
    sendBtn.setEnabled(false);
    messageList = new ArrayList<>();
    adapter = new TaskDetailAdapter(this, messageList);
    rvComments = findViewById(R.id.rvComments);
    rvComments.setLayoutManager(new LinearLayoutManager(this));
    rvComments.setAdapter(adapter);

    //everything needed to display task details
    task = getIntent().getParcelableExtra(Task.class.getSimpleName());
    title = findViewById(R.id.tvTitle);
    date = findViewById(R.id.etDate);
    description = findViewById(R.id.tvDescription);
    projectAssignment = findViewById(R.id.tvProjectName);

    category = findViewById(R.id.tvCategory);
    setCategoryInDetail();

    assignee = findViewById(R.id.tvAssignment);
    setAssigneeInDetail();

    //set title of menu bar to be title of task
    getSupportActionBar().setTitle(task.getTitle());
    title.setText(task.getTitle());
    description.setText(task.getDescription());
    date.setText(DateTimeUtils.getDateString(task.getEstimate()));
    if (task.getProject() != null) {
      task.getProject().fetchIfNeededInBackground(new GetCallback<Project>() {
        @Override
        public void done(Project project, ParseException e) {
          projectAssignment.setText(project.getName());
        }
      });
    }
    sendButtonClickSetUp();
  }

  @Override
  protected void onResume() {
    super.onResume();
    startRepeatingTask();
  }


  @Override
  protected void onPause() {
    super.onPause();
    stopRepeatingTask();
  }

  @Override
  protected void onDestroy() {
    super.onDestroy();
    stopRepeatingTask();
  }

  /**
   * gets assignment and displays in detail view
   */
  private void setAssigneeInDetail() {
    AssignmentQuery query =
        new AssignmentQuery().whereTaskEquals(task);
    query.findInBackground(new FindCallback<Assignment>() {
      @Override
      public void done(List<Assignment> objects, ParseException e) {
        if (objects.size() != 0) {
          Assignment assignment = objects.get(0);
          assignee.setText("Assigned to: " + assignment.getUser().getUsername());
        }
      }
    });
  }

  /**
   * gets category and displays in detail view
   */
  private void setCategoryInDetail() {
    if (task.getCategory() != null) {
      task.getCategory().fetchIfNeededInBackground(new GetCallback<TaskCategory>() {
        @Override
        public void done(TaskCategory taskCategory, ParseException e) {
          category.setText("Category: " + task.getCategory().toString());
        }
      });
    }
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    getMenuInflater().inflate(R.menu.menu_task_details_incomplete, menu);
    return true;
  }

  /**
   * deals with when different icons are clicked on the menu app bar
   */
  @Override
  public boolean onOptionsItemSelected(@NonNull MenuItem item) {
    switch (item.getItemId()) {
      case R.id.miEdit:
        editEvent();
        return true;
      case R.id.miComplete:
        markAsComplete();
        finish();
        return true;
      case R.id.miBack:
        finish();
      default:
        return super.onOptionsItemSelected(item);
    }
  }

  /**
   * new task dialog will pop up and pass in previous information about the task to allow editing
   */
  private void editEvent() {
    NewTaskDialog dialog = NewTaskDialog.newInstance();
    dialog.show(getSupportFragmentManager(), task.getProject(), task);
  }

  /**
   * marks task as complete when the check button is clicked
   */
  private void markAsComplete() {
    task.setCompleted();
    task.saveInBackground(new SaveCallback() {
      @Override
      public void done(ParseException e) {
        if (e == null) {
          Log.d("completeTask", "Task successfully marked complete");
        } else {
          Log.d("completeTask", "Task not marked complete");
          e.printStackTrace();
        }
      }
    });
    Toast toast = Toast.makeText(this, "task has been marked as completed", Toast.LENGTH_LONG);
    toast.setGravity(Gravity.CENTER, 0, 0);
    toast.show();
  }

  /**
   * enables the button when sequence is not empty
   */
  private void settingButtonEnabled(CharSequence charSequence) {
    if (charSequence.length() == 0) {
      sendBtn.setEnabled(false);
    } else {
      sendBtn.setEnabled(true);
    }
  }

  /**
   * populates previous comments
   */
  private void populateComments() {
    ParseQuery<Message> query = ParseQuery.getQuery(Message.class);
    query.whereEqualTo("task", task);
    query.include("user");
    query.orderByAscending("createdAt");

    if (messageList.size() > 0) {
      query.whereGreaterThan("createdAt",
          messageList.get(messageList.size() - 1).getCreatedAt());
    }

    query.findInBackground(new FindCallback<Message>() {
      @Override
      public void done(List<Message> objects, ParseException e) {
        if (e == null) {
          messageList.addAll(objects);
          adapter.notifyItemRangeInserted(adapter.getItemCount(), objects.size());
          rvComments.scrollToPosition(messageList.size() - 1);
        } else {
          Log.d("HomeActivity", "get post failed");
        }
      }
    });
  }

  /**
   * setup for button to send comment
   */
  private void sendButtonClickSetUp() {
    sendBtn.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View view) {
        createComment();
      }
    });
  }

  /**
   * actually creating comment and clears adapter
   */
  private void createComment() {

    Message newComment = new Message();
    newComment.setBody(etComment.getText().toString());
    newComment.setTask(task);
    newComment.setUser(ParseUser.getCurrentUser());

    newComment.saveInBackground(new SaveCallback() {
      @Override
      public void done(ParseException e) {
        if (e == null) {
          Log.d("createComment", "comment saved successfully!");
        } else {
          Log.d("createComment", "Comment saved unsuccessfully :(");
          e.printStackTrace();
        }
      }
    });
    etComment.setText(null);
    adapter.clear();
    populateComments();

  }

}
