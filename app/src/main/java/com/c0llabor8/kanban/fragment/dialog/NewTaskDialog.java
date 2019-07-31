package com.c0llabor8.kanban.fragment.dialog;

import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;
import com.c0llabor8.kanban.R;
import com.c0llabor8.kanban.databinding.FragmentNewTaskBinding;
import com.c0llabor8.kanban.model.Assignment;
import com.c0llabor8.kanban.model.Project;
import com.c0llabor8.kanban.model.Task;
import com.parse.ParseUser;

public class NewTaskDialog extends DialogFragment {

  private TaskRefreshListener listener;
  private FragmentNewTaskBinding binding;
  private Project project;

  public static NewTaskDialog newInstance() {

    Bundle args = new Bundle();

    NewTaskDialog fragment = new NewTaskDialog();
    fragment.setArguments(args);
    return fragment;
  }

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setStyle(DialogFragment.STYLE_NO_FRAME, R.style.AppTheme);
  }

  public void show(@NonNull FragmentManager manager, Project project) {
    this.project = project;
    super.show(manager, "");
  }

  @Override
  public void onStart() {
    super.onStart();
    Dialog dialog = getDialog();

    if (dialog != null) {
      int width = ViewGroup.LayoutParams.MATCH_PARENT;
      int height = ViewGroup.LayoutParams.MATCH_PARENT;
      dialog.getWindow().setLayout(width, height);
      dialog.getWindow().setWindowAnimations(R.style.Animation_MyTheme_BottomSheet_Modal);
    }
  }

  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    super.onCreateView(inflater, container, savedInstanceState);

    binding = FragmentNewTaskBinding.inflate(inflater, container, false);
    return binding.getRoot();
  }

  @Override
  public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    binding.toolbar.setNavigationOnClickListener((View v) -> dismiss());
    binding.toolbar.setTitle("New Task");
    binding.toolbar.inflateMenu(R.menu.menu_dialog_save);

    binding.toolbar.setOnMenuItemClickListener(item -> {
      final String title = binding.etTitle.getText().toString();
      final String description = binding.etDescription.getText().toString();
      createTask(title, description, 0L, 0);
      dismiss();
      return true;
    });
  }

//  private void updateLabel() {
//    String myFormat = "MM/dd/yy"; //In which you need put here
//    SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
//
//    etDate.setText(sdf.format(calendar.getTime()));
//  }

  private void createTask(String taskTitle, String taskDescription, Long date, int priority) {
    Assignment assignment = new Assignment();
    assignment.setUser(ParseUser.getCurrentUser());
    Task task = new Task();
    task.setTitle(taskTitle);
    task.setDescription(taskDescription);
    task.setEstimate(date);
    task.setPriority(priority);

    if (project != null) {
      task.setProject(project);
    }

    task.saveInBackground(e -> {
      if (e != null) {
        Log.d("TaskCreationActivity", "Error while saving task");
        e.printStackTrace();
        return;
      }
      Log.d("TaskCreationActivity", "Task saved successfully!");
      assignment.setTask(task);
      saveTask(assignment);
    });
  }

  private void saveTask(Assignment assignment) {
    assignment.saveInBackground(e -> {
      if (e != null) {
        Log.d("TaskCreationActivity", "Error while saving task");
        e.printStackTrace();
        return;
      }

      listener.onTaskRefresh();
      Log.d("TaskCreationActivity", "Task saved successfully!");
    });
  }

  public void setListener(TaskRefreshListener listener) {
    this.listener = listener;
  }

  public interface TaskRefreshListener {

    void onTaskRefresh();
  }
}
