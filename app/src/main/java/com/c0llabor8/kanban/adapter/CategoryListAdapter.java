package com.c0llabor8.kanban.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.c0llabor8.kanban.databinding.ListItemCategoryBinding;
import com.c0llabor8.kanban.model.TaskCategory;
import java.util.List;

public class CategoryListAdapter extends RecyclerView.Adapter<CategoryListAdapter.ViewHolder> {

  private List<TaskCategory> taskCategories;

  public CategoryListAdapter(List<TaskCategory> taskCategories) {
    this.taskCategories = taskCategories;
  }

  @NonNull
  @Override
  public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    LayoutInflater inflater = LayoutInflater.from(parent.getContext());

    ListItemCategoryBinding binding = ListItemCategoryBinding.inflate(inflater, parent, false);
    return new ViewHolder(binding);
  }

  @Override
  public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
    holder.bind(taskCategories.get(position));
  }

  @Override
  public int getItemCount() {
    return taskCategories.size();
  }

  public class ViewHolder extends RecyclerView.ViewHolder {

    ListItemCategoryBinding binding;

    ViewHolder(@NonNull ListItemCategoryBinding binding) {
      super(binding.getRoot());
      this.binding = binding;
    }

    public void bind(TaskCategory category) {
      binding.categoryLabel.setText(category.getTitle());
    }
  }
}
