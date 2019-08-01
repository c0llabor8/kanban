package com.c0llabor8.kanban.fragment.dialog;

import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.Dialog;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar.OnMenuItemClickListener;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;
import com.c0llabor8.kanban.R;
import com.c0llabor8.kanban.databinding.FragmentNewTaskBinding;
import com.c0llabor8.kanban.model.Assignment;
import com.c0llabor8.kanban.model.Project;
import com.c0llabor8.kanban.model.Task;
import com.c0llabor8.kanban.model.TaskCategory;
import com.c0llabor8.kanban.util.DateTimeUtils;
import com.c0llabor8.kanban.util.MemberProvider;
import com.c0llabor8.kanban.util.TaskProvider;
import com.parse.ParseUser;
import java.util.Calendar;

public class NewTaskDialog extends DialogFragment {

  private FragmentNewTaskBinding binding;
  private Calendar calendar = Calendar.getInstance();
  private ArrayAdapter<TaskCategory> categoryArrayAdapter;
  private ArrayAdapter<String> usernameArrayAdapter;
  private long estimate;
  private DatePickerDialog datePickerDialog;
  private TaskRefreshListener listener;
  private Project project;

  private OnMenuItemClickListener onMenuItemClickListener = new OnMenuItemClickListener() {
    @Override
    public boolean onMenuItemClick(MenuItem item) {
      final String title = binding.etTitle.getText().toString();
      final String description = binding.etDescription.getText().toString();

      final ParseUser assignee = MemberProvider.getParseUser(
          project,
          binding.etAssignee.getText().toString()
      );

      final TaskCategory category = TaskProvider.getCategory(
          project,
          binding.etCategory.getText().toString()
      );

      final String date = binding.etDate.getText().toString();

      if (title.length() > 0 && date.length() > 0) {
        Task.createNew(title, description, estimate, assignee, project, category,
            e -> listener.onTaskRefresh());
        dismiss();
      }

      return true;
    }
  };

  private OnDateSetListener onDateSetListener = (DatePicker datePicker, int year, int month, int day) -> {
    calendar.set(Calendar.YEAR, year);
    calendar.set(Calendar.MONTH, month);
    calendar.set(Calendar.DAY_OF_MONTH, day);
    calendar.set(Calendar.HOUR, 0);
    calendar.set(Calendar.MINUTE, 0);
    calendar.set(Calendar.SECOND, 0);
    calendar.set(Calendar.MILLISECOND, 0);

    estimate = calendar.getTimeInMillis();

    binding.etDate.setText(DateTimeUtils.getDateString(estimate));
  };

  private OnClickListener onDateClickedListener = (View view) -> datePickerDialog.show();

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

    datePickerDialog = new DatePickerDialog(
        getContext(),
        onDateSetListener,
        calendar.get(Calendar.YEAR),
        calendar.get(Calendar.MONTH),
        calendar.get(Calendar.DAY_OF_MONTH)
    );

    datePickerDialog.getDatePicker()
        .setMinDate(System.currentTimeMillis() + DateUtils.DAY_IN_MILLIS);

    if (project != null) {
      categoryArrayAdapter = new ArrayAdapter<>(
          getContext(),
          R.layout.support_simple_spinner_dropdown_item,
          TaskProvider.getInstance().getCategories(project)
      );

      usernameArrayAdapter = new ArrayAdapter<>(
          getContext(),
          R.layout.support_simple_spinner_dropdown_item,
          MemberProvider.getInstance().getUsernameList(project)
      );
    }
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

    binding.toolbar.setOnMenuItemClickListener(onMenuItemClickListener);
    binding.tilDate.setEndIconOnClickListener(onDateClickedListener);

    if (project != null) {
      binding.etCategory.setAdapter(categoryArrayAdapter);

      binding.etCategory.setText(
          binding.etCategory.getAdapter().getItem(0).toString(),
          false
      );

      binding.etAssignee.setAdapter(usernameArrayAdapter);

      binding.etAssignee.setText(
          binding.etAssignee.getAdapter().getItem(0).toString(),
          false
      );
    } else {
      binding.tilCategory.setVisibility(View.GONE);
      binding.tilAssignees.setVisibility(View.GONE);
    }
  }

  private void createTask(String taskTitle, String taskDescription, Long date) {
    Assignment assignment = new Assignment();
    // assignment.setUser();
    Task task = new Task();
    task.setTitle(taskTitle);
    task.setCategory(TaskProvider.getInstance().getCategoryMap(project)
        .get(binding.etCategory.getText().toString()));
    task.setDescription(taskDescription);
    task.setEstimate(date);

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
