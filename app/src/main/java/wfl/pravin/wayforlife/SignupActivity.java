package wfl.pravin.wayforlife;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SignupActivity extends AppCompatActivity {

    private EditText email,password,confirmpassword;
    private Button email_sign_up_button;
    private FirebaseAuth firebaseAuth;
    private TextView login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        email = (EditText) findViewById(R.id.email);
        password = (EditText) findViewById(R.id.password);
        confirmpassword = (EditText) findViewById(R.id.confirmpassword);
        email_sign_up_button = (Button) findViewById(R.id.email_sign_up_button);
        firebaseAuth = FirebaseAuth.getInstance();
        login = (TextView) findViewById(R.id.login_into_main);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                startActivity(new Intent(SignupActivity.this, MainActivity.class));
            }
        });

        email_sign_up_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Animation shake = AnimationUtils.loadAnimation(SignupActivity.this, R.anim.shake);
                v.startAnimation(shake);
                if (validate()) {
                    String user_email = email.getText().toString().trim();
                    String user_password = confirmpassword.getText().toString().trim();
                    firebaseAuth.createUserWithEmailAndPassword(user_email, user_password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                sendEmailVerifiation();
                            } else {
                                Toast.makeText(SignupActivity.this, "Registration Failed", Toast.LENGTH_SHORT).show();

                            }
                        }
                    });
                }
            }
        });
    }

    private Boolean validate(){
        Boolean result= false;

        String emailId= email.getText().toString();
        String pass= password.getText().toString();
        String confirmpass= confirmpassword.getText().toString();

        if(emailId.isEmpty() || pass.isEmpty() || confirmpass.isEmpty()){
            Toast.makeText(this, "Please Enter All Details", Toast.LENGTH_SHORT).show();
        }
        else if(!pass.equals(confirmpass)){
            Toast.makeText(this, "Oopss...Password didn't match", Toast.LENGTH_SHORT).show();
        }
        else
            return true;

        return result;
    }

    private void sendEmailVerifiation(){
        final FirebaseUser firebaseUser= firebaseAuth.getCurrentUser();
        if(firebaseUser!=null){
            firebaseUser.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()){
                        Toast.makeText(SignupActivity.this, "Successfully Registered, Verification link has been sent!! ", Toast.LENGTH_SHORT).show();
                        firebaseAuth.signOut();
                        finish();
                        startActivity(new Intent(SignupActivity.this,MainActivity.class));
                    }
                    else {
                        Toast.makeText(SignupActivity.this, "Verification link hasn't been sent!!", Toast.LENGTH_SHORT).show();
                    }

                }
            });
        }
    }
}
