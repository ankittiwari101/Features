package com.ankit.features;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class SQLitePage extends AppCompatActivity {
    RecyclerView recyclerView;
    AppCompatButton button;
    RecordsAdapter myAdapter;
    List<RecordPojo> allRecords;
    private static final String TAG = "SQLitePage";

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
        setContentView(R.layout.activity_sqlite_page);
        recyclerView = findViewById(R.id.all_records);
        button = findViewById(R.id.btn_addRecord);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick: Button clicked");
                Dialog dialog = new Dialog(SQLitePage.this);
                dialog.setContentView(R.layout.form);
                EditText name = dialog.findViewById(R.id.et_name);
                EditText surname = dialog.findViewById(R.id.et_surname);
                EditText jobTitle = dialog.findViewById(R.id.et_jobTitle);
                AppCompatButton button = dialog.findViewById(R.id.btn_submit);

                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(name.getText().toString().trim().isEmpty()){
                            name.setError("Name must not be empty");
                            return;
                        }
                        else if(surname.getText().toString().trim().isEmpty()){
                            surname.setError("Surname must not be empty");
                            return;
                        }
                        else if(jobTitle.getText().toString().trim().isEmpty()){
                            jobTitle.setError("Job title must not be empty");
                            return;
                        }
                        DBHelper dbHelper = new DBHelper(SQLitePage.this);
                        boolean success = dbHelper.insertRecord(name.getText().toString(),surname.getText().toString(),jobTitle.getText().toString());
                        if(success){
                            //recreate();
                            /*allRecords = fetchRecords();
                            myAdapter.notifyDataSetChanged();*/
                            recreate();
                            Toast.makeText(SQLitePage.this,"Record Inserted Successfully",Toast.LENGTH_LONG).show();
                        }
                        else{
                            Toast.makeText(SQLitePage.this,"Something went wrong.Please try again later",Toast.LENGTH_LONG).show();
                        }
                        dialog.dismiss();
                    }
                });
                int width = (int)(getResources().getDisplayMetrics().widthPixels*0.90);
                int height = (int)(getResources().getDisplayMetrics().heightPixels*0.50);
                dialog.getWindow().setLayout(width,height);
                dialog.show();
            }
        });
        allRecords = fetchRecords();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        myAdapter = new RecordsAdapter(allRecords,this);
        recyclerView.setAdapter(myAdapter);
    }

    public List<RecordPojo> fetchRecords(){
        DBHelper dbHelper = new DBHelper(this);
        Cursor res = dbHelper.getAllRecords();
        List<RecordPojo> list = new ArrayList<>();
        while (res.moveToNext()){
            RecordPojo recordPojo = new RecordPojo();
            recordPojo.setEmpId(res.getString(res.getColumnIndexOrThrow(DBHelper.EMPLOYEE_ID)));
            recordPojo.setName(res.getString(res.getColumnIndexOrThrow(DBHelper.EMPLOYEE_NAME)));
            recordPojo.setSurname(res.getString(res.getColumnIndexOrThrow(DBHelper.EMPLOYEE_SURNAME)));
            recordPojo.setJobTitle(res.getString(res.getColumnIndexOrThrow(DBHelper.JOB_TITLE)));

            list.add(recordPojo);
        }
        return list;
    }
    private List<RecordPojo> dummyList(){
        List<RecordPojo> list = new ArrayList<>();
        for(int i=1;i<=3;i++){
            RecordPojo recordPojo = new RecordPojo();
            recordPojo.setName("Name "+i);
            recordPojo.setSurname("Surname "+i);
            recordPojo.setJobTitle("Job "+i);
            list.add(recordPojo);
        }
        return list;
    }
}