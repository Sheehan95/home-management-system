package com.example.kamil.projectapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

// SHould the server send back a boolean if the Alarm is armed?

public class Alarm extends Activity {

    private Button backButton;
    private Button armButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm);

        backButton = (Button)findViewById(R.id.backButtonAlarm);
        armButton = (Button)findViewById(R.id.armButton);


        //Back button goes back to Main Menu
        backButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                startActivity(new Intent(Alarm.this, MainActivity.class));
            }


        });

        //Arm Button - arms alarm
        armButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
               //code to arm goes here
            }


        });



    }

}
