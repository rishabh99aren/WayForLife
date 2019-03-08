package wfl.pravin.wayforlife;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import wfl.pravin.wayforlife.models.Complaint;

public class ComplaintRecyclerAdapter extends RecyclerView.Adapter<ComplaintRecyclerAdapter.ViewHolder> {
    private Context context;
    private List<Complaint> complaintList;
    private OnitemClickListener mListener;

    public interface OnitemClickListener{
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnitemClickListener listener){
        mListener=listener;
    }

    public ComplaintRecyclerAdapter(Context context, List<Complaint> complaintList) {
        this.context = context;
        this.complaintList = complaintList;

    }


    @NonNull

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_complaint, parent, false);

        return new ViewHolder(view,context);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int i) {
        Complaint complaint = complaintList.get(i);

        holder.title.setText(complaint.getTitle());
        holder.desc.setText(complaint.getDesc());
    }

    @Override
    public int getItemCount() {
        return complaintList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView title;
        public TextView desc;

        public ViewHolder(@NonNull View view,Context ctx) {
            super(view);
            context = ctx;
            title = (TextView) view.findViewById(R.id.complaint_title);
            desc = (TextView) view.findViewById(R.id.complaint_desc);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mListener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            mListener.onItemClick(position);
                        }
                    }
                }
            });
        }

        @Override
        public void onClick(View v) {
            int position=getAdapterPosition();
            Complaint complaint = complaintList.get(position);
//            Intent intents=new Intent(context,locationActivity.class);
//            intents.putExtra("latitude", complaint.getLat());
//            intents.putExtra("longitude", complaint.getLng());
//            context.startActivity(intents);
        }
    }
}
