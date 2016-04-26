package com.example.kamil.projectapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

// SHould the server send back a boolean if the AlarmActivity is armed?

public class AlarmActivity extends Activity {

    private Button armButton;
    private Button disarmButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm);

        armButton = (Button)findViewById(R.id.arm_alarm_button);
        disarmButton = (Button)findViewById(R.id.disarm_alarm_button);

        //Back button goes back to Main Menu
        disarmButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

            }
        });

        //Arm Button - arms alarm
        armButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

            }
        });

    }

}
