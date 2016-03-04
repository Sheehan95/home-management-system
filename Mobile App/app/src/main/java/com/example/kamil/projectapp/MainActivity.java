package com.example.kamil.projectapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;

public class MainActivity extends Activity {


    private Button tempButton;
    private Button sensorButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tempButton = (Button)findViewById(R.id.tempButton);
        sensorButton = (Button)findViewById(R.id.sensorButton);

        //Temperature button
        tempButton.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                //Make the button click open new activity (Temperature activity)
                startActivity(new Intent(MainActivity.this, Temperature.class));
            }

        });

        //Alarm button
        sensorButton.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                //Make the button click open new activity(Alarm activity)
                startActivity(new Intent(MainActivity.this, Alarm.class));
            }


        });




    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
