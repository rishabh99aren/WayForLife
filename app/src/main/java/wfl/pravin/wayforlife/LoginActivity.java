package wfl.pravin.wayforlife;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.storage.StorageTask;

import java.util.ArrayList;
import java.util.List;

public class LoginActivity extends AppCompatActivity {
    private AlertDialog.Builder   dialogBuilder;
    private AlertDialog dialog;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseUser mUser;
    private Button loginButton,resetpassword;
    private TextView createaccount,info;
    private List<LoginActivity> loginActivityList;
    private EditText emailField,passwordField,emailreset;
    private ProgressDialog mProgressDialog;
    private int counter=5;
    private TextView forgotpassword;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth=FirebaseAuth.getInstance();
        loginButton=(Button)findViewById(R.id.loginbutton);
        createaccount=(TextView) findViewById(R.id.logincreateAccount);
        emailField=(EditText)findViewById(R.id.loginEmail);
        passwordField=(EditText)findViewById(R.id.loginPassword);
        forgotpassword=(TextView)findViewById(R.id.forgotpassword);
        loginActivityList=new ArrayList<>();
        mProgressDialog=new ProgressDialog(this);
        info=(TextView)findViewById(R.id.info);

        forgotpassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetpassword();
            }
        });


        createaccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this,CreateAccountActivity.class));
                finish();
            }
        });
        mAuthListener=new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                mUser=firebaseAuth.getCurrentUser();

                if(mUser!=null){
                    Toast.makeText(LoginActivity.this,"Signed in",Toast.LENGTH_LONG).show();
                    startActivity(new Intent(LoginActivity.this,MainActivity.class));
                    finish();

                }else {
                    Toast.makeText(LoginActivity.this,"Not Signed In",Toast.LENGTH_LONG).show();
                }

            }
        };
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!TextUtils.isEmpty(emailField.getText().toString()) && !TextUtils.isEmpty(passwordField.getText().toString())){
                    mProgressDialog.setMessage("Logging in");
                    mProgressDialog.show();

                    String email=emailField.getText().toString();
                    String pwd=passwordField.getText().toString();

                    login(email,pwd);
                }
            }
        });
    }

    private void resetpassword() {
        dialogBuilder=new AlertDialog.Builder(this);
        View view=getLayoutInflater().inflate(R.layout.resetpassword,null);
        emailreset=(EditText)view.findViewById(R.id.resetemailrequired);
        resetpassword=(Button)view.findViewById(R.id.resetpasswordbutton);
        dialogBuilder.setView(view);
        dialog=dialogBuilder.create();
        dialog.show();

        resetpassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email=emailreset.getText().toString().trim();
                if(email.equals("")){
                    Toast.makeText(LoginActivity.this,"Enter enter your registered email ID",Toast.LENGTH_SHORT).show();
                }else{
                    mAuth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                Toast.makeText(LoginActivity.this,"Password reset email sent",Toast.LENGTH_SHORT).show();
                                dialog.dismiss();
                            }else {
                                Toast.makeText(LoginActivity.this,"Error in sending password reset email",Toast.LENGTH_SHORT).show();
                                //this errror will occur when the email is not registered in database
                                dialog.dismiss();
                            }

                        }
                    });
                }
            }
        });

    }

    private void login(String email, String pwd) {
        mAuth.signInWithEmailAndPassword(email,pwd).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){

                    mProgressDialog.dismiss();
                    emailVerificationdone();
                   // Toast.makeText(LoginActivity.this,"Signed in",Toast.LENGTH_SHORT).show();
                    //startActivity(new Intent(LoginActivity.this,MainActivity.class));
                    //finish();
                }else{
                    mProgressDialog.dismiss();
                    Toast.makeText(LoginActivity.this,"Login Failed",Toast.LENGTH_SHORT).show();
                    counter--;
                    info.setText("No of attempts remaining: " + counter);

                    if(counter==0){
                        loginButton.setEnabled(false);
                    }

                }
            }
        });

    }

    private void emailVerificationdone() {
        FirebaseUser firebaseUser=mAuth.getInstance().getCurrentUser();
        Boolean emailverified=firebaseUser.isEmailVerified();
        if(emailverified){
            startActivity(new Intent(this,MainActivity.class));
            finish();
        }else{
            Toast.makeText(this,"Verify your mail",Toast.LENGTH_SHORT).show();
            mAuth.signOut();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(mAuthListener!=null){
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }
}
