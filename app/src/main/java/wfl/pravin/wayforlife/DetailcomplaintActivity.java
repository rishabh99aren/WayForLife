package wfl.pravin.wayforlife;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import static wfl.pravin.wayforlife.ComplaintsActivity.EXTRA_CITY;
import static wfl.pravin.wayforlife.ComplaintsActivity.EXTRA_COMPLAINT;
import static wfl.pravin.wayforlife.ComplaintsActivity.EXTRA_URL;

public class DetailcomplaintActivity extends AppCompatActivity {

    private ImageView postedimage;
    private TextView postedcity;
    private TextView postedcomplaint;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailcomplaint);


        postedimage=(ImageView)findViewById(R.id.postedimage);
        postedcity=(TextView)findViewById(R.id.postedcity);
        postedcomplaint=(TextView)findViewById(R.id.postcomplaint);

        Intent intent=getIntent();
        String imageurl=intent.getStringExtra(EXTRA_URL);
        String city=intent.getStringExtra(EXTRA_CITY);
        String complaint=intent.getStringExtra(EXTRA_COMPLAINT);


        Picasso.get().load(imageurl).into(postedimage);
        postedcity.setText(city);
        postedcomplaint.setText(complaint);



    }

}
