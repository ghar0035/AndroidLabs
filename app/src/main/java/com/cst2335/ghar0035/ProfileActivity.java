package com.cst2335.ghar0035;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.Instrumentation;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageButton;

public class ProfileActivity extends AppCompatActivity {

    public static final String TAG = "PROFILE_ACTIVITY";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        Log.e(TAG, "In function: onCreate ");  /*add logging information calls to all of the functions*/


        ImageButton imageButton = findViewById(R.id.imageButton2);
    /*    imageButton.setOnClickListener(click ->  {

            ActivityResultLauncher<Intent> myPictureTakerLauncher = registerForActivityResult(
                    new ActivityResultContracts.StartActivityForResult()
                    ,new ActivityResultCallback<Instrumentation.ActivityResult>() {
                        @Override
                        public void onActivityResult(Instrumentation.ActivityResult result) {
                            if (result.getResultCode() == Activity.RESULT_OK) {
                                Intent data = result.getData();
                                Bitmap imgbitmap = (Bitmap) data.getExtras().get("data");
                                imgView.setImageBitmap(imgbitmap);
                            }
                            else if(result.getResultCode() == Activity.RESULT_CANCELED)
                                Log.i(TAG, "User refused to capture a picture.");
                        }
                    } );

            private void dispatchTakePictureIntent() {
                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                    myPictureTakerLauncher.launch(takePictureIntent);
                }
            }
        });


*/


    }
}