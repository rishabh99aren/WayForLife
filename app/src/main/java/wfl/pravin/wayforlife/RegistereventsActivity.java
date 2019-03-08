package wfl.pravin.wayforlife;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class RegistereventsActivity extends AppCompatActivity {
    private EditText title,description,date,address,time;
    private AutoCompleteTextView stateauto,cityauto;
    private Button submitevent;
    private DatabaseReference database;
    private ProgressDialog progress;
    private FirebaseAuth auth;
    private FirebaseUser user;
    private static  String USER_CITY="Mumbai";
    private static  String USER_STATE="Maharashtra";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registerevents);

        title=(EditText)findViewById(R.id.eventtitle);
        description=(EditText)findViewById(R.id.eventdescription);
        date=(EditText)findViewById(R.id.eventdate);
        address=(EditText)findViewById(R.id.eventaddress);
        submitevent=(Button)findViewById(R.id.postevent);
        time=(EditText)findViewById(R.id.eventtime);

        auth=FirebaseAuth.getInstance();
        user=auth.getCurrentUser();
        database= FirebaseDatabase.getInstance().getReference().child("Events");

        loadstates();
        loadCities();



        submitevent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startposting();
            }
        });

    }
    private void loadstates() {
        stateauto=findViewById(R.id.Stateauto);;
        FirebaseDatabase.getInstance().getReference().child("states").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<String> states=(List<String>)dataSnapshot.getValue();
                if(states!=null){
                    ArrayAdapter<String> adapter=new ArrayAdapter<>(RegistereventsActivity.this,android.R.layout.simple_list_item_activated_1,states);
                    stateauto.setAdapter(adapter);

                    //  loadCitesforregistration();

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });stateauto.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                USER_STATE = (String) parent.getItemAtPosition(position);
                loadCities();
            }
        });

    }

    private void loadCities() {
        cityauto = findViewById(R.id.cityauto);
        //String statetext = autoCompleteTextView2.getText().toString().trim();

        FirebaseDatabase.getInstance().getReference().child("cities").child(USER_STATE)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        //Log.d("cictiesmayuripawar","aenfhbQKUH HGOU AUVNYIOUNIEBIOUOEIVUY7W3O5YIJ3VU988YN9VUW7");
                        List<String> cities = (List<String>) dataSnapshot.getValue();
                        if (cities != null) {
                            ArrayAdapter<String> adapter = new ArrayAdapter<>(RegistereventsActivity.this, android.R.layout.simple_dropdown_item_1line, cities);
                            cityauto.setAdapter(adapter);
                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
        cityauto.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                USER_CITY = (String) parent.getItemAtPosition(position);
            }
        });

    }


    private void startposting() {
        String eventtitle=title.getText().toString().trim();
        String eventdescription=description.getText().toString().trim();
        String eventdate=date.getText().toString().trim();
        String eventadd=address.getText().toString().trim();
        String eventtime=time.getText().toString().trim();

        if(!TextUtils.isEmpty(eventtitle) && !TextUtils.isEmpty(eventdescription) && !TextUtils.isEmpty(eventdate)  &&
                !TextUtils.isEmpty(eventadd) && !TextUtils.isEmpty(eventtime)){
            String id=database.push().getKey();
            DatabaseReference mdatabase=database.child(id);
            mdatabase.child("state").setValue(USER_STATE);
            mdatabase.child("city").setValue(USER_CITY);
            mdatabase.child("time").setValue(eventtime);
            mdatabase.child("title").setValue(eventtitle);
            mdatabase.child("description").setValue(eventdescription);
            mdatabase.child("date").setValue(eventdate);
            mdatabase.child("address").setValue(eventadd);
            startActivity(new Intent(RegistereventsActivity.this,MainActivity.class));





        }else{
            Toast.makeText(RegistereventsActivity.this,"Enter all the entries",Toast.LENGTH_SHORT).show();
        }
    }
}
