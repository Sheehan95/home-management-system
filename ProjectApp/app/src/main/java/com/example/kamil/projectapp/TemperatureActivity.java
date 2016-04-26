package com.example.kamil.projectapp;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

public class TemperatureActivity extends Activity {

    private TextView result;
    private GetTemperature test;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_temperature);

        result = (TextView) findViewById(R.id.result);

        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        try {
                            test = new GetTemperature();
                            test.execute();
                            result.setText(test.get());
                        } catch (Exception e) {
                            e.printStackTrace();
                            result.setText("Failure");
                        }

                    }
                });
            }
        }, 0, 1000);

    }

}










