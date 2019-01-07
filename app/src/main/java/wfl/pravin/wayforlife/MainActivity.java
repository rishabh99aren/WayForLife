package wfl.pravin.wayforlife;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity
{
    ImageView reportButton,newsFeedButton,bloodReqButton,donateButton,calenderButton,aboutUsButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        reportButton =(ImageView)findViewById(R.id.userReport);
        newsFeedButton = (ImageView)findViewById(R.id.newsFeed);
        bloodReqButton = (ImageView)findViewById(R.id.bloodReq);
        donateButton = (ImageView)findViewById(R.id.donate);
        calenderButton =(ImageView)findViewById(R.id.calender);
        aboutUsButton = (ImageView)findViewById(R.id.aboutUs);



        //User Roport


        reportButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this,AddcomplaintActivity.class);
                startActivity(i);
            }
        });

        //News Feed
        newsFeedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent i = new Intent(MainActivity.this, NewFeedActivity.class);
                startActivity(i);
            }
        });

        //Blodd Request
        bloodReqButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {


                Uri uri = Uri.parse("http://www.friends2support.org/");

                Intent intent = new Intent(Intent.ACTION_VIEW,uri);

                startActivity(intent);

                Toast.makeText(getApplicationContext(),"You presed blood Request butoon",Toast.LENGTH_LONG).show();
            }
        });

        //Donate
        donateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {


                Uri uri = Uri.parse("http://milap.org/fundraisers/wayforlife?utm_source=shorturl");

                Intent intent = new Intent(Intent.ACTION_VIEW,uri);

                startActivity(intent);



                Toast.makeText(getApplicationContext(),"You presed Donate button",Toast.LENGTH_LONG).show();



            }
        });


        //Calender

        calenderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Toast.makeText(getApplicationContext(),"You presed calender button",Toast.LENGTH_LONG).show();
            }
        });

        //About Us

        aboutUsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {



                Toast.makeText(getApplicationContext(),"You presed about us button",Toast.LENGTH_LONG).show();
            }
        });

    }

}
