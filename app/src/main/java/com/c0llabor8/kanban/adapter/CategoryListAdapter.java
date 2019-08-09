package com.c0llabor8.kanban.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.c0llabor8.kanban.databinding.ListItemCategoryBinding;
import com.c0llabor8.kanban.model.TaskCategory;
import com.c0llabor8.kanban.util.DialogUtils;
import com.c0llabor8.kanban.util.DialogUtils.StringResultListener;
import com.parse.ParseException;
import com.parse.SaveCallback;
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
    TaskCategory category = taskCategories.get(position);
    holder.bind(category);

    holder.binding.getRoot().setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View view) {
        // TODO: Rename the selected category
        DialogUtils.textInputDialog(holder.itemView.getContext(), "Rename category",
            new StringResultListener() {
              @Override
              public void done(String string) {
                category.setTitle(string).saveInBackground(new SaveCallback() {
                  @Override
                  public void done(ParseException e) {
                    holder.bind(category);
                  }
                });
              }
            });
      }
    });
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
