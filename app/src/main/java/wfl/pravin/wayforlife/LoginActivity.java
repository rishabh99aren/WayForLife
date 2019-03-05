package wfl.pravin.wayforlife;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
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

import java.util.ArrayList;
import java.util.List;

public class LoginActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private EditText emailField, passwordField, inputnewpassword;
    private ProgressDialog mProgressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth=FirebaseAuth.getInstance();
        Button loginButton = (Button) findViewById(R.id.loginbutton);
        TextView createaccount = (TextView) findViewById(R.id.logincreateAccount);
        emailField=(EditText)findViewById(R.id.loginEmail);
        passwordField=(EditText)findViewById(R.id.loginPassword);
        TextView forgotpassword = (TextView) findViewById(R.id.forgotpassword);
        List<LoginActivity> loginActivityList = new ArrayList<>();
        mProgressDialog=new ProgressDialog(this);

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

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!TextUtils.isEmpty(emailField.getText().toString()) && !TextUtils.isEmpty(passwordField.getText().toString())){
                    mProgressDialog.setMessage("Logging in");
                    mProgressDialog.show();

                    String email=emailField.getText().toString();
                    String pwd=passwordField.getText().toString();

                    login(email,pwd);
                }else{
                    mProgressDialog.dismiss();
                    Toast.makeText(LoginActivity.this,"Enter all the entries",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void resetpassword() {
       android.app.AlertDialog.Builder builder=new android.app.AlertDialog.Builder(LoginActivity.this);
        inputnewpassword=new EditText(this);
        inputnewpassword.setHint("Enter your reistered MailID");
        builder.setView(inputnewpassword);

        builder.setNeutralButton("ResetPassword", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String email=inputnewpassword.getText().toString().trim();
                if(email.equals("")){
                    Toast.makeText(LoginActivity.this,"Enter enter your registered email ID",Toast.LENGTH_SHORT).show();
                }else{
                    mAuth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                Toast.makeText(LoginActivity.this,"Password reset email sent",Toast.LENGTH_SHORT).show();
                                mProgressDialog.dismiss();
                            }else {
                                Toast.makeText(LoginActivity.this,"Error in sending password reset email",Toast.LENGTH_SHORT).show();
                                //this errror will occur when the email is not registered in database
                                mProgressDialog.dismiss();
                            }

                        }
                    });
                }
            }
        });builder.show();

    }

    private void login(String email, String pwd) {
        mAuth.signInWithEmailAndPassword(email,pwd).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){

                    mProgressDialog.dismiss();
                    emailVerificationdone();

                }else{
                    mProgressDialog.dismiss();
                    Toast.makeText(LoginActivity.this,"Login Failed",Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private void emailVerificationdone() {
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        boolean emailVerified = false;
        if (firebaseUser != null) {
            emailVerified = firebaseUser.isEmailVerified();
        }
        if (emailVerified) {
            finish();
            startActivity(new Intent(LoginActivity.this,MainActivity.class));

        }else{
            Toast.makeText(this,"Verify your mail",Toast.LENGTH_SHORT).show();
            mAuth.signOut();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUI(currentUser);
    }

    private void updateUI(FirebaseUser currentUser) {
        if (currentUser != null) {
            Intent i = new Intent(LoginActivity.this,MainActivity.class);
            startActivity(i);
            this.finish();
        }
    }


}
