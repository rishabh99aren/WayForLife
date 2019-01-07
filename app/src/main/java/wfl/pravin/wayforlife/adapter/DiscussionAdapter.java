package wfl.pravin.wayforlife.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

import wfl.pravin.wayforlife.R;
import wfl.pravin.wayforlife.models.Discussion;

public class DiscussionAdapter extends RecyclerView.Adapter<DiscussionAdapter.DiscussionViewHolder> {
    private static final String TAG = "Nitin-DiscussionAdapter";

    private List<Discussion> discussionList;

    public DiscussionAdapter(List<Discussion> discussions) {
        this.discussionList = discussions;
    }

    @NonNull
    @Override
    public DiscussionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_discussion, parent, false);
        return new DiscussionViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final DiscussionViewHolder holder, int position) {
        Discussion discussion = discussionList.get(position);
        holder.title.setText(discussion.getTitle());
        holder.userName.setText(String.format("-By %s", discussion.getUserName()));
        holder.sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: send comment=" + holder.newComment.getText().toString());
            }
        });
        holder.commentsToggle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String value = holder.commentsToggle.getText().toString();
                if (value.equals("Show comments")) {
                    //TODO : show the comments
                    holder.commentsToggle.setText("Hide comments");
                } else {
                    //TODO : hide the comments
                    holder.commentsToggle.setText("Show comments");
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return discussionList.size();
    }

    class DiscussionViewHolder extends RecyclerView.ViewHolder {
        TextView title, userName, commentsToggle;
        ListView comments;
        EditText newComment;
        ImageButton sendButton;

        DiscussionViewHolder(View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.discussion_title);
            userName = itemView.findViewById(R.id.discussion_username);
            commentsToggle = itemView.findViewById(R.id.comments_visibility_toggle);
            comments = itemView.findViewById(R.id.discussion_comments_lv);
            newComment = itemView.findViewById(R.id.discussion_new_comment);
            sendButton = itemView.findViewById(R.id.discussion_send_btn);
        }
    }
}
