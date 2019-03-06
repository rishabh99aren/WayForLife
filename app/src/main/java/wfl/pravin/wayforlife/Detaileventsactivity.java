package wfl.pravin.wayforlife;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class Detaileventsactivity extends AppCompatActivity {

    private TextView dtitle,dstate,dcity,ddate,ddescription,dtime,daddress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detaileventsactivity);

        dstate=(TextView)findViewById(R.id.dstate);
        dcity=(TextView)findViewById(R.id.dCity);
        ddate=(TextView)findViewById(R.id.ddate);
        dtime=(TextView)findViewById(R.id.dtime);
        dtitle=(TextView)findViewById(R.id.dtitle);
        ddescription=(TextView)findViewById(R.id.ddescription);
        daddress=(TextView)findViewById(R.id.daddress);

        Intent intent=getIntent();

        dstate.setText("State : " +intent.getStringExtra("State"));
        dcity.setText("City : " + intent.getStringExtra("City"));
        ddate.setText("Date : " + intent.getStringExtra("Date"));
        dtime.setText("Time : " + intent.getStringExtra("Time"));
        dtitle.setText("Event : " + intent.getStringExtra("Title"));
        ddescription.setText(intent.getStringExtra("Description"));
        daddress.setText("Address : " +  intent.getStringExtra("Address"));


    }
}
