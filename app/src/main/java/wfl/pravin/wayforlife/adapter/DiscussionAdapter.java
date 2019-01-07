package wfl.pravin.wayforlife.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
    }

    @Override
    public int getItemCount() {
        return discussionList.size();
    }

    class DiscussionViewHolder extends RecyclerView.ViewHolder {
        TextView title, userName;

        DiscussionViewHolder(View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.discussion_title);
            userName = itemView.findViewById(R.id.discussion_username);
        }
    }
}
