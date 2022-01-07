package com.ankit.features;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.job.JobParameters;
import android.app.job.JobService;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;

import java.util.Timer;
import java.util.TimerTask;

public class ForegroundService extends JobService {
    private static final String TAG = "ForegroundService";
    private TimerTask timerTask;
    private Timer timer;


    @Override
    public void onCreate() {
        Log.d(TAG, "onCreate: within on create");
        PendingIntent contentIntent =PendingIntent.getActivity(this,0,new Intent(this,ForegroundPage.class),PendingIntent.FLAG_UPDATE_CURRENT);
        NotificationManager notificationManager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
        String channelId = Build.VERSION.SDK_INT >= Build.VERSION_CODES.O ? createNotificationChannel(notificationManager) : "";
        NotificationCompat.Builder noBuilder =new NotificationCompat.Builder(this,channelId);
        Notification notification =noBuilder.setOngoing(true)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("Features App")
                .setContentText("Foreground Service Running")
                .setContentIntent(contentIntent)
                .setPriority(-2)
                .setCategory(NotificationCompat.CATEGORY_SERVICE)
                .build();

        startForeground(1337,notification);
        ContextCompat.getMainExecutor(getApplicationContext()).execute(()->{
            Toast.makeText(getApplicationContext(),"Job service started",Toast.LENGTH_SHORT).show();
        });
        super.onCreate();
    }

    @Override
    public boolean onStartJob(JobParameters jobParameters) {
        //Note :- Important to return true here if you want to keep the service running.
        return true;
    }

    @Override
    public boolean onStopJob(JobParameters jobParameters) {
        return false;
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private String createNotificationChannel(NotificationManager notificationManager){
        String channelId = "my_service_channelid";
        String channelName = "My Foreground Service";
        NotificationChannel channel = new NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_HIGH);
        // omitted the LED color
        channel.setImportance(NotificationManager.IMPORTANCE_NONE);
        channel.setLockscreenVisibility(Notification.VISIBILITY_PRIVATE);
        notificationManager.createNotificationChannel(channel);
        return channelId;
    }
}
