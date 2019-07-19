package com.c0llabor8.kanban.fragment.dialog;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.util.Log;
import android.widget.RadioButton;
import com.c0llabor8.kanban.model.Assignment;
import com.c0llabor8.kanban.model.Task;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import java.text.SimpleDateFormat;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.DialogFragment;
import com.c0llabor8.kanban.R;
import java.util.Calendar;
import java.util.Locale;

public class NewTaskDialog extends DialogFragment {

  public static final String TAG = "task_creation";
  final Calendar calendar = Calendar.getInstance();


  private EditText etDate;
  private Toolbar toolbar;
  private EditText etDescription;
  private EditText etTitle;
  private Long estimate;
  private int priorityValue;
  private RadioButton high;
  private RadioButton medium;
  private RadioButton low;

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
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    super.onCreateView(inflater, container, savedInstanceState);
    View view = inflater.inflate(R.layout.fragment_new_task, container, false);


    etDate = view.findViewById(R.id.etDate);
    toolbar = view.findViewById(R.id.toolbar);
    etDescription = view.findViewById(R.id.etDescription);
    etTitle = view.findViewById(R.id.etTitle);
    estimate = calendar.getTimeInMillis();
    high = view.findViewById(R.id.rgHigh);
    medium = view.findViewById(R.id.rgMedium);
    low = view.findViewById(R.id.rgLow);


    DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

      @Override
      public void onDateSet(DatePicker view, int year, int monthOfYear,
          int dayOfMonth) {
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, monthOfYear);
        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        updateLabel();
      }

    };

    etDate.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View v) {
        new DatePickerDialog(getActivity(), date, calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)).show();
      }
    });

    high.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View view) {
        priorityValue = 2;
      }
    });

    medium.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View view) {
        priorityValue = 1;
      }
    });

    low.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View view) {
        priorityValue = 0;
      }
    });

    return view;
  }

  @Override
  public void onViewCreated(View view, Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    toolbar.setNavigationOnClickListener((View v) -> dismiss());
    toolbar.setTitle("New Task");
    toolbar.inflateMenu(R.menu.menu_dialog_save);

    toolbar.setOnMenuItemClickListener(item -> {
      final String title = etTitle.getText().toString();
      final String description = etDescription.getText().toString();
      createTask(title, description, estimate, priorityValue);
      dismiss();
      return true;
    });
  }

  private void updateLabel() {
    String myFormat = "MM/dd/yy"; //In which you need put here
    SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

    etDate.setText(sdf.format(calendar.getTime()));
  }

  private void createTask(String taskTitle, String taskDescription, long date, int priority) {
    Assignment assignment = new Assignment();
    assignment.setUser(ParseUser.getCurrentUser());
    Task task = new Task();
    task.setTitle(taskTitle);
    task.setDescription(taskDescription);
    task.setEstimate(date);
    task.setPriority(priority);


    task.saveInBackground(new SaveCallback() {
      @Override
      public void done(ParseException e) {
        if (e != null) {
          Log.d("TaskCreationActivity", "Error while saving task");
          e.printStackTrace();
          return;
        }
        Log.d("TaskCreationActivity", "Task saved successfully!");
        assignment.setTask(task);
        saveTask(assignment);
      }
    });
  }

  private void saveTask(Assignment assignment) {
    assignment.saveInBackground(new SaveCallback() {
      @Override
      public void done(ParseException e) {
        if (e != null) {
          Log.d("TaskCreationActivity", "Error while saving task");
          e.printStackTrace();
          return;
        }
        Log.d("TaskCreationActivity", "Task saved successfully!");
      }
    });
  }
}
