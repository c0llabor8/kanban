package com.c0llabor8.kanban.model;

import android.content.Context;
import androidx.core.content.ContextCompat;
import com.c0llabor8.kanban.R;
import com.c0llabor8.kanban.model.query.TaskQuery;
import com.c0llabor8.kanban.util.TaskProvider;
import com.c0llabor8.kanban.util.WordUtils;
import com.parse.DeleteCallback;
import com.parse.ParseClassName;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.SaveCallback;
import java.util.Comparator;

@ParseClassName("TaskCategory")
public class TaskCategory extends ParseObject {

  public static String KEY_PROJECT = "project";
  public static String KEY_TITLE = "title";
  public static String KEY_ORDER = "order";

  public static void create(String title, Project project, SaveCallback callback) {
    if (!TaskProvider.getInstance().getCategoryMap(project).keySet().contains(title)) {
      new TaskCategory().setTitle(title).setProject(project)
          .setOrder(TaskProvider.getInstance().getCategories(project).size())
          .saveInBackground(callback);
    } else {
      callback.done(new ParseException(0, "Category already exists"));
    }
  }

  public Project getProject() {
    return (Project) getParseObject(KEY_PROJECT);
  }

  public TaskCategory setProject(Project project) {
    put(KEY_PROJECT, project);
    return this;
  }

  public void delete(DeleteCallback callback) {
    TaskCategory category = this;
    TaskQuery query = new TaskQuery().whereCategoryEquals(category);

    query.findInBackground((tasks, e) -> {
      for (Task task : tasks) {
        task.removeCategory();
        task.saveInBackground();
      }

      category.deleteInBackground(callback);
    });
  }

  public int getOrder() {
    return getInt(KEY_ORDER);
  }

  public TaskCategory setOrder(int order) {
    put(KEY_ORDER, order);
    return this;
  }

  public String getTitle() {
    return WordUtils.capitalize(getString(KEY_TITLE));
  }

  public TaskCategory setTitle(String title) {
    put(KEY_TITLE, title);
    return this;
  }

  public int getColor(Context context) {
    int red = ContextCompat.getColor(context, R.color.color_secondary);
    int yellow = ContextCompat.getColor(context, R.color.color_tertiary);
    int categories = TaskProvider.getInstance().getCategories(getProject()).size();

    float value = (float) (this.getOrder()) / (float) (categories - 1);

    if (categories == 1) {
      value = 1f;
    }

    return androidx.core.graphics.ColorUtils.blendARGB(yellow, red, value);
  }

  @Override
  public String toString() {
    return getTitle();
  }

  @Override
  public boolean equals(Object obj) {
    if (obj instanceof TaskCategory) {
      return ((TaskCategory) obj).getObjectId().equals(this.getObjectId());
    }

    return false;
  }

  public static class SortComparator implements Comparator<TaskCategory> {

    @Override
    public int compare(TaskCategory category, TaskCategory o) {
      int result = Integer.compare(category.getOrder(), o.getOrder());

      if (result == 0) {
        return category.getTitle().compareTo(o.getTitle());
      }

      return result;
    }
  }
}