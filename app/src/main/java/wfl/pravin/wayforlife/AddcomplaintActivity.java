package wfl.pravin.wayforlife;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class AddcomplaintActivity extends AppCompatActivity {

    private EditText mcomplaint,mcity;
    private Button msubmitButton;
    private DatabaseReference mPostdatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addcomplaint);

        mPostdatabase= FirebaseDatabase.getInstance().getReference().child("City");
        mcomplaint=(EditText)findViewById(R.id.complaintname);
        mcity=(EditText)findViewById(R.id.cityname);
        msubmitButton=(Button)findViewById(R.id.submitbutton);

        msubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startPosting();
            }
        });

    }

    private void startPosting() {

        String complaint=mcomplaint.getText().toString().trim();
        String city=mcity.getText().toString().trim();

        if(!TextUtils.isEmpty(complaint) && !TextUtils.isEmpty(city)){

            DatabaseReference newPost=mPostdatabase.push();

            Map<String,String> datatosave=new HashMap<>();
            datatosave.put("userid","fQEJHFKJ   ");
            datatosave.put("username","BFQJh");
            datatosave.put("complaint",complaint);
            datatosave.put("city",city);
            datatosave.put("timestamp",String.valueOf(java.lang.System.currentTimeMillis()));
            newPost.setValue(datatosave);
        }
    }
}
