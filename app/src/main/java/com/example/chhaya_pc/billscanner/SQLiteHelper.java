package com.example.chhaya_pc.billscanner;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by SAMUEL-pc on 2/3/2018.
 */

public class SQLiteHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "SQLiteDatabase.db";

    public static final String TABLE_NAME = "MONTHLY_TRACKING";
    public static final String COLUMN_ID = "ID";
    public static final String TIMESTAMP="TIMESTAMP";
    public static final String CATEGORY = "CATEGORY";
    public static final String AMOUNT = "AMOUNT";

    public SQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE_NAME + " ( " + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + TIMESTAMP + " VARCHAR, " +CATEGORY + " VARCHAR, " + AMOUNT + " VARCHAR);");
        db.execSQL("create table FIXED_EXPENSES ( ID INTEGER PRIMARY KEY AUTOINCREMENT, EXPENSE_NAME VARCHAR, VALUE VARCHAR );");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + "FIXED_EXPENSES");
        onCreate(db);
    }
    void updateFixedSpends(String expenseName,String value){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("update FIXED_EXPENSES SET VALUE=\"\" WHERE EXPENSE_NAME=\""+expenseName+"\"");
        db.close();
    }
void addSpend(MonthlyTrackingBean spend) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(TIMESTAMP, spend.getTimeStamp()); // Contact Name
        values.put(CATEGORY, spend.getCategory()); // Contact Phone
        values.put(AMOUNT,spend.getAmount());

        // Inserting Row
        db.insert(TABLE_NAME, null, values);
        //2nd argument is String containing nullColumnHack
        db.close(); // Closing database connection
    }

    public List<MonthlyTrackingBean> getAllSpends() {
        List<MonthlyTrackingBean> beanList = new ArrayList<MonthlyTrackingBean>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_NAME;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                MonthlyTrackingBean spend = new MonthlyTrackingBean();
                spend.setID(Integer.parseInt(cursor.getString(0)));
                spend.setTimeStamp(cursor.getString(1));
                spend.setCategory(cursor.getString(2));
                spend.setAmount(cursor.getString(3));
                // Adding contact to list
                beanList.add(spend);
            } while (cursor.moveToNext());
        }

        // return contact list
        return beanList;
    }


}
