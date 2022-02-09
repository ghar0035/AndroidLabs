package com.cst2335.ghar0035;

import androidx.appcompat.app.AppCompatActivity;

import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_linear);


        Resources res = getResources();
        String toast_message= res.getString(R.string.toast_message);
        String snackbar_on= res.getString(R.string.snackbar_on);
        String snackbar_off= res.getString(R.string.snackbar_off);
        String Undo = res.getString(R.string.Undo);

        Button bttn = findViewById(R.id.button2);
        bttn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText( MainActivity.this,toast_message,Toast.LENGTH_LONG).show();
            }
        });

        Switch sw = findViewById(R.id.switch1);

        sw.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    Snackbar snackbar = Snackbar
                            .make(findViewById(R.id.layoutRoot), snackbar_on, Snackbar.LENGTH_LONG)
                            .setAction(Undo, new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    String status="";
                                    Snackbar snackbar1 = Snackbar.make(findViewById(R.id.layoutRoot), snackbar_off+ status, Snackbar.LENGTH_SHORT);
                                    snackbar1.show();
                                }
                            });
                    snackbar.show();
                }else {
                    Snackbar snackbar = Snackbar
                            .make(findViewById(R.id.layoutRoot), snackbar_off, Snackbar.LENGTH_LONG)
                            .setAction(Undo, new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    String status="";
                                    Snackbar snackbar1 = Snackbar.make(findViewById(R.id.layoutRoot), snackbar_on+ status, Snackbar.LENGTH_SHORT);
                                    snackbar1.show();
                                }
                            });
                    snackbar.show();
                }
            }
        });
        /*
        Resources resources = getResources();
        String toast_message= res.getString(R.string.toast_message);
        String snackbar_on= res.getString(R.string.snackbar_on);
        String snackbar_off= res.getString(R.string.snackbar_off);

        Button bttn = findViewById(R.id.button2);
        bttn.setOnClickListener( v -> {
                Toast.makeText(MainActivity.this, toast_message, Toast.LENGTH_LONG).show();
        });

        Switch sch = findViewById(R.id.switch1);
        sch.setOnCheckedChangeListener(CompoundButton cb, boolean b){
            if (b == true) {
                Snackbar.make(findViewById(R.id.layoutRoot), snackbar_on, Snackbar.LENGTH_SHORT);
                Snackbar.setAction( “Undo”, click -> cb.setChecked(!b);
                Snackbar.show();
            }else(b == false ) {
                Snackbar.make(findViewById(R.id.layoutRoot), R.string.snackbar_off, Snackbar.LENGTH_SHORT);
                Snackbar.setAction( “Undo”, click -> cb.setChecked(!b);
                Snackbar.show();
        });

*/



    }
}