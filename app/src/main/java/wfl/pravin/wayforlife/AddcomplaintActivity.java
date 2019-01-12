package wfl.pravin.wayforlife;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;
import java.util.Map;

public class AddcomplaintActivity extends AppCompatActivity {

    private ImageButton mPostImage;
    private EditText mcomplaint,mcity,mcomplainttitle;
    private Button msubmitButton;
    private DatabaseReference mPostdatabase;
    private ProgressDialog mProgress;
    private StorageReference mStorage;
    private Uri mImageUri;
    private static final int GALLERY_CODE=1;
    private StorageTask muploadtask;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addcomplaint);

        mProgress=new ProgressDialog(this);
        mStorage= FirebaseStorage.getInstance().getReference();
        mPostdatabase=FirebaseDatabase.getInstance().getReference("Complaints").child("Mumbai");

        mPostImage=(ImageButton)findViewById(R.id.imageButton);
        mcomplaint=(EditText)findViewById(R.id.complaintname);
        mcomplainttitle=(EditText)findViewById(R.id.complainttitle);
        mcity=(EditText)findViewById(R.id.cityname);
        msubmitButton=(Button)findViewById(R.id.submitbutton);

        msubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(muploadtask!=null && muploadtask.isInProgress()){
                    Toast.makeText(AddcomplaintActivity.this,"Uploading in progress",Toast.LENGTH_SHORT).show();
                }else {
                    startPosting();
                }
            }
        });

        mPostImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent galleryintent=new Intent(Intent.ACTION_GET_CONTENT);
                galleryintent.setType("image/*");
                startActivityForResult(galleryintent,GALLERY_CODE);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==GALLERY_CODE && resultCode==RESULT_OK){
            mImageUri=data.getData();
            mPostImage.setImageURI(mImageUri);
        }
    }
    private String getFileExtension(Uri uri){
        ContentResolver cr=getContentResolver();
        MimeTypeMap mim=MimeTypeMap.getSingleton();
        return mim.getExtensionFromMimeType(cr.getType(uri));
    }

    private void startPosting() {
        final String complainttitle=mcomplainttitle.getText().toString().trim();

        final String complaint=mcomplaint.getText().toString().trim();
        final String city=mcity.getText().toString().trim();

        if(!TextUtils.isEmpty(complainttitle) && !TextUtils.isEmpty(complaint) &&
                !TextUtils.isEmpty(city) && mImageUri!=null){
            final StorageReference filepath=mStorage.child("City_images").child(System.currentTimeMillis() + "." +
                                                                     getFileExtension(mImageUri));
           muploadtask= filepath.putFile(mImageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Uri downloadurl = taskSnapshot.getUploadSessionUri();

                    DatabaseReference newPost = mPostdatabase.push();

                    Map<String, String> datatosave = new HashMap<>();
                    datatosave.put("userid", "fQEJHFKJ".trim());
                    datatosave.put("username", "BFQJh");
                    datatosave.put("complainttitle",complainttitle);
                    datatosave.put("complaint", complaint);
                    datatosave.put("city", city);
                    datatosave.put("timestamp", String.valueOf(java.lang.System.currentTimeMillis()));
                    datatosave.put("image",downloadurl.toString());
                    datatosave.put("latitude",Double.toString(16.7086212));
                    datatosave.put("longitude",Double.toString(74.1688269));
                    newPost.setValue(datatosave);

                    Toast.makeText(AddcomplaintActivity.this,"Posted the complaint",Toast.LENGTH_LONG).show();


                }
            }).addOnFailureListener(new OnFailureListener() {
               @Override
               public void onFailure(@NonNull Exception e) {
                   Toast.makeText(AddcomplaintActivity.this,e.getMessage(),Toast.LENGTH_LONG).show();
               }
           });
        }else {
            Toast.makeText(this,"Fill all the entries",Toast.LENGTH_SHORT).show();
        }
    }
}
