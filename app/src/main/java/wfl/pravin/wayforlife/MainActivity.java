package wfl.pravin.wayforlife;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        UserDataUtils.refreshUserData(this);
        mAuth = FirebaseAuth.getInstance();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.aboutUs:
                Intent intent = new Intent(this, AboutUs.class);
                this.startActivity(intent);
                return true;
            case R.id.signout:
                AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this);
                alert.setTitle("Are you sure you want to signout?");
                alert.setNegativeButton("NO", null);
                alert.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mAuth.signOut();
                        UserDataUtils.clearSharedPref(MainActivity.this);
                        startActivity(new Intent(MainActivity.this, LoginActivity.class));
                        finish();

                    }
                });
                alert.show();
                return true;

            case R.id.donate_us:
                Uri uri = Uri.parse("https://milaap.org/fundraisers/wayforlife?utm_source=shorturl");
                Intent intent1 = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent1);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }

    }

    public void addComplaint(View view) {
        Intent i = new Intent(MainActivity.this, AddComplaintActivity.class);
        startActivity(i);
    }

    public void openEvents(View view) {
        Intent i = new Intent(MainActivity.this, RetriveeventsActivity.class);
        startActivity(i);
    }

    public void openNewsFeed(View view) {
        Intent i = new Intent(MainActivity.this, NewFeedActivity.class);
        startActivity(i);
    }

    public void openBloodDonation(View view) {
        Uri uri = Uri.parse("http://www.friends2support.org/");
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(intent);
    }
}
