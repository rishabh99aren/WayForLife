package wfl.pravin.wayforlife;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.Locale;
import java.util.Objects;

public class DetailcomplaintActivity extends AppCompatActivity {
    double lat, lng;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailcomplaint);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        TextView title = findViewById(R.id.title);
        TextView byName = findViewById(R.id.by_name);
        ImageView image = findViewById(R.id.image);
        TextView desc = findViewById(R.id.desc);
        Button locationButton = findViewById(R.id.location_button);

        Intent intent=getIntent();

        lat = intent.getDoubleExtra(ComplaintsActivity.EXTRA_COMPLAINT_LAT, 0);
        lng = intent.getDoubleExtra(ComplaintsActivity.EXTRA_COMPLAINT_LNG, 0);
        title.setText(intent.getStringExtra(ComplaintsActivity.EXTRA_COMPLAINT_TITLE));
        byName.setText(intent.getStringExtra(ComplaintsActivity.EXTRA_COMPLAINT_BY_USER));
        String imageurl = intent.getStringExtra(ComplaintsActivity.EXTRA_IMAGE_URL);
        desc.setText(intent.getStringExtra(ComplaintsActivity.EXTRA_COMPLAINT_DESC));
        locationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String uri = String.format(Locale.ENGLISH, "geo:%f,%f", lat, lng);
                Intent intent1 = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
                startActivity(intent1);
            }
        });

        Picasso.get().load(imageurl).into(image);
    }

}
