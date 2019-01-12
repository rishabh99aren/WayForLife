package wfl.pravin.wayforlife;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Date;
import java.util.List;

public class ComplaintRecyclerAdapter extends RecyclerView.Adapter<ComplaintRecyclerAdapter.ViewHolder> {
    private Context context;
    private List<Citycomplaint> citycomplaintList;
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

        holder.complaint.setText(citycomplaint.getComplainttitle());
        holder.city.setText(citycomplaint.getCity());

        java.text.DateFormat dateFormat=java.text.DateFormat.getDateInstance();
        String formatteddate=dateFormat.format(new Date(Long.valueOf(citycomplaint.getTimestamp())));
        holder.Date.setText(formatteddate);
    }

    @Override
    public int getItemCount() {
        return citycomplaintList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView complaint;
        public TextView city;
      //  public static final String CITY_COMPLAINT="userid";
        public TextView Date;
       // public Button deletebutton;
        public Button mapbutton;

        public ViewHolder(@NonNull View view,Context ctx) {
            super(view);
            context = ctx;
            complaint = (TextView) view.findViewById(R.id.complainttitle);
            city = (TextView) view.findViewById(R.id.city);
            Date = (TextView) view.findViewById(R.id.date);
           // deletebutton = (Button) view.findViewById(R.id.deletebutton);
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
            // final int position = getAdapterPosition();
                  /*  final Citycomplaint citycomplaint = citycomplaintList.get(position);
                   // final String id=citycomplaint.getUserid();                  /*  Log.d("mayuri",id.trim());*/
                    /*if ("fQEJHFKJ".equals(citycomplaint.getUserid())) {
                        alertDialogBuilder = new AlertDialog.Builder(context);
                        inflater = LayoutInflater.from(context);
                        View view = inflater.inflate(R.layout.confirmation_dialog, null);
                        Button yesButton = (Button) view.findViewById(R.id.yesButton);
                        final Button noButton = (Button) view.findViewById(R.id.noButton);
                        alertDialogBuilder.setView(view);
                        dialog = alertDialogBuilder.create();
                        dialog.show();
                        noButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dialog.dismiss();
                            }
                        });
                        yesButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                int positon=getAdapterPosition();


                                DatabaseReference db = FirebaseDatabase.getInstance().getReference("City");
                               // db.child(citycomplaintList.get(position)).removeValue();
                                //delete();

                                Toast.makeText(context,"deledted",Toast.LENGTH_LONG).show();
                                dialog.dismiss();

                            }
                        });


                    }*/

        @Override
        public void onClick(View v) {
            int position=getAdapterPosition();
            Citycomplaint citycomplaint=citycomplaintList.get(position);
            Intent intents=new Intent(context,locationActivity.class);
            intents.putExtra("latitude",citycomplaint.getLatitude());
            intents.putExtra("longitude",citycomplaint.getLongitude());
            context.startActivity(intents);
        }
    }
}
