package wfl.pravin.wayforlife;

import android.Manifest;
import android.app.Dialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.Objects;

import wfl.pravin.wayforlife.models.Complaint;


public class AddComplaintActivity extends AppCompatActivity {

    private static final int LOCATION_PERMISSION_REQ_CODE = 234;
    public static final int PICK_IMAGE_REQUEST_CODE = 123;
    private static final String TAG = "Nitin";

    double lat, lng;
    FusedLocationProviderClient mFusedLocationClient;
    private boolean mLocationPermissionGranted = false;

    //TODO: replace after auth module is complete
    private static final String USER_NAME = "Nitin";
    private static final String USER_ID = "aca_2424_vfaffa_2222";
    private static final String COMPLAINT = "complaints";
    private static String USER_CITY = "Rupnagar";

    String currentDateTimeString;


    private EditText mComplaintDesc;
    private EditText mComplaintTitle;
    private ConstraintLayout parentLayout;
    private ImageView mImageView;
    private Uri mImageUri;

    private StorageReference mStorageRef;
    private DatabaseReference mDatabaseRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_complaint);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        Button mButtonChoseImage = findViewById(R.id.button_choose_image);
        Button mButtonAddComplaint = findViewById(R.id.button_add_complaint);
        parentLayout = findViewById(R.id.parent_layout);
        mComplaintDesc = findViewById(R.id.complaint_desc);
        mImageView = findViewById(R.id.image_view);
        mComplaintTitle = findViewById(R.id.Complaint_title);

        mStorageRef = FirebaseStorage.getInstance().getReference(COMPLAINT);
        mDatabaseRef = FirebaseDatabase.getInstance().getReference(COMPLAINT).child(USER_CITY);

        mButtonChoseImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openFileChooser();
            }
        });

        mButtonAddComplaint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isServicesOK()) {
                    Log.d(TAG, "service is OK");
                    getLocationPermission();
                }
            }
        });

    }

    private boolean isServicesOK() {
        int available = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(this);
        if (available == ConnectionResult.SUCCESS) {
            //everything is fine
            return true;
        } else if (GoogleApiAvailability.getInstance().isUserResolvableError(available)) {
            //error but we can resolve it
            Dialog dialog = GoogleApiAvailability.getInstance().getErrorDialog(this, available, 123);
            dialog.show();
        } else {
            Toast.makeText(this, "google services unavailable", Toast.LENGTH_SHORT).show();
        }
        return false;
    }

    private void getLocationPermission() {

        if (ContextCompat.checkSelfPermission(this.getApplicationContext(), Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            mLocationPermissionGranted = true;
            getDeviceLocation();
        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, LOCATION_PERMISSION_REQ_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case LOCATION_PERMISSION_REQ_CODE: {
                if (grantResults.length > 0 && grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                    mLocationPermissionGranted = false;
                    return;
                }
                mLocationPermissionGranted = true;
                getDeviceLocation();
            }
        }
    }

    private void getDeviceLocation() {
        try {
            if (mLocationPermissionGranted) {
                mFusedLocationClient.getLastLocation().addOnSuccessListener(this, new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        if (location != null) {
                            lat = location.getLatitude();
                            lng = location.getLongitude();
                            Log.d(TAG, "got location:" + lat + ", " + lng);
                            uploadFile();
                        } else {
                            Toast.makeText(AddComplaintActivity.this, "location not found", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        } catch (SecurityException ex) {
            Log.e("Nitin", "security exception:" + ex.getMessage());
        }
    }

    private void openFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, PICK_IMAGE_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST_CODE && resultCode == RESULT_OK && data != null && data.getData() != null) {
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
            final Snackbar uploadingImageSnackbar = Snackbar.make(parentLayout, "uploading image", Snackbar.LENGTH_INDEFINITE);
            uploadingImageSnackbar.show();

            final StorageReference fileReference = mStorageRef.child(System.currentTimeMillis() + "." + getFileExtention(mImageUri));
            fileReference.putFile(mImageUri).continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                @Override
                public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                    if (!task.isSuccessful()) {
                        throw task.getException();
                    }
                    return fileReference.getDownloadUrl();
                }
            }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                @Override
                public void onComplete(@NonNull Task<Uri> task) {
                    if (task.isSuccessful()) {
                        Log.d(TAG, "Image uploaded");
                        uploadingImageSnackbar.dismiss();

                        final Snackbar uploadingComplaintSnackbar = Snackbar.make(parentLayout, "adding complaint", Snackbar.LENGTH_INDEFINITE);
                        uploadingComplaintSnackbar.show();

                        String desc = mComplaintDesc.getText().toString().trim();
                        String title = mComplaintTitle.getText().toString().trim();
                        String timestamp = currentDateTimeString;
                        String imageURL = task.getResult().toString();

                        Complaint newComplaint = new Complaint(desc, title, imageURL, lat, lng, USER_ID, USER_NAME, timestamp);

                        String key = mDatabaseRef.push().getKey();
                        if (key != null) {
                            mDatabaseRef.child(key).setValue(newComplaint).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Log.d(TAG, "complaint added to db");
                                    uploadingComplaintSnackbar.dismiss();
                                    showSnackbar("Complaint added");
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.d(TAG, "failed to add complaint in db");
                                    uploadingComplaintSnackbar.dismiss();
                                    showSnackbar("Try again later");
                                }
                            });
                        } else {
                            uploadingComplaintSnackbar.dismiss();
                            showSnackbar("Try again later");
                        }
                    } else {
                        Toast.makeText(AddComplaintActivity.this, "Error", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        } else {
            showSnackbar("Image not selected");
        }
    }

    private void showSnackbar(String msg) {
        Snackbar.make(parentLayout, msg, Snackbar.LENGTH_SHORT).show();
    }
}
