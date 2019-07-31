package com.c0llabor8.kanban.util;

import android.content.Context;
import android.view.LayoutInflater;
import com.c0llabor8.kanban.R;
import com.c0llabor8.kanban.databinding.LayoutTextInputBinding;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

public class DialogUtils {

  public static void textInputDialog(Context context, String title, StringResultListener listener) {
    textInputDialog(context, title, "", listener);
  }

  public static void textInputDialog(Context context, String title, String hint,
      StringResultListener listener) {
    textInputDialog(context, title, hint, "Continue", listener);
  }

  public static void textInputDialog(Context context, String title, String hint, String actionLabel,
      StringResultListener listener) {
    LayoutTextInputBinding binding = LayoutTextInputBinding.inflate(LayoutInflater.from(context));

    MaterialAlertDialogBuilder builder =
        new MaterialAlertDialogBuilder(context, R.style.ThemeOverlay_MaterialComponents_Dialog)
            .setTitle(title);

    binding.inputLayout.setHint(hint);

    builder.setView(binding.getRoot())
        .setPositiveButton(actionLabel, (dialogInterface, i) -> {
          listener.done(binding.editText.getText().toString());
          dialogInterface.dismiss();
        });

    builder.show();
  }

  public interface StringResultListener {

    void done(String string);
  }
}
