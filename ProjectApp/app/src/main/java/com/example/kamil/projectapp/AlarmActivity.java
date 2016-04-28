package com.example.kamil.projectapp;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Timer;
import java.util.TimerTask;

// SHould the server send back a boolean if the AlarmActivity is armed?

public class AlarmActivity extends Activity {

    private static final int TIMER_INTERVAL = 1000;
    private Timer timer;
    private boolean alarmOn;
    private boolean breakIn;

    private TextView breakInDetected;
    private TextView alarmStatus;
    private Button armButton;
    private Button breakInButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm);

        alarmStatus = (TextView) findViewById(R.id.alarm_status);
        breakInDetected = (TextView)findViewById(R.id.breakInLabel);

        armButton = (Button) findViewById(R.id.arm_alarm_button);
        breakInButton = (Button)findViewById(R.id.breakInButton);

        // new GetAlarmStatus().execute();

        //Arm Button - arms alarm
        armButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                new ArmAlarm().execute();
                Toast.makeText(AlarmActivity.this, "Break in Acknowledged", Toast.LENGTH_SHORT).show();
            }
        });

        breakInButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                new BreakInWatch().execute();

            }
        });
    }
    @Override
    protected void onStart() {

        super.onStart();

        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        new GetAlarmStatus().execute();
                    }
                });
            }
        }, 0, TIMER_INTERVAL);

    }

    @Override
    protected void onStop() {
        super.onStop();
        timer.cancel();
        timer.purge();
    }

    private class ArmAlarm extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... params) {
            HTTPRequestHandler requestHandler = HTTPRequestHandler.getInstance();
            requestHandler.PostArmAlarm(!alarmOn);
            return null;
        }

    }

    private class BreakInWatch extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... params) {
            HTTPRequestHandler requestHandler = HTTPRequestHandler.getInstance();
            requestHandler.PostBreakIn(!breakIn);
            return null;
        }

    }

    private class GetAlarmStatus extends AsyncTask<Void, Void, JSONObject> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected JSONObject doInBackground(Void... parms) {
            HTTPRequestHandler req = HTTPRequestHandler.getInstance();
            return req.getAlarmStatus();

        }

        protected void onPostExecute(JSONObject json) {
            try {

                alarmOn = json.getBoolean("alarm_armed");
                breakIn = json.getBoolean("break_in");

                if(breakIn) {
                    breakInDetected.setVisibility(View.INVISIBLE);
                    breakInButton.setVisibility(View.INVISIBLE);

                }
                else{

                    breakInDetected.setTextColor(Color.RED);
                    breakInDetected.setText("BREAK IN!!!");
                    breakInButton.setVisibility(View.VISIBLE);
                }


                if(alarmOn) {

                    alarmStatus.setText("Alarm is Armed!");
                    armButton.setText("Disarm Alarm");
                }
                else {

                    alarmStatus.setText("Alarm is not Armed!");
                    armButton.setText("Arm Alarm");
                }

            } catch (JSONException e) {
                alarmStatus.setText("Error has occured.");
            }


        }
    }

}