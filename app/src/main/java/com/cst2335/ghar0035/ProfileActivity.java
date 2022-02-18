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
import android.content.res.Resources;
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

    ImageView imgView;
    ImageButton imageButton;
    EditText emailField;

    // Activity launcher, when we created the object we can use it to get to as many activity as we need to get to
    ActivityResultLauncher<Intent> myPictureTakerLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult()
            ,new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {

                    Resources res = getResources();
                    String onActivityMsg = res.getString(R.string.onActivityMsg);
                    String refusedMsg = res.getString(R.string.refusedMsg);

                    Log.e(TAG , onActivityMsg );

                   if (result.getResultCode() == Activity.RESULT_OK) {    //user select ok and
                       Intent data = result.getData();
                       Bitmap imgbitmap = (Bitmap) data.getExtras().get("data");
                       imgView = findViewById(R.id.imageViewBox);
                       imgView.setImageBitmap(imgbitmap);
                    } else if(result.getResultCode() == Activity.RESULT_CANCELED) {   //user select cancel
                       Log.e(TAG, refusedMsg);
                   }
                }
            });


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        Resources res = getResources();
        String onCreateMsg = res.getString(R.string.onCreateMsg);

        Log.e(TAG, onCreateMsg);


        imageButton = findViewById(R.id.btnUpload);
        emailField = findViewById(R.id.yourEmail);

        // set default value email for email field, coming from MainActivity
        Intent fromMain = getIntent();
        emailField.setText(fromMain.getStringExtra("EMAIL"));


        imageButton.setOnClickListener(click -> {
                    this.dispatchTakePictureIntent();
                });


    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        if(takePictureIntent.resolveActivity(getPackageManager()) != null){
             myPictureTakerLauncher.launch(takePictureIntent);     //this acts same as startActivity(Intent) to go to a new activity
        }
    }

    @Override
    protected void onStart() {
        super.onStart();

        Resources res = getResources();
        String onStartMsg = res.getString(R.string.onStartMsg);

        Log.e(TAG , onStartMsg);
    }

    @Override
    protected void onResume() {
        super.onResume();

        Resources res = getResources();
        String onResumeMsg = res.getString(R.string.onResumeMsg);

        Log.e(TAG , onResumeMsg);
    }

    @Override
    protected void onPause() {
        super.onPause();

        Resources res = getResources();
        String onPauseMsg = res.getString(R.string.onPauseMsg);

        Log.e(TAG, onPauseMsg);
    }

    @Override
    protected void onStop() {
        super.onStop();

        Resources res = getResources();
        String onStopMsg = res.getString(R.string.onStopMsg);

        Log.e(TAG , onStopMsg);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        Resources res = getResources();
        String onDestroyMsg = res.getString(R.string.onDestroyMsg);

        Log.e(TAG ,onDestroyMsg);
    }
}