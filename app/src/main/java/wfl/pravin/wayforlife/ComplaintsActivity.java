package wfl.pravin.wayforlife;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class ComplaintsActivity extends AppCompatActivity implements ComplaintRecyclerAdapter.OnitemClickListener {

    public static final String EXTRA_COMPLAINT="comaplintname";
    public static final String EXTRA_CITY="cityname";
    public static final String EXTRA_URL="image";
    private DatabaseReference mDatabaseReference;
    private RecyclerView recyclerView;
    private ComplaintRecyclerAdapter complaintRecyclerAdapter;
    private List<Citycomplaint> citycomplaintList;
    private FirebaseDatabase mDatabase;
    private static  String USER_CITY="Mumbai";
    private static  String USER_STATE="Maharashtra";
    AutoCompleteTextView cityAutocompleteTextView;
    private Button signout;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complaints);

        mDatabase=FirebaseDatabase.getInstance();
        mAuth=FirebaseAuth.getInstance();



        citycomplaintList=new ArrayList<>();

        recyclerView=(RecyclerView)findViewById(R.id.recyclerViewId);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        complaintRecyclerAdapter=new ComplaintRecyclerAdapter(ComplaintsActivity.this,citycomplaintList);
        recyclerView.setAdapter(complaintRecyclerAdapter);
        signout=(Button)findViewById(R.id.signout);

        loadcomplaints();
        loadCities();


        complaintRecyclerAdapter.setOnItemClickListener(ComplaintsActivity.this);
        signout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.signOut();
                startActivity(new Intent(ComplaintsActivity.this,LoginActivity.class));
                finish();
            }
        });

    }

    private void loadcomplaints() {
        final Snackbar loadingcomplaintSnackbar=Snackbar.make(recyclerView,"loading complaints",Snackbar.LENGTH_SHORT);
        loadingcomplaintSnackbar.show();
        mDatabaseReference=FirebaseDatabase.getInstance().getReference().child("Complaints").child(USER_CITY);
        mDatabaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                citycomplaintList.clear();
                for(DataSnapshot complaintsnapshot : dataSnapshot.getChildren()){
                    Citycomplaint citycomplaint=complaintsnapshot.getValue(Citycomplaint.class);
                    citycomplaintList.add(citycomplaint);
                    Collections.reverse(citycomplaintList);


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
        //cityAutocompleteTextView.setText(USER_CITY);

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

    //to retrive the data
  /*  @Override
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
    }*/

 /*   @Override
    protected void onDestroy() {
        super.onDestroy();
        mDatabaseReference.removeValue();

    }*/
//when clicked on the view of recyclerview
    @Override
    public void onItemClick(int position) {
        Intent detailIntent=new Intent(this,DetailcomplaintActivity.class);
        Citycomplaint clickeditem=citycomplaintList.get(position);

        detailIntent.putExtra(EXTRA_URL,clickeditem.getImage());
        detailIntent.putExtra(EXTRA_COMPLAINT,clickeditem.getComplaint());
        detailIntent.putExtra(EXTRA_CITY,clickeditem.getCity());

        startActivity(detailIntent);

    }
}
