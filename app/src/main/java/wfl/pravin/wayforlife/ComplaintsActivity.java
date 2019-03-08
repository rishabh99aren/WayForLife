package wfl.pravin.wayforlife;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import wfl.pravin.wayforlife.models.Complaint;


public class ComplaintsActivity extends AppCompatActivity implements ComplaintRecyclerAdapter.OnitemClickListener {

    public static final String EXTRA_COMPLAINT_TITLE = "comaplintTitle";
    public static final String EXTRA_COMPLAINT_DESC = "complaintDesc";
    public static final String EXTRA_IMAGE_URL = "imageURL";
    public static final String EXTRA_COMPLAINT_LAT = "complaintLat";
    public static final String EXTRA_COMPLAINT_LNG = "complaintLng";
    public static final String EXTRA_COMPLAINT_BY_USER = "complaintByUser";



    private DatabaseReference mDatabaseReference;
    private RecyclerView recyclerView;
    private ComplaintRecyclerAdapter complaintRecyclerAdapter;
    private List<Complaint> complaintList;
    private FirebaseDatabase mDatabase;
    private static String USER_CITY = "Rupnagar";
    private static String USER_STATE = "Punjab";

    AutoCompleteTextView cityAutocompleteTextView,stateAutoCompleteTextView;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complaints);
		Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
		
        mDatabase=FirebaseDatabase.getInstance();
        mAuth=FirebaseAuth.getInstance();
        mDatabaseReference = mDatabase.getReference().child("complaints");
        mDatabaseReference.keepSynced(true);

        Log.d("Nitin", mAuth.getCurrentUser().getUid());

        complaintList = new ArrayList<>();

        recyclerView=(RecyclerView)findViewById(R.id.recyclerViewId);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        complaintRecyclerAdapter = new ComplaintRecyclerAdapter(ComplaintsActivity.this, complaintList);
        recyclerView.setAdapter(complaintRecyclerAdapter);

        loadstates();
        loadCities();
        loadcomplaints();


        complaintRecyclerAdapter.setOnItemClickListener(ComplaintsActivity.this);


    }

    private void loadstates() {
        stateAutoCompleteTextView=findViewById(R.id.autoCompleteTextView);;
        FirebaseDatabase.getInstance().getReference().child("states").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<String> states=(List<String>)dataSnapshot.getValue();
                if(states!=null){
                    ArrayAdapter<String> adapter=new ArrayAdapter<>(ComplaintsActivity.this,android.R.layout.simple_list_item_activated_1,states);
                    stateAutoCompleteTextView.setAdapter(adapter);

                    //  loadCitesforregistration();

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });stateAutoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                USER_STATE = (String) parent.getItemAtPosition(position);
                loadCities();
            }
        });

    }



    private void loadcomplaints() {
        final Snackbar loadingcomplaintSnackbar=Snackbar.make(recyclerView,"loading complaints",Snackbar.LENGTH_SHORT);
        loadingcomplaintSnackbar.show();
        FirebaseDatabase.getInstance().getReference().child("complaints").child(USER_CITY).
        addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                complaintList.clear();
                for(DataSnapshot complaintsnapshot : dataSnapshot.getChildren()){
                    Complaint complaint = complaintsnapshot.getValue(Complaint.class);
                    complaintList.add(complaint);
                    Collections.reverse(complaintList);
                }
                complaintRecyclerAdapter.notifyDataSetChanged();
                loadingcomplaintSnackbar.dismiss();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                loadingcomplaintSnackbar.dismiss();
                Snackbar.make(findViewById(R.id.recyclerViewId),"Error in loading",Snackbar.LENGTH_SHORT).show();

            }
        });
    }

    private void loadCities() {
        cityAutocompleteTextView=findViewById(R.id.autocomplete);
        FirebaseDatabase.getInstance().getReference().child("cities").child(USER_STATE)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        List<String> cities= (List<String>) dataSnapshot.getValue();
                      //  Log.d("cities", String.valueOf(cities));
                        if(cities!=null){
                            ArrayAdapter<String> adapter=new ArrayAdapter<>(ComplaintsActivity.this,android.R.layout.simple_dropdown_item_1line,cities);
                            cityAutocompleteTextView.setAdapter(adapter);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
        cityAutocompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                USER_CITY = (String) parent.getItemAtPosition(position);
                loadcomplaints();


            }
        });

    }

    @Override
    public void onItemClick(int position) {
        Intent detailIntent=new Intent(this,DetailcomplaintActivity.class);
        Complaint clickeditem = complaintList.get(position);

        detailIntent.putExtra(EXTRA_IMAGE_URL, clickeditem.getImageUrl());
        detailIntent.putExtra(EXTRA_COMPLAINT_TITLE, clickeditem.getTitle());
        detailIntent.putExtra(EXTRA_COMPLAINT_DESC, clickeditem.getDesc());
        detailIntent.putExtra(EXTRA_COMPLAINT_LAT, clickeditem.getLat());
        detailIntent.putExtra(EXTRA_COMPLAINT_LNG, clickeditem.getLng());
        detailIntent.putExtra(EXTRA_COMPLAINT_BY_USER, clickeditem.getUserName());

        startActivity(detailIntent);

    }
}
