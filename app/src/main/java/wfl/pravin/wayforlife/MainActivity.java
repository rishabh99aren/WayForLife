package wfl.pravin.wayforlife;

import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;
public class MainActivity extends AppCompatActivity
{
    private Toolbar toolbar ;
    ImageView reportButton,newsFeedButton,bloodReqButton,donateButton,calenderButton,addEvents;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        toolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);


        reportButton =(ImageView)findViewById(R.id.userReport);
        newsFeedButton = (ImageView)findViewById(R.id.newsFeed);
        bloodReqButton = (ImageView)findViewById(R.id.bloodReq);
        donateButton = (ImageView)findViewById(R.id.donate);
        calenderButton =(ImageView)findViewById(R.id.calender);
     //   aboutUsButton = (ImageView)findViewById(R.id.aboutUs);
        addEvents = (ImageView)findViewById(R.id.addEvents);




        //User Roport


        reportButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this,UserActivity.class);
                startActivity(i);
            }
        });

        //News Feed
        newsFeedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {


                Toast.makeText(getApplicationContext(),"You presed news feed",Toast.LENGTH_LONG).show();
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


                Uri uri = Uri.parse("https://milaap.org/fundraisers/wayforlife?utm_source=shorturl");

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


        addEvents.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {

                Intent i = new Intent(MainActivity.this,EventAct.class);
                startActivity(i);

                Toast.makeText(getApplicationContext(),"You presed addEvents us button",Toast.LENGTH_LONG).show();
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu,menu);
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case R.id.aboutUs:
                Intent intent = new Intent(this,AboutUs.class);
                this.startActivity(intent);
                return true;
            case R.id.signout:
                Intent intent1 = new Intent(this,SignOut.class);
                this.startActivity(intent1);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }
}
