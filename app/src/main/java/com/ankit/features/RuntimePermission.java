package com.ankit.features;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import static android.Manifest.permission.SEND_SMS;

public class RuntimePermission extends AppCompatActivity {

    AppCompatButton btnRequestPermission;

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
        setContentView(R.layout.activity_runtime_permission);
        btnRequestPermission = findViewById(R.id.btn_requestPermission);

        btnRequestPermission.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!checkPermission()){
                    requestPermission();
                }
            }
        });

    }

    private boolean checkPermission() {
        int result = ContextCompat.checkSelfPermission(RuntimePermission.this, SEND_SMS);
        //int result1 = ContextCompat.checkSelfPermission(RuntimePermission.this, WRITE_EXTERNAL_STORAGE);
        return result == PackageManager.PERMISSION_GRANTED  ;
    }

    private void requestPermission() {
        ActivityCompat.requestPermissions(RuntimePermission.this, new String[]{SEND_SMS}, 666);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 666:
                if (grantResults.length > 0) {
                    boolean CALL_PHONEAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    // boolean SStorageAccepted = grantResults[1] == PackageManager.PERMISSION_GRANTED;

                    if (CALL_PHONEAccepted)
                        Toast.makeText(this, "Permission Granted,Thank you", Toast.LENGTH_SHORT).show();
                    else {
                        Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show();

                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            if (shouldShowRequestPermissionRationale(SEND_SMS))
                            {
                                showMessageOKCancel("You need to allow access to the permissions",
                                        new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                                    requestPermissions(new String[]{SEND_SMS},
                                                            666);
                                                }
                                            }
                                        });
                                return;
                            }
                        }
                    }
                }
                break;
        }
    }

    private void showMessageOKCancel(String message, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(RuntimePermission.this)
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", null)
                .create()
                .show();
    }
}