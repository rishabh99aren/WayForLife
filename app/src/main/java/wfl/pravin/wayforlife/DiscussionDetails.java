package wfl.pravin.wayforlife;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

public class DiscussionDetails extends AppCompatActivity {
    public static final String EXTRA_TITLE_KEY = "title";
    public static final String EXTRA_USER_NAME_KEY = "username";
    public static final String EXTRA_DISCUSSION_KEY = "discussionKey";
    private static final String TAG = "Nitin-DiscDetailsActi";

    private String title, username, key;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_discussion_details);

        getExtraFromIntent();


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
}
