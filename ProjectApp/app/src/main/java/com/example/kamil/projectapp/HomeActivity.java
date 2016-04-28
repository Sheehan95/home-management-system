package com.example.kamil.projectapp;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.kamil.services.DocService;

/**
 * The main home of the Application. Contains a link to all other activities.
 */
public class HomeActivity extends Activity {

    private Button temperatureButton;
    private Button alarmButton;
    private Button scheduleButton;
    private Button twitterButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        temperatureButton = (Button) findViewById(R.id.temperature_button);
        alarmButton = (Button) findViewById(R.id.alarm_button);
        scheduleButton = (Button) findViewById(R.id.schedule_button);
        twitterButton = (Button) findViewById(R.id.twitter_button);

        startService(new Intent(HomeActivity.this, DocService.class));

        // TemperatureActivity button
        temperatureButton.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                startActivity(new Intent(HomeActivity.this, TemperatureActivity.class));
            }
        });

        // AlarmActivity button
        alarmButton.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                startActivity(new Intent(HomeActivity.this, AlarmActivity.class));
            }
        });

        // ScheduleActivity button
        scheduleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomeActivity.this, ScheduleActivity.class));
            }
        });

        // TwitterActivity button
        twitterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomeActivity.this, TwitterActivity.class));
            }
        });

        Button button = (Button) findViewById(R.id.button_test_notification);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Notification.Builder notification = new Notification.Builder(HomeActivity.this);
                notification.setSmallIcon(R.drawable.logo);
                notification.setContentTitle("BREAK IN");
                notification.setContentText("There has been a break-in in your area");

                Intent resultIntent = new Intent(HomeActivity.this, TwitterActivity.class);
                TaskStackBuilder stackBuilder = TaskStackBuilder.create(HomeActivity.this);

                stackBuilder.addNextIntent(resultIntent);
                PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
                notification.setContentIntent(resultPendingIntent);

                NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                notificationManager.notify(0, notification.build());
            }
        });

    }

}
