package com.c0llabor8.kanban.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.c0llabor8.kanban.R;
import com.c0llabor8.kanban.databinding.ListItemMembersBinding;
import com.parse.ParseUser;
import java.util.List;

public class MemberProfileAdapter extends RecyclerView.Adapter<MemberProfileAdapter.ViewHolder> {

  private static final String TAG = "MemberProfileAdapter";
  private List<ParseUser> members;
  private Context context;

  public MemberProfileAdapter(Context context, List<ParseUser> members) {
    this.members = members;
    this.context = context;
  }

  /**
   * Inflates the row layout from xml when needed
   *
   * @return adapter
   */
  @NonNull
  @Override
  public MemberProfileAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent,
      int viewType) {

    LayoutInflater inflater = LayoutInflater.from(parent.getContext());
    ListItemMembersBinding binding = ListItemMembersBinding.inflate(inflater, parent, false);

    return new ViewHolder(binding);
  }

  /**
   * Binds the data to the view
   */
  @Override
  public void onBindViewHolder(@NonNull MemberProfileAdapter.ViewHolder holder, int position) {
    Log.d(TAG, "onCreateViewHolder: called");

    // getting the product from the specified position
    holder.bind(members.get(position));
  }

  /**
   * Total number of row
   */
  @Override
  public int getItemCount() {
    return members.size();
  }

  /**
   * Stores and recycles views as they are scrolled off screen
   */
  class ViewHolder extends RecyclerView.ViewHolder {

    private ListItemMembersBinding binding;

    public ViewHolder(@NonNull ListItemMembersBinding binding) {
      super(binding.getRoot());
      this.binding = binding;
    }

    public void bind(ParseUser user) {
      // For Bitmaps:
      Glide.with(context)
          .load(R.raw.profile)
          .apply(new RequestOptions().circleCrop())
          .into(binding.ivProfile);

      binding.tvName.setText(user.getUsername());
      binding.ivProfile.setOnClickListener(new OnClickListener() {
        @Override
        public void onClick(View view) {
          Log.d(TAG, "onClick: clicked on an image");
        }
      });
    }
  }
}
