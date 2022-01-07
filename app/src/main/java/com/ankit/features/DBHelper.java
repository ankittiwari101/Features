package com.ankit.features;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DBHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "employees.db";
    public static final String TABLE_NAME = "employee";
    public static final String EMPLOYEE_ID = "emp_id";
    public static final String EMPLOYEE_NAME = "emp_name";
    public static final String EMPLOYEE_SURNAME = "emp_surname";
    public static final String JOB_TITLE = "emp_job_title";

    public DBHelper( Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE "+TABLE_NAME+"( "+EMPLOYEE_ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "+EMPLOYEE_NAME+" TEXT, "+EMPLOYEE_SURNAME+" TEXT,"+JOB_TITLE+" TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
    }

    /**
     * Inserts a new record into the SQLite Database present locally on the device.
     * @param name   The Name of the new Employee
     * @param surname  Employee Surname
     * @param jobTitle Employee Job
     * @return - Returns true if record was successfully inserted in the database,false otherwise.
     */
    public boolean insertRecord(String name,String surname,String jobTitle){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(EMPLOYEE_NAME,name);
        contentValues.put(EMPLOYEE_SURNAME,surname);
        contentValues.put(JOB_TITLE,jobTitle);

        long result = db.insert(TABLE_NAME,null,contentValues);
        return result != -1;
    }

    public Cursor getAllRecords(){
        SQLiteDatabase db = this.getWritableDatabase();
        return db.rawQuery("SELECT * FROM "+TABLE_NAME,null);
    }

    public boolean updateData(String empId,String name,String surname,String jobTitle){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(EMPLOYEE_SURNAME,surname);
        contentValues.put(EMPLOYEE_NAME,name);
        contentValues.put(JOB_TITLE,jobTitle);

        long rows = db.update(TABLE_NAME,contentValues,EMPLOYEE_ID+" = ?",new String[]{empId});
        return rows>0;
    }

    public int deleteRecord(String empId){
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_NAME,EMPLOYEE_ID+" = ?",new String[]{empId});
    }

    public int getDataCount(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM "+TABLE_NAME,null);
        return cursor.getCount();
    }
}
