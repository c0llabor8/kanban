package com.c0llabor8.kanban.fragment.dialog;

import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.Dialog;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar.OnMenuItemClickListener;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import com.c0llabor8.kanban.R;
import com.c0llabor8.kanban.databinding.FragmentNewTaskBinding;
import com.c0llabor8.kanban.model.Project;
import com.c0llabor8.kanban.model.Task;
import com.c0llabor8.kanban.model.TaskCategory;
import com.c0llabor8.kanban.util.DateTimeUtils;
import com.c0llabor8.kanban.util.MemberProvider;
import com.c0llabor8.kanban.util.TaskProvider;
import com.google.android.material.snackbar.Snackbar;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import java.util.Calendar;

public class NewTaskDialog extends DialogFragment {

  private FragmentNewTaskBinding binding;
  private Task task;
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
      if (task == null) {
        newTaskCreation();
      } else {
        editTask();
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

  /**
   * edits existing task
   */
  private void editTask() {
    final String titleEdit = binding.etTitle.getText().toString();
    final String descriptionEdit = binding.etDescription.getText().toString();
    final TaskCategory categoryEdit = TaskProvider.getCategory(
        project,
        binding.etCategory.getText().toString()
    );
    final ParseUser assigneeEdit = MemberProvider.getParseUser(
        project,
        binding.etAssignee.getText().toString()
    );
    Task.editTask(task, titleEdit, descriptionEdit, estimate,
        project, categoryEdit, assigneeEdit, new SaveCallback() {
          @Override
          public void done(ParseException e) {
            if (e != null) {
              Snackbar.make(
                  binding.getRoot(),
                  "task could not be edited",
                  Snackbar.LENGTH_SHORT
              ).show();
              return;
            }
            if (listener != null) {
              listener.onTaskRefresh();
            }
          }
        });
    Toast toast = Toast.makeText(getContext(), "task has been edited", Toast.LENGTH_LONG);
    toast.setGravity(Gravity.CENTER, 0, 0);
    toast.show();
    getActivity().finish();
  }

  /**
   * creates new task
   */
  private void newTaskCreation() {
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
    if (!title.isEmpty() && !date.isEmpty() && (project == null || assignee != null)) {
      Task.createNew(title, description, estimate, assignee, project, category,
          (ParseException e) -> {
            if (e != null) {
              Snackbar.make(
                  binding.getRoot(),
                  "Couldn't create this task",
                  Snackbar.LENGTH_SHORT
              ).show();
              return;
            }
            if (listener != null) {
              listener.onTaskRefresh();
            }
            dismiss();
          }
      );
      Toast toast = Toast.makeText(getContext(), "task has been created", Toast.LENGTH_SHORT);
      toast.setGravity(Gravity.CENTER, 0, 0);
      toast.show();
    }
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

    updateSpinners();
  }

  private void updateSpinners() {
    // Only show categories and username selector while in a project
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

  public void show(@NonNull FragmentManager manager, Project project, Task task) {
    this.project = project;
    this.task = task;
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

    // Only show categories and username selector while in a project
    if (project != null) {
      binding.etCategory.setAdapter(categoryArrayAdapter);
      binding.etAssignee.setAdapter(usernameArrayAdapter);
    } else {
      binding.tilCategory.setVisibility(View.GONE);
      binding.tilAssignees.setVisibility(View.GONE);
    }

    //show previous input data when editing tasks so user can see what they are changing
    if (task != null) {
      estimate = task.getEstimate();
      binding.etTitle.setText(task.getTitle());
      binding.etDescription.setText(task.getDescription());
      binding.etDate.setText(DateTimeUtils.getDateString(task.getEstimate()));
      if (task.getCategory() != null) {
        task.getCategory().fetchIfNeededInBackground(new GetCallback<TaskCategory>() {
          @Override
          public void done(TaskCategory category, ParseException e) {
            binding.etCategory.setText(category.toString(), false);
          }
        });
      }
    }
  }


  public void setListener(TaskRefreshListener listener) {
    this.listener = listener;
  }

  public interface TaskRefreshListener {

    void setSwipeRefresh(SwipeRefreshLayout layout);

    void onTaskRefresh();
  }
}
