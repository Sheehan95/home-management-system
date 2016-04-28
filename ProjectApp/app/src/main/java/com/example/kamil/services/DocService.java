package com.example.kamil.services;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Handler;

/**
 * Created by sheeha7 on 28/04/2016.
 */
public class DocService extends Service {

    Intent intent;
    Timer timer;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onStart(Intent intent, int startId) {
        super.onStart(intent, startId);
        Log.e("SERVICE", "DocService has started");

        timer = new Timer();

        TimerTask task = new TimerTask(){
            @Override
            public void run() {
                Log.e("RUN", "RUNNING");
            }
        };

        timer.schedule(task, 0, 1000);

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.e("SERVICE", "DocService has stopped");
    }



}
