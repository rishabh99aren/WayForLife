package wfl.pravin.wayforlife;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageReference;

import java.util.List;

public class CreateAccountActivity extends AppCompatActivity {

    private EditText username,emailact,passwordact,city,state,confirmpwd;
    private Button createAccount;
    private DatabaseReference mDatabasereference;
    private FirebaseDatabase mDatabase;
    private FirebaseAuth mAuth;
    private ProgressDialog mProgressDialog;
    private TextView havelogin;
    private static String USER_STATE="Maharashtra";
    AutoCompleteTextView autoCompleteTextView1,autoCompleteTextView2;
    private static final String[] COUNTRIES=new String[]{"Maharashtra","Delhi","Assam","Chandigarh"};



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);

        Log.d("cities", "be calm  strig"/*String.valueOf(cities)*/);


        mDatabase=FirebaseDatabase.getInstance();
        mDatabasereference=mDatabase.getReference().child("Musers");
        mAuth=FirebaseAuth.getInstance();

        username=(EditText)findViewById(R.id.firstName);
        emailact=(EditText)findViewById(R.id.emailAct);
        passwordact=(EditText)findViewById(R.id.passwordAct);
        confirmpwd=(EditText)findViewById(R.id.passwordConfirmAct);
        city=(EditText)findViewById(R.id.cityAct);
        state=(EditText)findViewById(R.id.stateAct);
        havelogin=(TextView)findViewById(R.id.Logindirected);
        createAccount=(Button)findViewById(R.id.createAccount2);
        mProgressDialog=new ProgressDialog(this);
     //   loadstates();

       /* autoCompleteTextView1 =(AutoCompleteTextView)findViewById(R.id.autocomplete1);
        ArrayAdapter<String> adapter=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,COUNTRIES);
        autoCompleteTextView1.setAdapter(adapter);
        autoCompleteTextView1.setThreshold(1);
        autoCompleteTextView1.setAdapter(adapter);*/

        //loadcities();
       // Log.d("cities", "be AegjJEandkj stupid"/*String.valueOf(cities)*/);


        havelogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CreateAccountActivity.this,LoginActivity.class));
                finish();
            }
        });

        createAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createNewAccount();

            }
        });



    }

   /* private void loadcities() {
        Log.d("cities", "be calm and stray strig"/*String.valueOf(cities));*/

        /*autoCompleteTextView2=(AutoCompleteTextView)findViewById(R.id.autoComplete2);

        FirebaseDatabase.getInstance().getReference().child("cities").child(USER_STATE)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                      //  List<String> cities= (List<String>) dataSnapshot.getValue();
                        Log.d("cities", "be calm and stray strig ehqFIUHGIYGIGUIHUY"/*String.valueOf(cities));
                       /* if(cities!=null){
                            ArrayAdapter<String> adapter1=new ArrayAdapter<>(CreateAccountActivity.this,android.R.layout.simple_dropdown_item_1line,cities);
                            autoCompleteTextView2.setAdapter(adapter1);
                        }*/
                    //}

                    /*@Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                       // Log.d("cities", "be calm and stray strig"/*String.valueOf(cities));*/

                //    }*/
              //  });*/

    //}

  /* private void loadstates() {
        autoCompleteTextView1=(AutoCompleteTextView)findViewById(R.id.autocomplete1);
        autoCompleteTextView1.setText(USER_STATE);


        FirebaseDatabase.getInstance().getReference().child("states")
                .addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<String> states=(List<String>) dataSnapshot.getValue();
               // Log.d("cities", String.valueOf(states));
                if(states!=null){
                    ArrayAdapter<String> adapter=new ArrayAdapter<>(CreateAccountActivity.this,android.R.layout.simple_dropdown_item_1line,states);
                    autoCompleteTextView1.setAdapter(adapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }*/

    private void createNewAccount() {
        final String uname=username.getText().toString().trim();
        String email=emailact.getText().toString().trim();
        String password=passwordact.getText().toString().trim();
        String confirmpassword=confirmpwd.getText().toString().trim();
        final String ccity=city.getText().toString().trim();
        final String cstate=state.getText().toString().trim();

        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            emailact.setError("Please enter a valid email");
            emailact.requestFocus();
            return;
        }
        if(password.length()<6 ){
            passwordact.setError("Minimum length required is 6");
            passwordact.requestFocus();
            return;
        }



        if(!TextUtils.isEmpty(uname) && !TextUtils.isEmpty(email) && !TextUtils.isEmpty(password)
                && !TextUtils.isEmpty(ccity) && !TextUtils.isEmpty(cstate)){

            if(password.equals(confirmpassword) ){

                mProgressDialog.setMessage("Creating Account.....");
                mProgressDialog.show();
                mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {


                            sendEmailVeriication();


                        }else{
                            Toast.makeText(CreateAccountActivity.this,"Registration failed",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }else{
                Toast.makeText(CreateAccountActivity.this,"Oopss..Password didnt match",Toast.LENGTH_SHORT).show();
            }

        }else{
            Toast.makeText(CreateAccountActivity.this,"Enter all the details",Toast.LENGTH_SHORT).show();
        }

    }

    private void sendEmailVeriication() {
        FirebaseUser firebaseUser=mAuth.getCurrentUser();
        if(firebaseUser!=null){
            firebaseUser.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if(task.isSuccessful()){
                        Toast.makeText(CreateAccountActivity.this, "Verification email has been sent.", Toast.LENGTH_SHORT).show();
                            String userid = mAuth.getCurrentUser().getUid();
                            DatabaseReference currentuserdb = mDatabasereference.child(userid);
                            currentuserdb.child("Username").setValue(username.getText().toString().trim());
                            currentuserdb.child("Userid").setValue(userid);
                            currentuserdb.child("City").setValue(city.getText().toString().trim());
                            currentuserdb.child("State").setValue(state.getText().toString().trim());
                            mProgressDialog.dismiss();
                        mAuth.signOut();
                        startActivity(new Intent(CreateAccountActivity.this,LoginActivity.class));
                        finish();
                    }else{
                        Toast.makeText(CreateAccountActivity.this,"Verificaton link not sent.",Toast.LENGTH_SHORT).show();
                    }

                }
            });
        }
    }
}
