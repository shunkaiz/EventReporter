package com.example.zhangshunkai.laiproject;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
public class EventReportActivity extends AppCompatActivity {

    private static final String TAG = EventReportActivity.class.getSimpleName();
    private EditText mEditTextLocation;
    private EditText mEditTextTitle;
    private EditText mEditTextContent;
    private ImageView mImageViewSend;
    private ImageView mImageViewCamera;
    private DatabaseReference database;
    private ImageView mImageLocation;

    private LocationTracker mLocationTracker;


    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    private static int RESULT_LOAD_IMAGE = 1;
    private ImageView img_event_picture;
    private Uri mImgUri;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_report);

        mEditTextLocation = findViewById(R.id.edit_text_event_location);
        mEditTextTitle = findViewById(R.id.edit_text_event_title);
        mEditTextContent = findViewById(R.id.edit_text_event_content);
        mImageViewCamera = findViewById(R.id.img_event_camera);
        mImageViewSend = findViewById(R.id.img_event_report);
        database = FirebaseDatabase.getInstance().getReference();

        mImageViewSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String key = uploadEvent();
            }
        });

        mAuth = FirebaseAuth.getInstance();

        //Add listener to check sign in status
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
                } else {
                    Log.d(TAG, "onAuthStateChanged:signed_out");
                }
            }
        };

        //sign in anonymously
        mAuth.signInAnonymously().addOnCompleteListener(this,  new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                Log.d(TAG, "signInAnonymously:onComplete:" + task.isSuccessful());
                if (!task.isSuccessful()) {
                    Log.w(TAG, "signInAnonymously", task.getException());
                }
            }
        });

        mLocationTracker = new LocationTracker(this);
        mLocationTracker.getLocation();
        final double latitude = mLocationTracker.getLatitude();
        final double longitude = mLocationTracker.getLongitude();

        mImageLocation = findViewById(R.id.img_event_location);
        mImageLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mEditTextLocation.setText(mLocationTracker.getLocation().toString());
            }
        });

        img_event_picture = findViewById(R.id.img_event_picture_capture);
        mImageViewCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(
                        Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, RESULT_LOAD_IMAGE);
            }


        });
    }
    private String uploadEvent() {
        String title = mEditTextTitle.getText().toString();
        String location = mEditTextLocation.getText().toString();
        String description = mEditTextContent.getText().toString();
        if (location.equals("") || description.equals("") ||
                title.equals("") || Utils.username == null) {
            return null;
        }
        //create event instance
        Event event = new Event();
        event.setTitle(title);
        event.setAddress(location);
        event.setDescription(description);
        event.setTime(System.currentTimeMillis());
        event.setUsername(Utils.username);
        String key = database.child("events").push().getKey();
        event.setId(key);
        database.child("events").child(key).setValue(event, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                if (databaseError != null) {
                    Toast toast = Toast.makeText(getBaseContext(),
                            "The event is failed, please check your network status.", Toast.LENGTH_SHORT);
                    toast.show();
                } else {
                    Toast toast = Toast.makeText(getBaseContext(), "The event is reported", Toast.LENGTH_SHORT);
                    toast.show();
                    mEditTextTitle.setText("");
                    mEditTextLocation.setText("");
                    mEditTextContent.setText("");
                }
            }
        });
        return key;
    }


    //Add authentification listener when activity starts
    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    //Remove authentification listener when activity ends
    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null != data) {
                Uri selectedImage = data.getData();
                img_event_picture.setVisibility(View.VISIBLE);
                img_event_picture.setImageURI(selectedImage);
                mImgUri = selectedImage;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }


}
