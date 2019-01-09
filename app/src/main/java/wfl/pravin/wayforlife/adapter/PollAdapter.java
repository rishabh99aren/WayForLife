package wfl.pravin.wayforlife.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import wfl.pravin.wayforlife.R;
import wfl.pravin.wayforlife.models.Poll;

public class PollAdapter extends RecyclerView.Adapter<PollAdapter.PollViewHolder> {
    private List<Poll> mPollList;

    public PollAdapter(List<Poll> mPollList) {
        this.mPollList = mPollList;
    }

    @NonNull
    @Override
    public PollViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_poll, parent, false);
        return new PollViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull PollViewHolder holder, int position) {
        Poll poll = mPollList.get(position);
        List<String> optionList = poll.getOptions();

        holder.title.setText(poll.getTitle());
        holder.option1.setText(optionList.get(0));
        holder.option2.setText(optionList.get(1));
        if (optionList.size() > 2) {
            holder.option3.setVisibility(View.VISIBLE);
            holder.option3.setText(optionList.get(2));
        }
        if (optionList.size() > 3) {
            holder.option4.setVisibility(View.VISIBLE);
            holder.option4.setText(optionList.get(3));
        }
    }

    @Override
    public int getItemCount() {
        return mPollList.size();
    }

    class PollViewHolder extends RecyclerView.ViewHolder {
        TextView title, option1, option2, option3, option4;

        PollViewHolder(View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.title);
            option1 = itemView.findViewById(R.id.poll_option1);
            option2 = itemView.findViewById(R.id.poll_option2);
            option3 = itemView.findViewById(R.id.poll_option3);
            option4 = itemView.findViewById(R.id.poll_option4);
        }
    }
}
