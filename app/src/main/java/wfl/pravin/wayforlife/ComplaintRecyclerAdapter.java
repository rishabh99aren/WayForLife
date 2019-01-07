package wfl.pravin.wayforlife;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;



import java.util.Date;
import java.util.List;

public class ComplaintRecyclerAdapter extends RecyclerView.Adapter<ComplaintRecyclerAdapter.ViewHolder> {
    private Context context;
    private List<Citycomplaint> citycomplaintList;

    public ComplaintRecyclerAdapter(Context context, List<Citycomplaint> citycomplaintList) {
        this.context = context;
        this.citycomplaintList = citycomplaintList;
    }

    @NonNull

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.retrivecmptlistrow,parent,false);

        return new ViewHolder(view,context);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int i) {
        Citycomplaint citycomplaint=citycomplaintList.get(i);
        holder.complaint.setText(citycomplaint.getComplaint());
        holder.city.setText(citycomplaint.getCity());

     /*   java.text.DateFormat dateFormat=java.text.DateFormat.getDateInstance();
        String formatteddate=dateFormat.format(new Date(Long.valueOf(citycomplaint.getTimestamp())).getTime());
        holder.timestamp.setText(formatteddate);*/


    }

    @Override
    public int getItemCount() {
        return citycomplaintList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView complaint;
        public TextView city;
      //  public TextView timestamp;

        public ViewHolder(@NonNull View view,Context ctx) {
            super(view);
            context=ctx;
            complaint=(TextView)view.findViewById(R.id.complaint);
            city=(TextView)view.findViewById(R.id.city);
           // timestamp=(TextView)view.findViewById(R.id.date);

        }
    }
}
