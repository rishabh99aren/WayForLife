package wfl.pravin.wayforlife;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.gms.vision.text.Text;

import java.util.List;

public class EventsRecyclerAdapter extends RecyclerView.Adapter<EventsRecyclerAdapter.ViewHolder> {

    private Context context;
    private List<Events> eventsList;

    public EventsRecyclerAdapter(Context context, List<Events> eventsList) {
        this.context = context;
        this.eventsList = eventsList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.retriveeventslistrow,parent,false);
        return new ViewHolder(view,context);
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Events events=eventsList.get(position);
        holder.title.setText(events.getTitle());
        holder.date.setText(events.getDate());
        holder.city.setText(events.getCity());

    }

    @Override
    public int getItemCount() {
        return eventsList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView title,city,date;

        public ViewHolder(@NonNull View view,Context ctx) {
            super(view);

            context=ctx;
            title=(TextView)view.findViewById(R.id.atitle);
            city=(TextView)view.findViewById(R.id.acity);
            date=(TextView)view.findViewById(R.id.adate);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position=getAdapterPosition();
                    Events events=eventsList.get(position);
                    Intent i=new Intent(context,Detaileventsactivity.class);
                    i.putExtra("Title",events.getTitle());
                    i.putExtra("Description",events.getDescription());
                    i.putExtra("Date",events.getDate());
                    i.putExtra("Time",events.getTime());
                    i.putExtra("Address",events.getAddress());
                    i.putExtra("State",events.getState());
                    i.putExtra("City",events.getCity());
                    context.startActivity(i);

                }
            });

        }


    }
}
