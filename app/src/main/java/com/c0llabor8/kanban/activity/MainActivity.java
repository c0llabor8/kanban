package com.c0llabor8.kanban.activity;

import android.os.Bundle;
import android.util.SparseArray;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SubMenu;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import com.c0llabor8.kanban.R;
import com.c0llabor8.kanban.databinding.ActivityMainBinding;
import com.c0llabor8.kanban.fragment.ProjectFragment;
import com.c0llabor8.kanban.fragment.TaskListFragment;
import com.c0llabor8.kanban.fragment.dialog.ManageCategoryFragment;
import com.c0llabor8.kanban.fragment.dialog.NewTaskDialog;
import com.c0llabor8.kanban.fragment.dialog.NewTaskDialog.TaskRefreshListener;
import com.c0llabor8.kanban.fragment.dialog.StringResultDialog;
import com.c0llabor8.kanban.fragment.sheet.BottomNavigationSheet;
import com.c0llabor8.kanban.fragment.sheet.ProjectBottomActionSheet;
import com.c0llabor8.kanban.fragment.sheet.ProjectSheetListener;
import com.c0llabor8.kanban.model.Membership;
import com.c0llabor8.kanban.model.Project;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.snackbar.Snackbar;
import com.parse.ParseException;

public class MainActivity extends AppCompatActivity implements ProjectSheetListener {

  ActivityMainBinding binding;

  BottomNavigationSheet navFragment;
  Project currentProject;
  ProjectBottomActionSheet navActionFragment;
  TaskRefreshListener taskRefreshListener;
  // SparseArray maps integers and Objects, more memory efficient than HashMap
  SparseArray<Project> projectMenuMap;

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
    binding.fab.setOnClickListener(view -> openTaskCreationDialog());

    navActionFragment = ProjectBottomActionSheet.newInstance();
    navFragment = BottomNavigationSheet.newInstance();

    // Not within a project scope in the initial screen, so keep it at null
    switchProjectScope(null);

    setSupportActionBar(binding.bar);
    loadProjects();
  }

  /**
   * switches fragment from personal task to that project fragment
   */
  public void switchProjectScope(Project project) {
    currentProject = project;

    Fragment fragment = (project == null) ? TaskListFragment.newInstance(null) :
        ProjectFragment.newInstance(project);

    if (fragment != null) {
      taskRefreshListener = (TaskRefreshListener) fragment;
      FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
      transaction.replace(binding.content.getId(), fragment);
      transaction.commit();
    }
  }

  //opens a new dialog for task creation
  private void openTaskCreationDialog() {
    NewTaskDialog.newInstance().show(getSupportFragmentManager(), currentProject);
  }

  @Override
  public boolean onOptionsItemSelected(@NonNull MenuItem item) {

    // Show our BottomNavigationSheet when the menu icon is clicked
    if (item.getItemId() == android.R.id.home) {
      navFragment.show(getSupportFragmentManager(), "");
      return true;
    }

    if (item.getItemId() == R.id.action_more) {
      navActionFragment.show(getSupportFragmentManager(), currentProject);
      return true;
    }

    return super.onOptionsItemSelected(item);
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    getMenuInflater().inflate(R.menu.menu_main, menu);
    return true;
  }

  @Override
  public void onAttachFragment(@NonNull Fragment fragment) {
    if (fragment instanceof BottomNavigationSheet) {
      BottomNavigationSheet navFragment = (BottomNavigationSheet) fragment;

      navFragment.setProjectNavigationListener(this);
    }

    if (fragment instanceof ProjectBottomActionSheet) {
      ProjectBottomActionSheet navFragment = (ProjectBottomActionSheet) fragment;

      navFragment.setProjectNavigationListener(this);
    }

    if (fragment instanceof NewTaskDialog) {
      NewTaskDialog taskDialog = (NewTaskDialog) fragment;
      taskDialog.setListener(taskRefreshListener);
    }
  }

  /*
   * Inflate the project menu once the BottomSheet's view is created based off our projectMenuMap
   * (SparseArray)
   * */
  @Override
  public void onPrepareProjectMenu(SubMenu subMenu) {
    for (int i = 0; i < projectMenuMap.size(); i++) {
      int key = projectMenuMap.keyAt(i);
      subMenu.add(Menu.NONE, key, key, projectMenuMap.get(key).getName());
    }
  }

  @Override
  public boolean onProjectItemSelected(MenuItem item) {
    // If the selected item is the user's personal tasks
    if (item.getItemId() == R.id.my_tasks) {
      setTitle(item.getTitle());
      //this is null because personal tasks are not within the scope of projects
      switchProjectScope(null);
      navFragment.dismiss();
      return true;
    }

    // if the selected item's id is in our HashSet<int(menuID), String(Project)>
    if (projectMenuMap.indexOfKey(item.getItemId()) > -1) {
      Project project = projectMenuMap.get(item.getItemId());

      setTitle(project.getName());
      switchProjectScope(project);
      navFragment.dismiss();
      return true;
    }

    // When new project is selected, dialog is launched to create new project
    if (item.getItemId() == R.id.new_project) {
      navFragment.dismiss();
      promptNewProject();
      return true;
    }

    if (item.getItemId() == R.id.action_category) {
      navActionFragment.dismiss();
      promptManageCategory();
      return true;
    }

    // Have the current user leave the current project
    if (item.getItemId() == R.id.action_leave_project) {
      navActionFragment.dismiss();
      promptLeaveProject();
      return true;
    }

    if (item.getItemId() == R.id.action_rename_project) {
      navActionFragment.dismiss();
      promptRenameProject();
      return true;
    }

    if (item.getItemId() == R.id.action_invite) {
      navActionFragment.dismiss();
      promptInviteMember();
      return true;
    }

    return false;
  }

  public void promptManageCategory() {
    ManageCategoryFragment fragment = ManageCategoryFragment.newInstance(currentProject);
    fragment.setTaskRefreshListener(taskRefreshListener);
    fragment.show(getSupportFragmentManager(), "");
  }

  public void promptNewProject() {
    StringResultDialog dialog = StringResultDialog.newInstance("Create new project",
        "Project name");

    dialog.onStringResult(
        (String result) -> {
          Project.create(result, e -> {
            if (e != null) {
              e.printStackTrace();
              return;
            }

            loadProjects();
            dialog.dismiss();
          });
        }
    );

    dialog.show(getSupportFragmentManager(), "");
  }

  public void promptInviteMember() {
    StringResultDialog dialog = StringResultDialog.newInstance("Invite member",
        "Email");

    dialog.onStringResult((String result) -> {
          Membership.invite(result, currentProject, (ParseException e) -> {
            if (e != null) {
              e.printStackTrace();

              Snackbar.make(binding.getRoot(), e.getMessage(), Snackbar.LENGTH_SHORT).show();
              return;
            }

            Snackbar.make(binding.getRoot(), String.format("Added %s to %s", result,
                currentProject.getName()), Snackbar.LENGTH_SHORT).show();
          });

          dialog.dismiss();
        }
    );

    dialog.show(getSupportFragmentManager(), "");
  }

  /**
   * Opens a dialog asking for a new project name
   */
  public void promptRenameProject() {
    StringResultDialog dialog = StringResultDialog.newInstance(String.format("Rename %s",
        currentProject.getName()), "New project name");

    dialog.onStringResult((String result) -> {
          currentProject.rename(result, (ParseException e) -> {
            if (e != null) {
              e.printStackTrace();
              return;
            }

            switchProjectScope(null);
            loadProjects();
          });

          dialog.dismiss();
        }
    );

    dialog.show(getSupportFragmentManager(), "");
  }

  /**
   * Opens a dialog prompting the user to leave the current project
   */
  public void promptLeaveProject() {
    new MaterialAlertDialogBuilder(this,
        R.style.ThemeOverlay_MaterialComponents_MaterialAlertDialog)
        .setTitle(String.format("Leave %s?", currentProject.getName()))
        .setNegativeButton("Cancel", (dialogInterface, i) -> dialogInterface.cancel())
        .setPositiveButton("Leave", (dialogInterface, i) -> {
          currentProject.leave(e -> {
            if (e != null) {
              e.printStackTrace();
            }

            switchProjectScope(null);
            loadProjects();
          });
        }).show();
  }

  /**
   * Query for all projects the current user is a member of and store them
   */
  public void loadProjects() {
    projectMenuMap = new SparseArray<>();

    Project.queryUserProjects((projects, e) -> {
      if (e != null) {
        e.printStackTrace();
        return;
      }

      for (int i = 0; i < projects.size(); i++) {
        projectMenuMap.put(Menu.FIRST + i, projects.get(i));
      }
    });
  }
}
