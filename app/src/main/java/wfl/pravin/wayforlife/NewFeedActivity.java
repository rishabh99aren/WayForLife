package wfl.pravin.wayforlife;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class NewFeedActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_feed);

        Button postevent=(Button)findViewById(R.id.button2);

        postevent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(NewFeedActivity.this,RegistereventsActivity.class));
                finish();
            }
        });

    }

    public void openComplaintsActivity(View view) {
        Intent i = new Intent(NewFeedActivity.this, ComplaintsActivity.class);
        startActivity(i);
    }
}
