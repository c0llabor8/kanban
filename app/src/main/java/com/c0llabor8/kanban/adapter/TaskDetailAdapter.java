package com.c0llabor8.kanban.adapter;

import android.content.Context;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.c0llabor8.kanban.R;
import com.c0llabor8.kanban.model.Message;
import com.c0llabor8.kanban.util.DateTimeUtils;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;
import org.w3c.dom.Text;

public class TaskDetailAdapter extends RecyclerView.Adapter<TaskDetailAdapter.ViewHolder> {
  //list of comments
  List<Message> messages;
  Context context;

  public TaskDetailAdapter(Context context, List<Message> message) {
    this.messages = message;
    this.context = context;

  }
  @Override
  public void onBindViewHolder(@NonNull TaskDetailAdapter.ViewHolder holder, int position,
      @NonNull List<Object> payloads) {
    super.onBindViewHolder(holder, position, payloads);
  }

  @Override
  public int getItemCount() {
    return messages.size();
  }

  @NonNull
  @Override
  public TaskDetailAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    //get the context and create the inflater
    context = parent.getContext();
    LayoutInflater inflater = LayoutInflater.from(context);
    //create the view using the item_movie layout
    View commentView = inflater.inflate(R.layout.task_comments, parent, false);
    TaskDetailAdapter.ViewHolder view = new TaskDetailAdapter.ViewHolder(commentView);
    //return a new ViewHolder
    return view;

  }

  @Override
  public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
    Message message = messages.get(position);
    holder.tvComment.setText(message.getBody());
    holder.tvUsername.setText(message.getUser().getUsername());
    holder.timeStamp.setText(DateTimeUtils.getRelativeTimeAgo(message.getRelativeTime()));
  }

  public void clear() {
    messages.clear();
    notifyDataSetChanged();
  }


  public class ViewHolder extends RecyclerView.ViewHolder{

    TextView tvComment;
    TextView tvUsername;
    TextView timeStamp;


    public ViewHolder(@NonNull View itemView) {
      super(itemView);
      tvComment = itemView.findViewById(R.id.tvComment);
      tvUsername = itemView.findViewById(R.id.tvUsername);
      timeStamp = itemView.findViewById(R.id.tvTimeStamp);
    }
  }
}
