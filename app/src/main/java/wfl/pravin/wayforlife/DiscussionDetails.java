package wfl.pravin.wayforlife;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import wfl.pravin.wayforlife.models.Comment;

public class DiscussionDetails extends AppCompatActivity {
    public static final String EXTRA_TITLE_KEY = "title";
    public static final String EXTRA_USER_NAME_KEY = "username";
    public static final String EXTRA_DISCUSSION_KEY = "discussionKey";
    private static final String TAG = "Nitin-DiscDetailsActi";

    private String title, username, key;

    DatabaseReference mCommentsReference;
    ChildEventListener mCommentsEventListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_discussion_details);

        getExtraFromIntent();

        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        mCommentsReference = firebaseDatabase.getReference().child("comments").child(key);

        mCommentsEventListener = mCommentsReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
//                Log.d(TAG, "onChildAdded: ");
                Comment comment = dataSnapshot.getValue(Comment.class);
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Log.d(TAG, "onChildChanged: ");
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

    private void getExtraFromIntent() {
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            title = extras.getString(EXTRA_TITLE_KEY);
            username = extras.getString(EXTRA_USER_NAME_KEY);
            key = extras.getString(EXTRA_DISCUSSION_KEY);

            Log.d(TAG, "getExtraFromIntent: " + title + " , " + username + " , " + key);
        }
    }

    public void addNewComment(View view) {
        EditText newComment = findViewById(R.id.new_comment);
        String commentText = newComment.getText().toString();
        if (commentText.trim().length() > 0) {
            Comment comment = new Comment(commentText, "bfbu_2746_bFBUJB", "Nitin Verma", "" + System.currentTimeMillis());
            mCommentsReference.push().setValue(comment);
        } else {
            Snackbar.make(findViewById(R.id.parent_layout), "Please enter a comment", Snackbar.LENGTH_SHORT).show();
        }
        newComment.setText(""); //clear the comment edit text
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mCommentsReference.removeEventListener(mCommentsEventListener);
    }
}
