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
import com.c0llabor8.kanban.databinding.FragmentNewProjectBinding;
import com.c0llabor8.kanban.model.Membership;
import com.c0llabor8.kanban.model.Project;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SaveCallback;

public class NewProjectDialog extends AppCompatDialogFragment {

  FragmentNewProjectBinding binding;

  public static NewProjectDialog newInstance() {

    Bundle args = new Bundle();

    NewProjectDialog fragment = new NewProjectDialog();
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

    binding = DataBindingUtil.inflate(inflater, R.layout.fragment_new_project, container, false);
    return binding.getRoot();
  }

  @Override
  public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    binding.toolbar.setNavigationOnClickListener((View v) -> dismiss());
    binding.toolbar.inflateMenu(R.menu.menu_dialog_save);
    binding.toolbar.setTitle("Create new project");

    binding.toolbar.setOnMenuItemClickListener(item -> {
      Project project = new Project()
          .setTitle(binding.etTitle.getText().toString())
          .setTasks(0)
          .setMembers(1);

      project.saveInBackground(
          new SaveCallback() {
            @Override
            public void done(ParseException e) {
              if (e != null) {
                e.printStackTrace();
                return;
              }

              Membership membership = new Membership()
                  .setUser(ParseUser.getCurrentUser())
                  .setProject(project);

              membership.saveInBackground(new SaveCallback() {
                @Override
                public void done(ParseException e) {
                  if (e != null) {
                    e.printStackTrace();
                    return;
                  }

                  dismiss();
                }
              });
            }
          });
      return true;
    });
  }
}
