package wfl.pravin.wayforlife;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import wfl.pravin.wayforlife.models.Comment;
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
        //TODO: open a dialog,let user add a new title
        //TODO: don't add comments at this stage,for now we just have to add a new discussion
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        firebaseDatabase.getReference().child(DISCUSSIONS).child(USER_CITY).push().
                setValue(new Discussion(
                        "title of discussion",
                        USER_ID,
                        USER_NAME,
                        getDummyComments()
                ));
    }

    private List<Comment> getDummyComments() {
        //TODO:  delete this method
        List<Comment> dummyCommentList = new ArrayList<>();
        dummyCommentList.add(new Comment("comment1", USER_ID, USER_NAME, Calendar.getInstance().getTime().toString()));
        dummyCommentList.add(new Comment("comment2", "abua_1357_aja", "mayuri", Calendar.getInstance().getTime().toString()));
        dummyCommentList.add(new Comment("comment3", "jfdi_7494_noafbf", "nitin", Calendar.getInstance().getTime().toString()));

        return dummyCommentList;
    }
}
