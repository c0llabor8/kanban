package com.c0llabor8.kanban.fragment.dialog;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;
import com.c0llabor8.kanban.R;
import com.c0llabor8.kanban.adapter.CategoryListAdapter;
import com.c0llabor8.kanban.databinding.FragmentCategoryEditorBinding;
import com.c0llabor8.kanban.fragment.dialog.NewTaskDialog.TaskRefreshListener;
import com.c0llabor8.kanban.model.Project;
import com.c0llabor8.kanban.model.TaskCategory;
import com.c0llabor8.kanban.util.TaskProvider;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.parse.DeleteCallback;
import com.parse.ParseException;
import java.util.Collections;
import java.util.List;

public class ManageCategoryFragment extends DialogFragment {

  private Project project;
  private TaskRefreshListener listener;
  private CategoryListAdapter listAdapter;

  private FragmentCategoryEditorBinding binding;

  public static ManageCategoryFragment newInstance(Project project) {

    Bundle args = new Bundle();

    args.putParcelable("project", project);
    ManageCategoryFragment fragment = new ManageCategoryFragment();
    fragment.setArguments(args);
    return fragment;
  }

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    this.project = getArguments().getParcelable("project");
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

    listAdapter = new CategoryListAdapter(TaskProvider.getInstance().getCategories(project));
    binding = FragmentCategoryEditorBinding.inflate(inflater, container, false);
    return binding.getRoot();
  }

  @Override
  public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);

    binding.categoryList.setLayoutManager(new LinearLayoutManager(getContext()));
    binding.categoryList.setAdapter(listAdapter);
    binding.categoryList.addItemDecoration(
        new DividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL)
    );

    ItemTouchHelper touchHelper = new ItemTouchHelper(new CategoryTouchHelper());
    touchHelper.attachToRecyclerView(binding.categoryList);

    binding.addCategory.setOnClickListener(v -> newCategory());
  }

  /**
   * Launch a dialog in order to create a new category
   */
  public void newCategory() {
    MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(getContext())
        .setTitle("Enter category label");

    final EditText editText = new EditText(getContext());
    builder.setView(editText);

    builder.setPositiveButton("Create", (dialogInterface, i) -> {
      TaskCategory.create(editText.getText().toString(), project, e -> {

        TaskProvider.getInstance().updateCategories(project, (objects, e1) -> {
          listAdapter.notifyDataSetChanged();
          dialogInterface.dismiss();
        });
      });
    });

    builder.show();
  }

  public void setTaskRefreshListener(TaskRefreshListener listener) {
    this.listener = listener;
  }

  @Override
  public void onDetach() {
    super.onDetach();
    // Notify the listening class we've updated the tasks
    listener.onTaskRefresh();
  }

  // Helper class to handle drag and drop actions on list items
  public class CategoryTouchHelper extends ItemTouchHelper.Callback {


    @Override
    public int getMovementFlags(@NonNull RecyclerView recyclerView,
        @NonNull ViewHolder viewHolder) {

      return makeFlag(
          ItemTouchHelper.ACTION_STATE_DRAG,
          ItemTouchHelper.DOWN | ItemTouchHelper.UP
      ) | makeFlag(ItemTouchHelper.ACTION_STATE_SWIPE, ItemTouchHelper.END);
    }

    @Override
    public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull ViewHolder viewHolder,
        @NonNull ViewHolder target) {
      List<TaskCategory> categories = TaskProvider.getInstance().getCategories(project);

      Collections.swap(categories, viewHolder.getAdapterPosition(), target.getAdapterPosition());

      listAdapter.notifyItemMoved(viewHolder.getAdapterPosition(), target.getAdapterPosition());

      // Set the order for every task category
      for (int i = 0; i < categories.size(); i++) {
        TaskCategory category = categories.get(i);
        category.setOrder(i);
        category.saveInBackground();
      }

      return true;
    }

    @Override
    public void onSwiped(@NonNull ViewHolder viewHolder, int direction) {
      int position = viewHolder.getAdapterPosition();
      List<TaskCategory> categories = TaskProvider.getInstance().getCategories(project);

      categories.get(position).delete(e -> listener.onTaskRefresh());
      categories.remove(position);
      listAdapter.notifyItemRemoved(position);
    }
  }
}
