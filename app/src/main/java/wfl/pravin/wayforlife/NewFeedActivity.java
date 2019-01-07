package wfl.pravin.wayforlife;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.FirebaseDatabase;

import wfl.pravin.wayforlife.models.Discussion;

public class NewFeedActivity extends AppCompatActivity {
    //dummy user data
    //TODO: replace after auth module is complete
    private static final String USER_NAME = "Nitin";
    private static final String USER_ID = "aca_2424_vfaffa_2222";
    private static final String USER_CITY = "Rupnagar";
    private static final String DISCUSSIONS = "discussions";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_feed);


    }

    public void openComplaintsActivity(View view) {
        //TODO: make complaints option in action bar and open it on it's click
        Intent i = new Intent(NewFeedActivity.this, ComplaintsActivity.class);
        startActivity(i);
    }

    public void addNewDiscussion(View view) {
        // this method executes when user clicks on fab to add a new discussion

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Add new discussion ?");
        builder.setMessage("Enter the title of discussion");

        final EditText titleEditText = new EditText(this);
        builder.setView(titleEditText);

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                addToFirebase(titleEditText);
            }
        });

        builder.setNegativeButton("Cancel", null);

        builder.create().show();
    }

    private void addToFirebase(EditText titleEditText) {
        if (titleEditText.getText().toString().trim().length() == 0) {
            Snackbar.make(findViewById(R.id.discussion_rv), "Title can't be empty", Snackbar.LENGTH_SHORT).show();
        }
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        firebaseDatabase.getReference()
                .child(DISCUSSIONS).child(USER_CITY).push()
                .setValue(new Discussion(
                        titleEditText.getText().toString(),
                        USER_ID,
                        USER_NAME,
                        null
                ))
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Snackbar.make(findViewById(R.id.discussion_rv), "Discussion added", Snackbar.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Snackbar.make(findViewById(R.id.discussion_rv), "Error adding discussion", Snackbar.LENGTH_SHORT).show();
                    }
                });
    }
}
