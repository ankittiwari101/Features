package com.ankit.features;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import javax.security.auth.login.LoginException;

public class MainActivity extends AppCompatActivity {
    CardView runtimePermission,animation,themes,notification,backgroundService,foregroundService,sqlite,multiMedia;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme();
        setContentView(R.layout.activity_main);

        runtimePermission = findViewById(R.id.runtime);
        animation = findViewById(R.id.animation);
        themes = findViewById(R.id.themes);
        notification = findViewById(R.id.notification);
        backgroundService = findViewById(R.id.backgroundService);
        foregroundService = findViewById(R.id.foregroundService);
        sqlite = findViewById(R.id.sqlite);
        multiMedia = findViewById(R.id.multiMedia);

        backgroundService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(MainActivity.this,"Background Service",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(MainActivity.this, BackgroundPage.class);
                startActivity(intent);
            }
        });

        foregroundService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ForegroundPage.class);
                startActivity(intent);
            }
        });

        notification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,NotificationPage.class);
                startActivity(intent);
            }
        });

        runtimePermission.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this,RuntimePermission.class);
            startActivity(intent);
        });

        themes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,ThemePage.class);
                startActivity(intent);
            }
        });

        animation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,AnimationPage.class);
                startActivity(intent);
            }
        });

        sqlite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,SQLitePage.class);
                startActivity(intent);
            }
        });
        multiMedia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,MultimediaPage.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        recreate();
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