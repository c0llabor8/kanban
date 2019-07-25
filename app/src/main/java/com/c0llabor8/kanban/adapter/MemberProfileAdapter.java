package com.c0llabor8.kanban.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.c0llabor8.kanban.databinding.FragmentSummaryBinding;
import com.c0llabor8.kanban.model.Membership;
import java.util.List;

public class MemberProfileAdapter extends RecyclerView.Adapter<MemberProfileAdapter.ViewHolder> {

  private List<Membership> members;
  private Context context;

  public MemberProfileAdapter(Context context, List<Membership> members) {
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

    FragmentSummaryBinding binding =
        FragmentSummaryBinding.inflate(inflater, parent, false);
    return new ViewHolder(binding);

  }

  /**
   * Binds the data to the view and textview in each row
   */
  @Override
  public void onBindViewHolder(@NonNull MemberProfileAdapter.ViewHolder holder, int position) {
    Membership membership = members.get(position);
    //holder.ivProfile.setImageResource(members.get(position).imageId);

  }

  @Override
  public int getItemCount() {
    return members.size();
  }

  /**
   * Stores and recycles views as they are scrolled off screen
   */
  class ViewHolder extends RecyclerView.ViewHolder {

    private FragmentSummaryBinding binding;
    private ImageView ivProfile;
//    private TextView tvMemberName;

    public ViewHolder(@NonNull FragmentSummaryBinding binding) {
      super(binding.getRoot());
      this.binding = binding;
    }

    public void bind(int position) {
      //binding.ivProfile.
      // For Bitmaps:
//      Glide.with()
//          .load()
//          .asBitmap()
//          .centerCrop()
//          .into(ivProfile);
//    }
    }
  }
}