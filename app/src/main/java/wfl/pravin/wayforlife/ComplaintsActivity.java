package wfl.pravin.wayforlife;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import java.util.Objects;

public class ComplaintsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complaints);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
    }
}
