package wfl.pravin.wayforlife;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Date;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;
import static android.app.Activity.RESULT_OK;

public class EventAct extends AppCompatActivity {

    //Declare all the content
//    AutoCompleteTextView autoCompleteTextView;
//    private ArrayAdapter<String> adapter;
//    String [] Country_names;


    String currentDateTimeString;
    public static final int PICK_IMAGE_REQUIES = 0;
    private Button mButtonChoseImage;
    private Button mButtonUploadImage;


    private EditText mEditTextFileName;
    private EditText mComplaintTitle;
    private EditText mCityName;

    private TextView mTextViewShowUpload;
    private ImageView mImageView;
    private ProgressBar mProgressBass;
    private Uri mImageUri;
    protected LocationListener locationListener;
    protected Context context;


    String myLocation = null;
    private StorageReference mStaorageRef;
    private DatabaseReference mDatabaseRef;

    private StorageTask mUploadTask;
    double latitude, longitude;

    private EditText mDateTime;


    private FusedLocationProviderClient client;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);


        //Get the string array
        // String[] colors =getResources().getStringArray(R.array.Name);

//        autoCompleteTextView = (AutoCompleteTextView) findViewById(R.id.coutry);
//        Country_names = getResources().getStringArray(R.array.Country_names);
//
//        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,Country_names);
//        autoCompleteTextView.setAdapter(adapter);
//
//
//        //Autocomplete code finish here
//
//        requestPermission();

        client = LocationServices.getFusedLocationProviderClient(this);

        mButtonChoseImage = findViewById(R.id.button_chose_image);
        mButtonUploadImage = findViewById(R.id.button_upload_image);
        mTextViewShowUpload = findViewById(R.id.text_view_show_upload);

        mEditTextFileName = findViewById(R.id.edit_text_file_name);

        mImageView = findViewById(R.id.image_view);
        mProgressBass = findViewById(R.id.progress_bar);

        mComplaintTitle = findViewById(R.id.ComplaintTitle);
        mCityName = findViewById(R.id.enteredCity);
       // mDateTime = findViewById(R.id.date1);


        mStaorageRef = FirebaseStorage.getInstance().getReference("Complaints1");
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("Complaints");


//        mButtonChoseImage.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                openFileChooser();
//            }
//        });

        mButtonUploadImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currentDateTimeString = SimpleDateFormat.getDateTimeInstance().format(new Date());
                Toast.makeText(EventAct.this, "Upload is in progress", Toast.LENGTH_SHORT).show();

                uploadFile();


                //Tacking current Location of user


                if (ActivityCompat.checkSelfPermission(EventAct.this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    return;
                }

                client.getLastLocation().addOnSuccessListener(EventAct.this, new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location)
                    {

                        if(location != null)
                        {
                            //Successfully getting location

                            TextView textView2 = findViewById(R.id.location);
                            double lat = location.getLatitude();
                            double longi = location.getLongitude();
                            myLocation = lat +"  " +longi;
                            latitude =lat;
                            longitude = longi;


                            textView2.setText(myLocation);
                        }
                    }
                });

            }
        });



//
//        autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            public void onItemClick(AdapterView<?> parent, View view, int position, long rowId) {
//                String selection = (String)parent.getItemAtPosition(position);
//                //TODO Do something with the selected text
//            }
//        });

        mTextViewShowUpload.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                openImagesActivity();
            }
        });

    }

//    private void openFileChooser() {
//        Intent intent = new Intent();
//        intent.setType("image/*");
//        intent.setAction(Intent.ACTION_GET_CONTENT);
//        startActivityForResult(intent, PICK_IMAGE_REQUIES);
//    }


    //Getting data from EditText For Stiring on Firebase as a description...

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUIES && resultCode == RESULT_OK && data != null && data.getData() != null)
        {
            mImageUri = data.getData();

            //Picasso.with(this).load(mImageUri).into(mImageView) ;
            mImageView.setImageURI(mImageUri);

            Picasso.get().load(mImageUri).into(mImageView);

        }
    }

    private String getFileExtention(Uri uri) {
        ContentResolver cR = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }

    private void uploadFile() {


        String report = mEditTextFileName.getText().toString();
        String title = mComplaintTitle.getText().toString();
        //  String cityname = mCityName.getText().toString();


        String datetime = mDateTime.getText().toString();




        String timestamp = currentDateTimeString.toString();
        Toast.makeText(EventAct.this, "Upload success", Toast.LENGTH_SHORT).show();
        EventUpload upload = new EventUpload(mEditTextFileName.getText().toString().trim(),
                mComplaintTitle.getText().toString().trim(),timestamp.toString().trim());




        String uploadId = mDatabaseRef.push().getKey();

        //Main Statement
        //mDatabaseRef.child("Complaints").setValue("Events");


        mDatabaseRef.child("Events").child("UserReport").setValue(report);
        mDatabaseRef.child("Events").child("Title ").setValue(title);


   //     mDatabaseRef.child(uploadId).child("Events").child("Date Time").setValue(datetime);


         mDatabaseRef.child("Events").child("Current date").setValue(timestamp);
       //  mDatabaseRef.child("Events").child("Date Time").setValue(datetime);


//
//            StorageReference fileReference = mStaorageRef.child(System.currentTimeMillis() + "." + getFileExtention(mImageUri));
//            mUploadTask = fileReference.putFile(mImageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
//                @Override
//                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
//                    Handler handler = new Handler();
//                    handler.postDelayed(new Runnable() {
//                        @Override
//                        public void run() {
//                            mProgressBass.setProgress(0);
//                        }
//                    }, 500);
//
//                    //Upload Constructor add data which you want to retrive from firebase database...
//
//
//
////                    mDatabaseRef.child(currentDateTimeString).child("Date").setValue(currentDateTimeString);
////                        User u = new User(data, "SimpleDate");
////                       mDatabaseRef.push().setValue(u);
//
//
//
//                }
//            }).addOnFailureListener(new OnFailureListener() {
//                @Override
//                public void onFailure(@NonNull Exception e) {
//                    Toast.makeText(UserActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
//                }
//            })
//            .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
//                @Override
//                public void onProgress(UploadTask.TaskSnapshot taskSnapshot)
//                {
//                    double onProgress = (100.0 * taskSnapshot.getBytesTransferred()/taskSnapshot.getTotalByteCount());
//                    mProgressBass.setProgress((int)onProgress);
//                }
//            });
    }

    //Openning file choser
    private void openImagesActivity()
    {
        Intent i = new Intent(this,ImagesActivity.class);
        startActivity(i);
    }

    //Asking for permission to user
    private void requestPermission()
    {
        ActivityCompat.requestPermissions(this,new String[]{ACCESS_FINE_LOCATION},1);
    }



}
