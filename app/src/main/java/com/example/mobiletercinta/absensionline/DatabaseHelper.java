package com.example.mobiletercinta.absensionline;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String TAG = "DatabaseHelper";
    private static final int DB_VERSION = 1;
    private static final String TABLE_NAME = "waktu_eksekusi";
    private static final String COL1 = "No";
    private static final String COL2 = "Nama";
    private static final String COL3 = "Start";
    private static final String COL4 = "End";
    private static final String COL5 = "Delta";
    private static final String COL6 = "Time";

    public DatabaseHelper(Context context) {
        super(context, TABLE_NAME, null, DB_VERSION);
    }


    private static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + " (" +
                    COL1 + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COL2 + " TEXT, " +
                    COL3 + " TEXT, " +
                    COL4 + " TEXT, " +
                    COL5 + " TEXT, " +
                    COL6 + " TEXT );";


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
    public boolean insertData(String nama, String start, String end, String delta , String time) {

        SQLiteDatabase db;
        db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL2, nama);
        contentValues.put(COL3, start);
        contentValues.put(COL4, end);
        contentValues.put(COL5, delta);
        contentValues.put(COL6, time);

        long result = db.insert(TABLE_NAME, null, contentValues);

        //if date as inserted incorrectly it will return -1
        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }
    public Cursor getData(){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME;
        Cursor data = db.rawQuery(query, null);
        return data;
    }


}