package wfl.pravin.wayforlife;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;


public class ComplaintsActivity extends AppCompatActivity {
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
        mDatabaseReference=mDatabase.getReference().child("City");
        mDatabaseReference.keepSynced(true);

        citycomplaintList=new ArrayList<>();
        recyclerView=(RecyclerView)findViewById(R.id.recyclerViewId);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    protected void onStart() {
        super.onStart();

        mDatabaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Citycomplaint citycomplaint=dataSnapshot.getValue(Citycomplaint.class);
                citycomplaintList.add(citycomplaint);

                complaintRecyclerAdapter=new ComplaintRecyclerAdapter(ComplaintsActivity.this,citycomplaintList);
                recyclerView.setAdapter(complaintRecyclerAdapter);
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
}
