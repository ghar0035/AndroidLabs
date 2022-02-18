package com.cst2335.ghar0035;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

public class MainActivity extends AppCompatActivity {
    public static final String TAG = "Main_Activity";
    public final static String USER_DETAIL = "UserDetails";
    // View variables
    private Button loginButton;
    private EditText inputEmail;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_login);

        loginButton = findViewById(R.id.btnLogin);
        inputEmail = findViewById(R.id.emailAddress);

        // get from shared value file the email address and set the email field text to that value
        SharedPreferences prefs = getSharedPreferences( USER_DETAIL , Context.MODE_PRIVATE);
        String emailAddress = prefs.getString("emailAddress", "");   //save instance
        inputEmail.setText(emailAddress);    //restore instance


        loginButton.setOnClickListener(click -> {
            Intent goToProfile = new Intent(MainActivity.this, ProfileActivity.class);
            goToProfile.putExtra("EMAIL",inputEmail.getText().toString() );
            startActivity(goToProfile);
        });


    }

    @Override
    protected void onPause() {
        super.onPause();

        // Save the email input text into shared preferences
        SharedPreferences prefs = getSharedPreferences( USER_DETAIL , Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = prefs.edit();
        edit.putString("emailAddress",inputEmail.getText().toString());
        edit.commit();
    }
}

