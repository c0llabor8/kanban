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
import com.c0llabor8.kanban.util.ProjectActivityInterface;
import com.parse.ParseException;
import com.parse.ParseUser;

public class NewProjectDialog extends AppCompatDialogFragment {

  private FragmentNewProjectBinding binding;
  private ProjectActivityInterface listener;

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

    // Dismiss the dialog when the navigation button is clicked
    binding.toolbar.setNavigationOnClickListener((View v) -> dismiss());

    binding.toolbar.inflateMenu(R.menu.menu_dialog_save);
    binding.toolbar.setTitle("Create new project");

    binding.toolbar.setOnMenuItemClickListener(item -> {
      createProject();
      return true;
    });
  }

  // Create the Project Object
  private void createProject() {
    Project project = new Project()
        .setTitle(binding.etTitle.getText().toString())
        .setTasks(0)
        .setMembers(1);

    project.saveInBackground((ParseException e) -> {
      if (e != null) {
        e.printStackTrace();
        return;
      }

      joinProject(project);
    });
  }

  // Set the class that will be listening to menu actions
  public void setListener(ProjectActivityInterface listener) {
    this.listener = listener;
  }

  // Link the user to the project
  private void joinProject(Project project) {
    Membership membership = new Membership()
        .setUser(ParseUser.getCurrentUser())
        .setProject(project);

    membership.saveInBackground((ParseException e) -> {
      if (e != null) {
        e.printStackTrace();
        return;
      }

      listener.loadProjects();
      dismiss();
    });
  }
}
