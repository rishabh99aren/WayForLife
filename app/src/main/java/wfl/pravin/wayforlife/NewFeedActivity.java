package wfl.pravin.wayforlife;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class NewFeedActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_feed);
    }

    public void openComplaintsActivity(View view) {
        Intent i = new Intent(NewFeedActivity.this, ComplaintsActivity.class);
        startActivity(i);
    }
}
