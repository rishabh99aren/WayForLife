package wfl.pravin.wayforlife;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class RetriveeventsActivity extends AppCompatActivity {

    private DatabaseReference databaserefer;
    private RecyclerView recyclerView;
    private EventsRecyclerAdapter eventsRecyclerAdapter;
    private List<Events> eventsList;
    private FirebaseDatabase mdatabase;
    private FirebaseUser muser;
    private FirebaseAuth mauth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retriveevents);
       // mauth=FirebaseAuth.getInstance();
        //muser=mauth.getCurrentUser();

        mdatabase=FirebaseDatabase.getInstance();
        databaserefer=mdatabase.getReference().child("Events");
        databaserefer.keepSynced(true);

        eventsList=new ArrayList<>();



        recyclerView=(RecyclerView)findViewById(R.id.recyclerviewevents);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        eventsRecyclerAdapter=new EventsRecyclerAdapter(RetriveeventsActivity.this,eventsList);
        recyclerView.setAdapter(eventsRecyclerAdapter);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menuforevents,menu);
        return super.onCreateOptionsMenu(menu);
    }

   @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.addevents:

                startActivity(new Intent(RetriveeventsActivity.this,RegistereventsActivity.class));
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d("mayuri","pawar");
       FirebaseDatabase.getInstance().getReference().child("Events").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                Log.d("work","pleasejhdgbjGGBJKJHIKqujnikFAUHIUGHBIUJANSKJJBHUIHABSUIHNKJBNSUIR");
                eventsList.clear();

                for(DataSnapshot eventssnapshot:dataSnapshot.getChildren()){
                    Log.d("workkarab","plssssssssssssssssssssssssssssssssssssssssssssssss");
                    Events events=eventssnapshot.getValue(Events.class);
                    eventsList.add(events);
                    Log.d("chalanaan","challlllllllllllllllllllllllllllllllllllllllllllll");
                    Collections.reverse(eventsList);
                }
                eventsRecyclerAdapter.notifyDataSetChanged();


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


       // eventsList.clear();

       /* databaserefer.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                Events events=dataSnapshot.getValue(Events.class);
                eventsList.add(events);
                Collections.reverse(eventsList);
                eventsRecyclerAdapter=new EventsRecyclerAdapter(RetriveeventsActivity.this,eventsList);
                recyclerView.setAdapter(eventsRecyclerAdapter);
                eventsRecyclerAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });*/

    }
}
