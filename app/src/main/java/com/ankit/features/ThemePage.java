package com.ankit.features;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.util.Timer;
import java.util.TimerTask;

public class ThemePage extends AppCompatActivity {

    SharedPreferences sharedPreferences;

    AppCompatButton themeButton1,themeButton2,themeButton3,themeButton4;
    private static final String TAG = "ThemePage";
    int themeNo = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        sharedPreferences = getSharedPreferences("Theme",MODE_PRIVATE);
        themeNo = sharedPreferences.getInt("theme_no",1);
        switch (themeNo){
            case 1:
                getTheme().applyStyle(R.style.OverlayThemeLime1,true);
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
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_theme_page);
        themeButton1 = findViewById(R.id.btn_changeTheme1);
        themeButton2 = findViewById(R.id.btn_changeTheme2);
        themeButton3 = findViewById(R.id.btn_changeTheme3);
        themeButton4 = findViewById(R.id.btn_changeTheme4);

        themeButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick: Theme button clicked");
                sharedPreferences.edit().putInt("theme_no",1).apply();
                recreate();
            }
        });

        themeButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick: Theme button clicked");
                sharedPreferences.edit().putInt("theme_no",2).apply();
                recreate();
            }
        });
        themeButton3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick: Theme button clicked");
                sharedPreferences.edit().putInt("theme_no",3).apply();
                recreate();
            }
        });
        themeButton4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick: Theme button clicked");
                sharedPreferences.edit().putInt("theme_no",4).apply();
                recreate();
            }
        });
    }
}