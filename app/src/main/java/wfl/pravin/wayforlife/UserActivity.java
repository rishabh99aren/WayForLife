package wfl.pravin.wayforlife;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
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
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Date;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;


public class UserActivity extends AppCompatActivity {
    String currentDateTimeString;
    public static final int PICK_IMAGE_REQUIES = 1;
    private Button mButtonChoseImage;
    private Button mButtonUploadImage;
    private EditText mEditTextFileName;
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
    Double latitude, longitude;


    private FusedLocationProviderClient client;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        requestPermission();

        client = LocationServices.getFusedLocationProviderClient(this);

        mButtonChoseImage = findViewById(R.id.button_chose_image);
        mButtonUploadImage = findViewById(R.id.button_upload_image);
        mTextViewShowUpload = findViewById(R.id.text_view_show_upload);
        mEditTextFileName = findViewById(R.id.edit_text_file_name);
        mImageView = findViewById(R.id.image_view);
        mProgressBass = findViewById(R.id.progress_bar);

        mStaorageRef = FirebaseStorage.getInstance().getReference("uploads");
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("uploads");


        mButtonChoseImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openFileChooser();
            }
        });

        mButtonUploadImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mUploadTask != null && mUploadTask.isInProgress()) {


                } else {
                    currentDateTimeString = SimpleDateFormat.getDateTimeInstance().format(new Date());
                    Toast.makeText(UserActivity.this, "Upload is in progress", Toast.LENGTH_SHORT).show();
                }

                uploadFile();


                //Tacking current Location of user


                if (ActivityCompat.checkSelfPermission(UserActivity.this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    return;
                }

                client.getLastLocation().addOnSuccessListener(UserActivity.this, new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {

                        if(location != null)
                        {
                            //Successfully getting location

                            TextView textView2 = findViewById(R.id.location);
                            double lat = location.getLatitude();
                            double longi = location.getLongitude();
                            myLocation = lat +"  " +longi;
                            textView2.setText(myLocation);
                        }
                    }
                });

            }
        });

        mTextViewShowUpload.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                openImagesActivity();
            }
        });

    }

    private void openFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, PICK_IMAGE_REQUIES);
    }


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
        if (mImageUri != null) {
            StorageReference fileReference = mStaorageRef.child(System.currentTimeMillis() + "." + getFileExtention(mImageUri));
            mUploadTask = fileReference.putFile(mImageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            mProgressBass.setProgress(0);
                        }
                    }, 500);

                    Toast.makeText(UserActivity.this, "Upload success", Toast.LENGTH_SHORT).show();
                    Upload upload = new Upload(mEditTextFileName.getText().toString().trim(),
                            taskSnapshot.getDownloadUrl().toString());

                    String data = mEditTextFileName.getText().toString();



                    String uploadId = mDatabaseRef.push().getKey();

//Main Statement

                    mDatabaseRef.child(currentDateTimeString).child("User Report").setValue(data);
                    mDatabaseRef.child(currentDateTimeString).child("Location").setValue(myLocation);




//                    mDatabaseRef.child(currentDateTimeString).child("Date").setValue(currentDateTimeString);
//                        User u = new User(data, "SimpleDate");
//                       mDatabaseRef.push().setValue(u);



                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(UserActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            })
            .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onProgress(UploadTask.TaskSnapshot taskSnapshot)
                {
                    double onProgress = (100.0 * taskSnapshot.getBytesTransferred()/taskSnapshot.getTotalByteCount());
                    mProgressBass.setProgress((int)onProgress);
                }
            });
        }
        else
        {
            Toast.makeText(this,"No File Selected",Toast.LENGTH_SHORT).show();
        }
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
