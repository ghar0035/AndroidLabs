package com.cst2335.ghar0035;


import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.core.view.GravityCompat;

import com.google.android.material.navigation.NavigationView;

public class TestToolbar extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    public static final String TAG = "TestToolbar";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_toolbar);
        Log.i(TAG , "onCreate");

        //For toolbar layout:
        Toolbar toolbar = findViewById(R.id.toolbar_layout);
        setSupportActionBar(toolbar); //causes Android to call onCreateOptionsMenu(Menu menu)
        toolbar.bringToFront();  //brings toolbar layout on top of other layouts

        //For Drawer layout:
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.open, R.string.close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        //For NavigationView:
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this); //registers for NavigationItem Selection events
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        Log.i(TAG , "onCreateOptionsMenu");

        // Inflate the menu items for use in the action bar
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.toolbar_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Log.i(TAG, "onOptionsItemSelected");

        String message = null;
        switch (item.getItemId()) {
            case R.id.play:
                message = "You clicked on play icon";
                break;
            case R.id.pause:
                message = "You clicked on pause icon";
                break;
            case R.id.stop:
                message = "You clicked on stop icon";
                break;
            case R.id.faceBook:
                message = "You clicked on the overflow menu";
                break;
        }
        if (message != null){
            Toast.makeText(this, message, Toast.LENGTH_LONG).show();
        }
        return true;
    }

    //@Override
    public boolean onNavigationItemSelected(MenuItem item) {
        Log.i(TAG, "onNavigationItemSelected");

        switch(item.getItemId()) {
            case R.id.ChatPage:
                Intent gotoChat = new Intent(TestToolbar.this , ChatRoomActivity.class);
                startActivity(gotoChat);
                break;
            case R.id.WeatherForecast:
                Intent gotoForecast = new Intent(TestToolbar.this, WeatherForCast.class);
                startActivity(gotoForecast);
                break;
            case R.id.GoBackToLogin:
                Intent gotoLogin = new Intent(TestToolbar.this,  MainActivity.class);
                startActivity(gotoLogin);
                finish();
                break;
        }
        DrawerLayout drawerLayout = findViewById(R.id.drawer_layout);
        drawerLayout.closeDrawer(GravityCompat.START);   //Closes the drawer
        return true;
    }
}