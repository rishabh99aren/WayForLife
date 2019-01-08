package wfl.pravin.wayforlife;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import static wfl.pravin.wayforlife.ComplaintRecyclerAdapter.ViewHolder.EXTRA_CITY;
import static wfl.pravin.wayforlife.ComplaintRecyclerAdapter.ViewHolder.EXTRA_URL;

public class DetailcomplaintActivity extends AppCompatActivity {

    private ImageView postedimage;
    private TextView postedcity;
    private DatabaseReference detaildatabaseref;
    private FirebaseDatabase detailfirebase;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailcomplaint);


        postedimage=(ImageView)findViewById(R.id.postedimage);
        postedcity=(TextView)findViewById(R.id.postedcity);

        Intent intent=getIntent();
        String imageurl=intent.getStringExtra(EXTRA_URL);
        String city=intent.getStringExtra(EXTRA_CITY);

        Picasso.get().load(imageurl).fit().centerInside().into(postedimage);
        postedcity.setText(city);
    }

}
