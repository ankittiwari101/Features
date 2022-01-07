package com.ankit.features;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.app.NotificationCompat;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class NotificationPage extends AppCompatActivity {
    private static final String TAG = "NotificationPage";
    AppCompatButton normalNotification,imageNotification;
    int normalNotificationNumber,imageNotificationNumber=100;

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
        setContentView(R.layout.activity_notification_page);
        normalNotification = findViewById(R.id.btn_notnNormal);
        imageNotification = findViewById(R.id.btn_notnImage);

        normalNotification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick: normal notification clicked");
                NotificationManager notificationManager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
                String channelId = Build.VERSION.SDK_INT >= Build.VERSION_CODES.O ? createNotificationChannel(notificationManager,normalNotificationNumber) : "";
                NotificationCompat.Builder noBuilder =new NotificationCompat.Builder(NotificationPage.this,channelId);
                Notification notification =noBuilder.setOngoing(false)
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setContentTitle("Hello!")
                        .setContentText("This is normal notification no "+(normalNotificationNumber+1))
                        .setCategory(NotificationCompat.CATEGORY_EMAIL)
//                        .setChannelId(channelId)
                        .build();
                notificationManager.notify(normalNotificationNumber++,notification);
            }
        });

        imageNotification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick: image notification clicked");
                NotificationManager notificationManager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
                String channelId = Build.VERSION.SDK_INT >= Build.VERSION_CODES.O ? createNotificationChannel(notificationManager,imageNotificationNumber) : "";
                NotificationCompat.Builder noBuilder =new NotificationCompat.Builder(NotificationPage.this,channelId);
                Notification notification =noBuilder.setOngoing(false)
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setContentTitle("Hello!")
                        .setContentText("This is image notification no "+(imageNotificationNumber+1-100))
                        .setCategory(NotificationCompat.CATEGORY_EMAIL)
                        .setStyle(new NotificationCompat.BigPictureStyle().bigPicture(BitmapFactory.decodeResource(getResources(),R.drawable.github)))
//                        .setChannelId(channelId)
                        .build();
                notificationManager.notify(imageNotificationNumber++,notification);
            }
        });
    }
    @RequiresApi(Build.VERSION_CODES.O)
    private String createNotificationChannel(NotificationManager notificationManager,int id){
        Log.d(TAG, "createNotificationChannel: within");
        String channelId = "c"+id;
        String channelName = "My Notification Channel";
        NotificationChannel channel = new NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_HIGH);
        // omitted the LED color
        channel.setImportance(NotificationManager.IMPORTANCE_DEFAULT);
        channel.enableVibration(true);
        notificationManager.createNotificationChannel(channel);
        return channelId;
    }
}