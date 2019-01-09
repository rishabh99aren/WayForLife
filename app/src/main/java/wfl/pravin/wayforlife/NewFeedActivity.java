package wfl.pravin.wayforlife;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import wfl.pravin.wayforlife.adapter.DiscussionAdapter;
import wfl.pravin.wayforlife.models.Discussion;

public class NewFeedActivity extends AppCompatActivity {
    //dummy user data
    //TODO: replace after auth module is complete
    private static final String USER_NAME = "Nitin";
    private static final String USER_ID = "aca_2424_vfaffa_2222";
    private static final String USER_STATE = "Punjab";
    private static String USER_CITY = "Rupnagar";
    private static final String DISCUSSIONS = "discussions";

    List<Discussion> discussionList;
    RecyclerView mRecyclerView;
    DiscussionAdapter mDiscussionAdapter;
    AutoCompleteTextView cityAutoCompleteTextView;

    private DatabaseReference mDiscussionReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_feed);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        discussionList = new ArrayList<>();
        mRecyclerView = findViewById(R.id.discussion_rv);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        DiscussionClickListener discussionClickListener = new DiscussionClickListener() {
            @Override
            public void discussionClicked(String title, String userName, String key) {
                Intent intent = new Intent(NewFeedActivity.this, DiscussionDetails.class);
                intent.putExtra(DiscussionDetails.EXTRA_TITLE_KEY, title);
                intent.putExtra(DiscussionDetails.EXTRA_USER_NAME_KEY, userName);
                intent.putExtra(DiscussionDetails.EXTRA_DISCUSSION_KEY, key);
                startActivity(intent);
            }
        };

        mDiscussionAdapter = new DiscussionAdapter(discussionList, discussionClickListener);
        mRecyclerView.setAdapter(mDiscussionAdapter);

        loadDiscussions();

        loadCities();
    }

    private void loadDiscussions() {
        final Snackbar loadingDiscussionSnackbar = Snackbar.make(mRecyclerView, "Loading discussions", Snackbar.LENGTH_INDEFINITE);
        loadingDiscussionSnackbar.show();
        mDiscussionReference = FirebaseDatabase.getInstance().getReference(DISCUSSIONS).child(USER_CITY);
        mDiscussionReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                discussionList.clear();
                for (DataSnapshot discussionSnapshot : dataSnapshot.getChildren()) {
                    Discussion d = discussionSnapshot.getValue(Discussion.class);
                    discussionList.add(d);
                }
                mDiscussionAdapter.notifyDataSetChanged();
                loadingDiscussionSnackbar.dismiss();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                loadingDiscussionSnackbar.dismiss();
                showSnackbar("Error in loading");
            }
        });
    }

    private void loadCities() {
        cityAutoCompleteTextView = findViewById(R.id.city);
        cityAutoCompleteTextView.setText(USER_CITY); //by default value

        FirebaseDatabase.getInstance().getReference().child("cities").child(USER_STATE)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        List<String> cities = (List<String>) dataSnapshot.getValue();
                        if (cities != null) {
                            ArrayAdapter<String> adapter = new ArrayAdapter<>(NewFeedActivity.this, android.R.layout.simple_dropdown_item_1line, cities);
                            cityAutoCompleteTextView.setAdapter(adapter);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

        cityAutoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                USER_CITY = (String) parent.getItemAtPosition(position);
                loadDiscussions();
            }
        });
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
            showSnackbar("Title can't be empty");
            return;
        }
        final Snackbar addingDiscussionSnackbar = Snackbar.make(findViewById(R.id.discussion_rv), "Adding discussion", Snackbar.LENGTH_INDEFINITE);
        addingDiscussionSnackbar.show();

        String key = mDiscussionReference.push().getKey();
        final Discussion newDiscussion = new Discussion(titleEditText.getText().toString(), USER_ID, USER_NAME, key);
        if (key != null) {
            mDiscussionReference.child(key).setValue(newDiscussion).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    addingDiscussionSnackbar.dismiss();
                    discussionList.add(newDiscussion);
                    mDiscussionAdapter.notifyDataSetChanged();
                    showSnackbar("Discussion added");

                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    addingDiscussionSnackbar.dismiss();
                    showSnackbar("Error adding discussion");
                }
            });
        }
    }

    private void showSnackbar(String msg) {
        Snackbar.make(findViewById(R.id.discussion_rv), msg, Snackbar.LENGTH_SHORT).show();
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
}
