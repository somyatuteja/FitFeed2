package com.example.hp.fitfeed;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

public class DbHelper extends SQLiteAssetHelper {
   private static String DB_NAME= "Database.db";
    private static String TableName="FoodTable";
    public DbHelper(Context context) {
        super(context,DB_NAME , null, 1);
    }
    public Cursor getData() {
        SQLiteDatabase db = getReadableDatabase();
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
        String [] sqlSelect = {"FoodName"};
        qb.setTables(TableName);
        Cursor c = qb.query(db, sqlSelect, null, null,
                null, null, null);
                c.moveToFirst();
                 return c;

    }
}
