package com.ankit.features;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.widget.Toast;

import androidx.core.content.ContextCompat;

import java.util.Timer;
import java.util.TimerTask;


public class BackgroundService extends Service {

    private int counter = 0;
    TimerTask timerTask;
    Timer timer;
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        timer = new Timer();
        timerTask = new TimerTask() {
            @Override
            public void run() {
                ContextCompat.getMainExecutor(getApplicationContext()).execute(()->{
                    Toast.makeText(getApplicationContext(),"Count ======== "+counter++,Toast.LENGTH_SHORT).show();
                });
            }
        };
        timer.schedule(timerTask,0,2000);
        return START_STICKY;
    }

    @Override
    public void onDestroy() {

        timerTask.cancel();
        timer.purge();
        timer.cancel();

        super.onDestroy();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
