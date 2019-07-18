package com.c0llabor8.kanban.activity;

import android.app.DatePickerDialog;
import android.app.Dialog;
import java.text.SimpleDateFormat;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import androidx.appcompat.widget.Toolbar;
import androidx.appcompat.widget.Toolbar.OnMenuItemClickListener;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;
import com.c0llabor8.kanban.R;
import java.util.Calendar;
import java.util.Locale;

public class TaskCreationActivity extends DialogFragment {

  public static final String TAG = "task_creation";
  final Calendar calendar = Calendar.getInstance();
  private EditText etDate;
  private Toolbar toolbar;

  public static TaskCreationActivity display(FragmentManager fragmentManager) {
    TaskCreationActivity dialog = new TaskCreationActivity();
    dialog.show(fragmentManager, TAG);
    return dialog;
  }

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

  }


  @Override
  public void onStart() {
    super.onStart();
    Dialog dialog = getDialog();
    if (dialog != null) {
      int width = ViewGroup.LayoutParams.MATCH_PARENT;
      int height = ViewGroup.LayoutParams.MATCH_PARENT;
      dialog.getWindow().setLayout(width, height);
    }
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    super.onCreateView(inflater, container, savedInstanceState);
    View view = inflater.inflate(R.layout.fragment_task, container, false);
    etDate = view.findViewById(R.id.etDate);
    toolbar = view.findViewById(R.id.toolbar);
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

    return view;
  }

  @Override
  public void onViewCreated(View view, Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    toolbar.setNavigationOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View view) {
        dismiss();
      }
    });
    toolbar.setTitle("New Task");
    toolbar.inflateMenu(R.menu.task_creation);
    toolbar.setOnMenuItemClickListener(new OnMenuItemClickListener() {
      @Override
      public boolean onMenuItemClick(MenuItem item) {
        dismiss();
        return true;
      }
    });

  }

  private void updateLabel() {
    String myFormat = "MM/dd/yy"; //In which you need put here
    SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

    etDate.setText(sdf.format(calendar.getTime()));
  }
}
