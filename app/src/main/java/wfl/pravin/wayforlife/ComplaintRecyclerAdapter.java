package wfl.pravin.wayforlife;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

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
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {

        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.retrivecmptlistrow,parent,false);

        return new ViewHolder(view,context);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int i) {
        Citycomplaint citycomplaint=citycomplaintList.get(i);

        holder.complaint.setText(citycomplaint.getComplaint());
        holder.city.setText(citycomplaint.getCity());
      //  Picasso.get().load(imageUrl).into(holder.postedImage);
        //Picasso.get().load(imageUrl).into(holder.postedImage);
       // Picasso.get().load(citycomplaint.getImage()).fit().centerCrop().into(holder.postedImage);




    }

    @Override
    public int getItemCount() {
        return citycomplaintList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView complaint;
        public TextView city;
        public static final String EXTRA_URL="imageUrl";
        public static final String EXTRA_CITY="city";


      //  public ImageView postedImage;

        public ViewHolder(@NonNull View view,Context ctx) {
            super(view);
            context=ctx;
            complaint=(TextView)view.findViewById(R.id.complaint);
            city=(TextView)view.findViewById(R.id.city);
           // postedImage=(ImageView)view.findViewById(R.id.postedimage);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position=getAdapterPosition();
                     Citycomplaint citycomplaint=citycomplaintList.get(position);

                    Intent intent=new Intent(context,DetailcomplaintActivity.class);
                    intent.putExtra(EXTRA_URL,citycomplaint.getImage());
                    intent.putExtra(EXTRA_CITY,citycomplaint.getCity());
                    context.startActivity(intent);
                }
            });


        }
    }
}
