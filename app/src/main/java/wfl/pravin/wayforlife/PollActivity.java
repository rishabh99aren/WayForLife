package wfl.pravin.wayforlife;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

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

import wfl.pravin.wayforlife.adapter.PollAdapter;
import wfl.pravin.wayforlife.models.Poll;

public class PollActivity extends AppCompatActivity {
    //dummy user data
    //TODO: replace after auth module is complete
    private static final String USER_NAME = "Nitin";
    private static final String USER_ID = "aca_2424_vfaffa_2222";
    private static final String USER_STATE = "Punjab";
    private static final String POLLS = "polls";
    private static final String VOTES = "votes";
    private static String USER_CITY = "Rupnagar";

    List<Poll> pollList;
    RecyclerView mRecyclerView;
    PollAdapter mPollAdapter;
    AutoCompleteTextView cityAutoCompleteTextView;

    DatabaseReference mPollsReference;
    private int optionToCheck = 2;    //used in validation when adding a new dialog

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_poll);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        pollList = new ArrayList<>();
        mRecyclerView = findViewById(R.id.polls_rv);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        OptionClickListener optionClickListener = new OptionClickListener() {
            @Override
            public void optionClicked(final VoteAddedListener voteAddedListener, final View optionView, String key) {
                final int option = getOptionFromOptionId(optionView.getId());

                final Snackbar voteAddingSnackbar = Snackbar.make(mRecyclerView, "Please wait ...", Snackbar.LENGTH_INDEFINITE);
                voteAddingSnackbar.show();
                FirebaseDatabase.getInstance().getReference().child(VOTES).child(key).child(USER_ID).setValue(option)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                voteAddingSnackbar.dismiss();
                                showSnackbar("Vote added");
                                voteAddedListener.voteAddedToFirebase(optionView, option);
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                voteAddingSnackbar.dismiss();
                                showSnackbar("Try again later");
                            }
                        });
            }
        };
        mPollAdapter = new PollAdapter(pollList, optionClickListener);
        mRecyclerView.setAdapter(mPollAdapter);

        loadCities();
        loadPolls();

    }

    private void loadPolls() {
        final Snackbar loadingPollsSnackbar = Snackbar.make(mRecyclerView, "Loading polls", Snackbar.LENGTH_INDEFINITE);
        loadingPollsSnackbar.show();

        FirebaseDatabase.getInstance().getReference().child(VOTES).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull final DataSnapshot dataSnapshot) {
                //now we have vote data, now get polls
                mPollsReference = FirebaseDatabase.getInstance().getReference().child(POLLS).child(USER_CITY);
                mPollsReference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot1) {
                        pollList.clear();
                        for (DataSnapshot discussionSnapshot : dataSnapshot1.getChildren()) {
                            Poll p = discussionSnapshot.getValue(Poll.class);
                            if (p != null) {
                                pollList.add(p);

                                String key = p.getKey();
                                Object obj = dataSnapshot.child(key).child(USER_ID).getValue();
                                long option = -1;
                                if (obj != null) {
                                    option = (long) obj;
                                }
                                if (option > -1) {
                                    // user has voted for this poll
                                    p.setVoted(true);
                                    p.setVotedOption(option);
                                }
                            }
                        }
                        mPollAdapter.notifyDataSetChanged();
                        loadingPollsSnackbar.dismiss();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        loadingPollsSnackbar.dismiss();
                        showSnackbar("Error in loading");
                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                loadingPollsSnackbar.dismiss();
                showSnackbar("Error in loading");
            }
        });
    }

    public void addNewPoll(View view) {
        // this method executes when user clicks on fab to add a new discussion

        //init new dialog
        final Dialog dialog = new Dialog(this);

        View dialogLayout = LayoutInflater.from(this).inflate(R.layout.dialog_new_poll, null);
        final EditText titleView = dialogLayout.findViewById(R.id.poll_title);
        final EditText option1View = dialogLayout.findViewById(R.id.poll_option1);
        final EditText option2View = dialogLayout.findViewById(R.id.poll_option2);
        final EditText option3View = dialogLayout.findViewById(R.id.poll_option3);
        final EditText option4View = dialogLayout.findViewById(R.id.poll_option4);
        final ImageButton addOptionButton = dialogLayout.findViewById(R.id.add_option_button);
        Button addPollButton = dialogLayout.findViewById(R.id.add_poll_button);

        //dialog.requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
        Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().setTitle("Add a new Poll ?");
        dialog.setContentView(dialogLayout);

        addOptionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (option3View.getVisibility() == View.GONE) {
                    option3View.setVisibility(View.VISIBLE);
                    optionToCheck++;
                } else {
                    option4View.setVisibility(View.VISIBLE);
                    addOptionButton.setVisibility(View.GONE);   //can't add new option now
                    optionToCheck++;
                }
            }
        });

        addPollButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = titleView.getText().toString().trim();
                String option1 = option1View.getText().toString().trim();
                String option2 = option2View.getText().toString().trim();
                String option3 = option3View.getText().toString().trim();
                String option4 = option4View.getText().toString().trim();

                // validation of data
                if (title.length() == 0) {
                    showSnackbar("title cannot be empty");
                    return;
                }
                if (option1.length() == 0 ||
                        option2.length() == 0 ||
                        (option3View.getVisibility() == View.VISIBLE && option3.length() == 0) ||
                        (option4View.getVisibility() == View.VISIBLE && option4.length() == 0)
                        ) {
                    showSnackbar("option cannot be empty");
                    return;
                }
                //everything is good, so add data to firebase

                final Snackbar addingDiscussionSnackbar = Snackbar.make(findViewById(R.id.parent_layout), "Adding poll", Snackbar.LENGTH_INDEFINITE);
                addingDiscussionSnackbar.show();
                dialog.dismiss();

                List<String> optionsList = new ArrayList<>();
                optionsList.add(option1);
                optionsList.add(option2);
                if (optionToCheck > 2)
                    optionsList.add(option3);
                if (optionToCheck > 3)
                    optionsList.add(option4);

                String key = mPollsReference.push().getKey();
                final Poll newPoll = new Poll(title, USER_ID, USER_NAME, key, optionsList);
                if (key != null) {
                    mPollsReference.child(key).setValue(newPoll).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            addingDiscussionSnackbar.dismiss();
                            pollList.add(newPoll);
                            mPollAdapter.notifyDataSetChanged();
                            showSnackbar("Poll added");

                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            addingDiscussionSnackbar.dismiss();
                            showSnackbar("Try again later");
                        }
                    });
                }
            }
        });

        dialog.show();
    }

    private void showSnackbar(String msg) {
        Snackbar.make(findViewById(R.id.parent_layout), msg, Snackbar.LENGTH_SHORT).show();
    }

    private int getOptionFromOptionId(int id) {
        switch (id) {
            case R.id.poll_option1:
                return 1;
            case R.id.poll_option2:
                return 2;
            case R.id.poll_option3:
                return 3;
            case R.id.poll_option4:
                return 4;
        }
        return -1;
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
                            ArrayAdapter<String> adapter = new ArrayAdapter<>(PollActivity.this, android.R.layout.simple_dropdown_item_1line, cities);
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
                loadPolls();
            }
        });
    }
}
