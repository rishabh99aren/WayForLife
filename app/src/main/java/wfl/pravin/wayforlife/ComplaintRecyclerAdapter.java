package wfl.pravin.wayforlife;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

public class ComplaintRecyclerAdapter extends RecyclerView.Adapter<ComplaintRecyclerAdapter.ViewHolder> {
    private Context context;
    private List<Upload> uploadList;
    private OnitemClickListener mListener;
    private AlertDialog.Builder alertDialogBuilder;
    private AlertDialog dialog;
    private LayoutInflater inflater;


    public interface OnitemClickListener{
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnitemClickListener listener){
        mListener=listener;
    }

    public ComplaintRecyclerAdapter(Context context, List<Upload> uploadList) {
        this.context = context;
        this.uploadList = uploadList;

    }


    @NonNull

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {

        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.retrivecmptlistrow,parent,false);

        return new ViewHolder(view,context);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int i) {
        Upload upload=uploadList.get(i);

        holder.complaint.setText(upload.getDesc());
        holder.city.setText(upload.getCity());
        holder.Date.setText(upload.getTimestamp());
    }

    @Override
    public int getItemCount() {
        return uploadList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView complaint;
        public TextView city;
        public TextView Date;
        public Button mapbutton;

        public ViewHolder(@NonNull View view,Context ctx) {
            super(view);
            context = ctx;
            complaint = (TextView) view.findViewById(R.id.complainttitle);
            city = (TextView) view.findViewById(R.id.city);
            Date = (TextView) view.findViewById(R.id.date);
            mapbutton=(Button)view.findViewById(R.id.mapbutton);

            mapbutton.setOnClickListener(this);

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
            Upload upload=uploadList.get(position);
            Intent intents=new Intent(context,locationActivity.class);
            intents.putExtra("latitude", upload.getLat());
            intents.putExtra("longitude", upload.getLng());
            context.startActivity(intents);
        }
    }
}
