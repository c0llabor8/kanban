package com.c0llabor8.kanban.fragment.dialog;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.DialogFragment;
import com.c0llabor8.kanban.R;
import com.c0llabor8.kanban.databinding.FragmentStringResultBinding;

public class StringResultDialog extends AppCompatDialogFragment {

  private FragmentStringResultBinding binding;
  private StringResultCallback listener;

  public static StringResultDialog newInstance(String title, String label) {
    Bundle args = new Bundle();
    args.putString("title", title);
    args.putString("label", label);

    StringResultDialog fragment = new StringResultDialog();
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

  @Nullable
  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {

    binding = DataBindingUtil.inflate(inflater, R.layout.fragment_string_result, container, false);
    return binding.getRoot();
  }

  @Override
  public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

    // Dismiss the dialog when the navigation button is clicked
    binding.toolbar.setNavigationOnClickListener((View v) -> dismiss());

    binding.toolbar.inflateMenu(R.menu.menu_dialog_save);
    binding.toolbar.setTitle(getArguments().getString("title", ""));
    binding.etLabel.setHint(getArguments().getString("label", ""));

    binding.toolbar.setOnMenuItemClickListener(item -> {
      listener.done(binding.etTitle.getText().toString());
      return true;
    });
  }

  // Set the class that will be listening for when a project is created
  public void onStringResult(StringResultCallback listener) {
    this.listener = listener;
  }

  public interface StringResultCallback {

    void done(String string);
  }
}
