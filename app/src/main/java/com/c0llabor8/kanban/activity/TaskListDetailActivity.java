package com.c0llabor8.kanban.activity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.c0llabor8.kanban.R;
import com.c0llabor8.kanban.adapter.TaskDetailAdapter;
import com.c0llabor8.kanban.model.Message;
import com.c0llabor8.kanban.model.Project;
import com.c0llabor8.kanban.model.Task;
import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import java.util.ArrayList;
import java.util.List;

public class TaskListDetailActivity extends AppCompatActivity {
  //to create comments
  RecyclerView rvComments;
  TaskDetailAdapter adapter;
  private EditText etComment;
  private Button sendBtn;
  private List<Message> messageList;
  private TextView title;
  private TextView description;
  private TextView projectAssignment;
  private ImageView priorityLevel;
  private Task task;

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_task_list_detail);
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
    rvComments = (RecyclerView) findViewById(R.id.rvComments);
    rvComments.setLayoutManager(new LinearLayoutManager(this));
    rvComments.setAdapter(adapter);

    //everything needed to display task details
    task = getIntent().getParcelableExtra(Task.class.getSimpleName());
    title = findViewById(R.id.tvTitle);
    description = findViewById(R.id.tvDescription);
    projectAssignment = findViewById(R.id.tvAssignment);
    priorityLevel = findViewById(R.id.ivPriorityLevel);
    title.setText(task.getTitle());
    description.setText(task.getDescription());
    //make sure there is project assigned to task
    if (task.getProject() != null) {
      task.getProject().fetchIfNeededInBackground(new GetCallback<Project>() {
        @Override
        public void done(Project project, ParseException e) {
          projectAssignment.setText(project.getName());
        }
      });
    }
    setPriorityLevel();
    sendButtonClickSetUp();
    populateComments();
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
    query.orderByDescending("createdAt");
    query.findInBackground(new FindCallback<Message>() {
      @Override
      public void done(List<Message> objects, ParseException e) {
        if (e == null) {
          messageList.addAll(objects);
          adapter.notifyItemInserted(0);
          rvComments.scrollToPosition(0);
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
