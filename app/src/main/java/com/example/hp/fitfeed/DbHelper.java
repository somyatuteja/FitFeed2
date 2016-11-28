package com.example.hp.fitfeed;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.util.Log;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

public class DbHelper extends SQLiteAssetHelper {
   private static String DB_NAME= "Database.db";
    private static String TableName="FoodTable";
    public DbHelper(Context context) {
        super(context,DB_NAME , null, 1);
    }
    public Cursor getData() {
        SQLiteDatabase readableDatabase = getReadableDatabase();
        SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();
        String [] sqlSelect = {"FoodName"};
        queryBuilder.setTables(TableName);
        Cursor c = queryBuilder.query(readableDatabase, sqlSelect, null, null,
                null, null, null);
                c.moveToFirst();
                 return c;

    }
    public  int getCalorie(String foodname)
    {
        String [] sqlSelect={"Calorie"};
        SQLiteDatabase readableDatabase = getReadableDatabase();
        SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();
        queryBuilder.setTables(TableName);
        queryBuilder.appendWhere("foodname = '"+foodname+"'");
        Cursor c = queryBuilder.query(readableDatabase, sqlSelect, null, null,
                null, null, null);
        c.moveToFirst();
        return c.getInt(1);
    }
    public  int getHealthyStatus(String foodname)
    {
        Log.v("HomeActivity","got in function");

        String [] sqlSelect={"healthy"};
        SQLiteDatabase readableDatabase = getReadableDatabase();
        SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();
        queryBuilder.setTables(TableName);
        queryBuilder.appendWhere("foodname = '"+foodname+"'");
        Log.v("HomeActivity","got query");

        Cursor c = queryBuilder.query(readableDatabase, sqlSelect, null, null,
                null, null, null);
        c.moveToFirst();
        Log.v("HomeActivity","Ran query");
        return c.getInt(0);
    }
    public  String getAlternative(String foodname)
    {
        String [] sqlSelect={"healthy"};
        SQLiteDatabase readableDatabase = getReadableDatabase();
        SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();
        queryBuilder.setTables(TableName);
        queryBuilder.appendWhere("foodname = '"+foodname+"'");
        Cursor c = queryBuilder.query(readableDatabase, sqlSelect, null, null,
                null, null, null);
        c.moveToFirst();
        return c.getString(1);
    }
}
