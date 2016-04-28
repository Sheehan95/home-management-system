package com.example.kamil.services;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import com.example.kamil.projectapp.AlarmActivity;
import com.example.kamil.projectapp.HTTPRequestHandler;
import com.example.kamil.projectapp.R;
import com.example.kamil.projectapp.TwitterActivity;

import org.json.JSONObject;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;
import java.util.logging.Handler;


public class MonitorService extends Service {

    Date previousBreakIn;
    Timer timer;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onStart(Intent intent, int startId) {
        super.onStart(intent, startId);

        timer = new Timer();

        TimerTask task = new TimerTask(){
            @Override
            public void run() {
                new GetAlarmStatus().execute();
            }
        };

        timer.schedule(task, 0, 5000);

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }


    /**
     * An Asynchronous Task that turns the heating on or off via HTTP request.
     */
    private class GetAlarmStatus extends AsyncTask<Void, Void, JSONObject> {

        @Override
        protected JSONObject doInBackground(Void... params) {
            HTTPRequestHandler requestHandler = HTTPRequestHandler.getInstance();
            return requestHandler.getAlarmStatus();
        }

        @Override
        protected void onPostExecute(JSONObject jsonObject) {

            super.onPostExecute(jsonObject);

            try {
                boolean breakIn = jsonObject.getBoolean("break_in");

                if (breakIn){

                    Date currentDate = new Date();

                    if (previousBreakIn == null){
                        previousBreakIn = currentDate;
                    }
                    else {
                        long minutes = getDateDifference(previousBreakIn, currentDate, TimeUnit.MINUTES);
                        if (minutes < 1){
                            return;
                        }
                    }

                    Notification.Builder notification = new Notification.Builder(MonitorService.this);
                    notification.setSmallIcon(R.drawable.logo);
                    notification.setContentTitle("BREAK IN");
                    notification.setContentText("There has been a break-in in your premises");
                    notification.setVibrate(new long[]{1000, 1000, 1000});
                    notification.setAutoCancel(true);

                    Intent resultIntent = new Intent(MonitorService.this, AlarmActivity.class);
                    TaskStackBuilder stackBuilder = TaskStackBuilder.create(MonitorService.this);

                    stackBuilder.addNextIntent(resultIntent);
                    PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
                    notification.setContentIntent(resultPendingIntent);

                    NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                    notificationManager.notify(0, notification.build());
                }

            } catch (Exception e){
                MonitorService.this.stopSelf();
            }

        }

        private long getDateDifference(Date d1, Date d2, TimeUnit unit){
            long differenceInMilliseconds = d2.getTime() - d1.getTime();
            return unit.convert(differenceInMilliseconds, TimeUnit.MILLISECONDS);
        }

    }

}
