package wfl.pravin.wayforlife;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class ComplaintsActivity extends AppCompatActivity implements ComplaintRecyclerAdapter.OnitemClickListener {

    public static final String EXTRA_COMPLAINT="comaplintname";
    public static final String EXTRA_CITY="cityname";
    private DatabaseReference mDatabaseReference;
    private RecyclerView recyclerView;
    private ComplaintRecyclerAdapter complaintRecyclerAdapter;
    private List<Citycomplaint> citycomplaintList;
    private FirebaseDatabase mDatabase;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complaints);

        mDatabase=FirebaseDatabase.getInstance();
        mDatabaseReference=mDatabase.getReference().child("Complaints").child("Mumbai");
        mDatabaseReference.keepSynced(true);

        citycomplaintList=new ArrayList<>();

        recyclerView=(RecyclerView)findViewById(R.id.recyclerViewId);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        complaintRecyclerAdapter=new ComplaintRecyclerAdapter(ComplaintsActivity.this,citycomplaintList);
        recyclerView.setAdapter(complaintRecyclerAdapter);


        complaintRecyclerAdapter.setOnItemClickListener(ComplaintsActivity.this);

    }

//to retrive the data
    @Override
    protected void onStart() {
        super.onStart();
        citycomplaintList.clear();

        mDatabaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Citycomplaint citycomplaint=dataSnapshot.getValue(Citycomplaint.class);
                citycomplaintList.add(citycomplaint);
                Collections.reverse(citycomplaintList);


                complaintRecyclerAdapter.notifyDataSetChanged();
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
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mDatabaseReference.removeValue();

    }
//when clicked on the view of recyclerview
    @Override
    public void onItemClick(int position) {
        Intent detailIntent=new Intent(this,DetailcomplaintActivity.class);
        Citycomplaint clickeditem=citycomplaintList.get(position);

        detailIntent.putExtra(EXTRA_COMPLAINT,clickeditem.getComplaint());
        detailIntent.putExtra(EXTRA_CITY,clickeditem.getCity());

        startActivity(detailIntent);

    }
}
