package com.cst2335.ghar0035;


import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.activity.result.ActivityResult;


import android.app.Activity;
import android.app.Instrumentation;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;

public class ProfileActivity extends AppCompatActivity {

    public static final String TAG = "PROFILE_ACTIVITY";

    // ImageView imgView;
    Button goToChat;
    ImageButton imageButton;
    EditText emailField;

     // Activity launcher
    ActivityResultLauncher<Intent> myPictureTakerLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult()
            ,new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {

                   if (result.getResultCode() == Activity.RESULT_OK) {
                       Intent data = result.getData();
                       Bitmap imgbitmap = (Bitmap) data.getExtras().get("data");
                       // imgView.setImageBitmap(imgbitmap);
                    } else if(result.getResultCode() == Activity.RESULT_CANCELED) {
                       Log.i(TAG, "User refused to capture a picture.");
                   }
                }
            });


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        Log.e(TAG, "In function: onCreate ");


        imageButton = findViewById(R.id.btnUpload);
        emailField = findViewById(R.id.yourEmail);
        goToChat = findViewById(R.id.btnGoToChat);

        // set default value email for email field, coming from MainActivity
        Intent fromMain = getIntent();
        emailField.setText(fromMain.getStringExtra("EMAIL"));


        imageButton.setOnClickListener(click -> {
                    this.dispatchTakePictureIntent();
                });

        goToChat.setOnClickListener(click ->{
            Intent goToChat = new Intent(ProfileActivity.this , ChatRoomActivity.class);
            startActivity(goToChat);
        });


    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        if(takePictureIntent.resolveActivity(getPackageManager()) != null){
             myPictureTakerLauncher.launch(takePictureIntent);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.e(TAG , "In function: onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.e(TAG , "In function: onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.e(TAG, "In function: onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.e(TAG , "In function: onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e(TAG , "In function: onDestroy");
    }
}