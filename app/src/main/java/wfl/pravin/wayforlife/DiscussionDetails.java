package wfl.pravin.wayforlife;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import wfl.pravin.wayforlife.adapter.CommentAdapter;
import wfl.pravin.wayforlife.models.Comment;

public class DiscussionDetails extends AppCompatActivity {
    public static final String EXTRA_TITLE_KEY = "title";
    public static final String EXTRA_USER_NAME_KEY = "username";
    public static final String EXTRA_DISCUSSION_KEY = "discussionKey";
    private static final String TAG = "Nitin-DiscDetailsActi";
    private static long timesToIgnore = 0;    //used in child event listener

    private String title, username, key;    //of selected discussion
    RecyclerView mCommentsRecycerView;

    DatabaseReference mCommentsReference;
    ChildEventListener mCommentsEventListener;
    CommentAdapter mCommentAdapter;
    private List<Comment> mCommentList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_discussion_details);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        UserDataUtils.refreshUserData(this);

        getExtraFromIntent();

        mCommentList = new ArrayList<>();
        mCommentAdapter = new CommentAdapter(mCommentList);
        mCommentsRecycerView = findViewById(R.id.comments_rv);
        TextView titleView = findViewById(R.id.discussion_title);
        TextView usernameView = findViewById(R.id.discussion_user);

        titleView.setText(title);
        usernameView.setText(username);

        mCommentsRecycerView.setLayoutManager(new LinearLayoutManager(this));
        mCommentsRecycerView.setItemAnimator(new DefaultItemAnimator());
        mCommentsRecycerView.setAdapter(mCommentAdapter);
        mCommentsRecycerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        mCommentsReference = firebaseDatabase.getReference().child("comments").child(key);

        //single value event listener to initially load all the comments.
        final Snackbar CommentLoadingSnackbar = Snackbar.make(mCommentsRecycerView, "Loading comments", Snackbar.LENGTH_INDEFINITE);
        CommentLoadingSnackbar.show();
        mCommentsReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                timesToIgnore = dataSnapshot.getChildrenCount();
                for (DataSnapshot commentSnapshot : dataSnapshot.getChildren()) {
                    Comment comment = commentSnapshot.getValue(Comment.class);
                    mCommentList.add(comment);
                }
                mCommentAdapter.notifyDataSetChanged();
                CommentLoadingSnackbar.dismiss();
                // add child listener to comments so as to dynamically add new incoming comments
                addChildEventListener();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    private void addChildEventListener() {

        /*
         * child event listener is added for this discussion
         * when this activity opens up for the first time, we have already loaded all the comments using
         * single value event listener and initialised timesToIgnore
         * Now we don't want to refresh recycler view unnecessarily, so untill timesToIgnore becomes zero,
         * we'll do nothing and simply return
         */
        mCommentsEventListener = mCommentsReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Log.d(TAG, "onChildAdded: timesToIgnore=" + timesToIgnore);
                if (timesToIgnore > 0) {
                    timesToIgnore--;
                    return;
                }
                Comment comment = dataSnapshot.getValue(Comment.class);
                mCommentList.add(comment);
                mCommentAdapter.notifyDataSetChanged();

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
            Comment comment = new Comment(commentText, ClientData.getUid(), ClientData.getName(), "" + System.currentTimeMillis());
            mCommentsReference.push().setValue(comment);

            hideSoftKeyboard();
        } else {
            Snackbar.make(findViewById(R.id.parent_layout), "Please enter a comment", Snackbar.LENGTH_SHORT).show();
        }
        newComment.setText(""); //clear the comment edit text
    }

    private void hideSoftKeyboard() {
        InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.hideSoftInputFromWindow(Objects.requireNonNull(this.getCurrentFocus()).getWindowToken(), 0);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mCommentsReference.removeEventListener(mCommentsEventListener);
    }
}
