package com.example.hp.fitfeed;


        import android.content.Context;
        import android.database.Cursor;
        import android.database.sqlite.SQLiteDatabase;
        import android.database.sqlite.SQLiteOpenHelper;

        import java.util.ArrayList;


public class DbHelper1 extends SQLiteOpenHelper {
    DbHelper1 context=this;



    public static  final int DATABASE_VERSION=1;
    public static  final String DATABASE_NAME= "USER.db";
    public static final String TABLE_NAME = "User";

    public static final String COLUMN_FoodName = "FoodName";
    public static final String COLUMN_Calories = "Calories";
    public static final String COLUMN_Date = "Date";
    public static final String COLUMN_Time = "Time";

    public String sql1= "CREATE TABLE " + TABLE_NAME + "(" +
            COLUMN_FoodName + " TEXT PRIMARY KEY  , " +
            COLUMN_Calories + " INTEGER " +
            COLUMN_Date +" Text "+
            COLUMN_Time +" Text" +
            ")";



   // public DbHelper1(Context context, String name, SQLiteDatabase.CursorFactory factory, int version)
    public DbHelper1(Context context){
        super(context, DATABASE_NAME,null, DATABASE_VERSION);
        // ctx=context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(sql1);
         }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS" + TABLE_NAME);

    }


    public Cursor retrieveData(String date , String time)
    {
        SQLiteDatabase sqLiteDatabase=this.getWritableDatabase();
        String sql=" Select * from "+TABLE_NAME+" where date = '" +date+"' && time=' "+time+" '" ;
        Cursor c=sqLiteDatabase.rawQuery(sql,null);
        return c;
    }
    public static ArrayList<String> getData(String date , String time,Context context)
    {
        ArrayList<String> stringArrayList=new ArrayList<>();
        DbHelper1 dbHelper1 =new DbHelper1(context);
        Cursor c= dbHelper1.retrieveData(date, time);
        while(c.moveToNext())
        {
            stringArrayList.add(c.getString(0));
        }
        return stringArrayList;
    }
     public void addData(String food, String date, String time, int calories)
     {
         SQLiteDatabase sqLiteDatabase=this.getWritableDatabase();
         String sql=" Insert into "+TABLE_NAME+" values ( '"+food+"',"+calories+",'"+date+"','"+time+"')";
         sqLiteDatabase.execSQL(sql);

     }





}
