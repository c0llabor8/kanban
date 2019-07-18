package com.c0llabor8.kanban.fragment;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.DialogFragment;
import com.c0llabor8.kanban.R;
import com.c0llabor8.kanban.databinding.FragmentTaskCreationBinding;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class TaskCreationDialog extends AppCompatDialogFragment {

  private final Calendar calendar = Calendar.getInstance();
  private FragmentTaskCreationBinding binding;

  public static TaskCreationDialog newInstance() {

    Bundle args = new Bundle();

    TaskCreationDialog fragment = new TaskCreationDialog();
    fragment.setArguments(args);
    return fragment;
  }

  @Override
  public void onCreate(@Nullable Bundle savedInstanceState) {
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
  public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {

    super.onCreateView(inflater, container, savedInstanceState);
    binding = DataBindingUtil.inflate(inflater, R.layout.fragment_task_creation, container, false);

    DatePickerDialog.OnDateSetListener date = (view, year, monthOfYear, dayOfMonth) -> {
      calendar.set(Calendar.YEAR, year);
      calendar.set(Calendar.MONTH, monthOfYear);
      calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
      updateLabel();
    };

    binding.etDate.setOnClickListener((View v) ->
        new DatePickerDialog(getActivity(), date, calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)).show()
    );

    return binding.getRoot();
  }

  @Override
  public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    binding.toolbar.setNavigationOnClickListener((View v) -> dismiss());

    binding.toolbar.setTitle("New Task");
    binding.toolbar.inflateMenu(R.menu.menu_dialog_save);
    binding.toolbar.setOnMenuItemClickListener((MenuItem item) -> {
      dismiss();
      return true;
    });

  }

  private void updateLabel() {
    String myFormat = "MM/dd/yy"; //In which you need put here
    SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

    binding.etDate.setText(sdf.format(calendar.getTime()));
  }
}
