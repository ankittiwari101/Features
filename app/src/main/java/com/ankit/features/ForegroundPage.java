package com.ankit.features;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.app.ActivityManager;
import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class ForegroundPage extends AppCompatActivity {
    private final int JOB_ID = 0;
    boolean serviceStarted;
    AppCompatButton button;
    TextView textView;
    private static final String TAG = "Foreground";

    void setTheme(){
        SharedPreferences sharedPreferences = getSharedPreferences("Theme",MODE_PRIVATE);
        int themeNo = sharedPreferences.getInt("theme_no",4);
        switch (themeNo){
            case 1:
                getTheme().applyStyle(R.style.OverlayThemeLime,true);
                break;
            case 2:
                getTheme().applyStyle(R.style.OverlayThemeRed,true);
                break;
            case 3:
                getTheme().applyStyle(R.style.OverlayThemeGreen,true);
                break;
            case 4:
                getTheme().applyStyle(R.style.OverlayThemeBlue,true);
                break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme();
        setContentView(R.layout.activity_foreground_page);
        button = findViewById(R.id.btn_fgservice);
        textView = findViewById(R.id.fgservice_status);
        serviceStarted = isMyJobRunning();

        Log.d(TAG, "onCreate: is foreground service running ? "+serviceStarted);
        if(serviceStarted){
            textView.setTextColor(Color.GREEN);
            textView.setText("FOREGROUND SERVICE IS ALREADY RUNNING!! CHECK NOTIFICATION");
            button.setText("Stop Foreground Service");
        }
        else{
            textView.setText("");
            button.setText("Start Foreground Service");
        }
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!serviceStarted){
                    serviceStarted = true;
                    scheduleJob();
                    button.setText("Stop Foreground Service");
                    textView.setTextColor(Color.GREEN);
                    textView.setText("FOREGROUND SERVICE STARTED!! CHECK NOTIFICATION");
                }
                else{
                    serviceStarted = false;
                    stopJobService();
                    button.setText("START FOREGROUND SERVICE");
                    textView.setTextColor(Color.BLUE);
                    textView.setText("FOREGROUND SERVICE STOPPED");
                }
            }
        });
    }

    void stopJobService(){
        JobScheduler jobScheduler = (JobScheduler) getSystemService(Context.JOB_SCHEDULER_SERVICE);
        jobScheduler.cancel(JOB_ID);
    }

    boolean isMyJobRunning(){
        JobScheduler scheduler = (JobScheduler) getSystemService(JOB_SCHEDULER_SERVICE);
        boolean hasBeenScheduled = false;
        Log.d(TAG, "isMyJobRunning: "+scheduler.getAllPendingJobs().size());
        for (JobInfo jobInfo : scheduler.getAllPendingJobs()) {
            Log.d(TAG, "isMyJobRunning: pending job id = "+jobInfo.getId());
            if (jobInfo.getId() == JOB_ID) {
                hasBeenScheduled = true;
                Log.d(TAG, "isMyJobRunning: Job is already running");
                break;
            }
        }

        Log.d(TAG, "isMyJobRunning: "+hasBeenScheduled);
        return hasBeenScheduled;
    }

    private void scheduleJob(){
        Log.d("Util","Within Schedule job");
        ComponentName serviceComponent = new ComponentName(getApplicationContext(), ForegroundService.class);
        JobInfo.Builder builder = new JobInfo.Builder(JOB_ID, serviceComponent);
        builder.setOverrideDeadline(500);
        builder.setMinimumLatency(0);
        PersistableBundle bundle = new PersistableBundle();
        bundle.putString("job_scheduled","true");
        builder.setExtras(bundle);
        //builder.setBackoffCriteria(600,JobInfo.BACKOFF_POLICY_LINEAR);

        //builder.setRequiredNetworkType(JobInfo.NETWORK_TYPE_UNMETERED); // require unmetered network
        //builder.setRequiresDeviceIdle(true); // device should be idle
        builder.setRequiresCharging(false); // we don't care if the device is charging or not
        JobScheduler jobScheduler = (JobScheduler) getApplicationContext().getSystemService(Context.JOB_SCHEDULER_SERVICE);
        jobScheduler.schedule(builder.build());
        //startService(new Intent(Explorer.this,BackgroundService.class));
    }
}