package com.ankit.features;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class BackgroundPage extends AppCompatActivity {
    boolean serviceStarted;
    AppCompatButton button;
    TextView textView;
    private static final String TAG = "Background";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme();
        setContentView(R.layout.activity_background_page);
        button = findViewById(R.id.btn_service);
        textView = findViewById(R.id.service_status);

        serviceStarted = isMyServiceRunning(BackgroundService.class);
        Log.d(TAG, "onCreate: service started = "+serviceStarted);
        if(serviceStarted){
            textView.setTextColor(Color.GREEN);
            textView.setText("SERVICE IS ALREADY RUNNING!!");
            button.setText("Stop Service");
        }
        else{
            //textView.setTextColor(Color.BLUE);
            textView.setText("");
            button.setText("Start Service");
        }


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!serviceStarted){
                    startService(new Intent(BackgroundPage.this,BackgroundService.class));
                    serviceStarted = true;
                    textView.setTextColor(Color.GREEN);
                    textView.setText("SERVICE STARTED SUCCESSFULLY!");
                    button.setText("Stop Service");
                }
                else{
                    stopService(new Intent(BackgroundPage.this,BackgroundService.class));
                    serviceStarted = false;
                    textView.setTextColor(Color.BLUE);
                    textView.setText("SERVICE STOPPED SUCCESSFULLY!");
                    button.setText("Start Service");
                }
            }
        });
    }

    private boolean isMyServiceRunning(Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }
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
}