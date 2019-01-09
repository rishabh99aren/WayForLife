package wfl.pravin.wayforlife;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class PollActivity extends AppCompatActivity {
    //dummy user data
    //TODO: replace after auth module is complete
    private static final String USER_NAME = "Nitin";
    private static final String USER_ID = "aca_2424_vfaffa_2222";
    private static final String USER_STATE = "Punjab";
    private static final String POLLS = "polls";
    private static String USER_CITY = "Rupnagar";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_poll);

    }

    public void addNewPoll(View view) {

    }
}
