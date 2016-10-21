package com.example.hp.fitfeed;


        import android.content.ContentValues;
        import android.content.Context;
        import android.database.Cursor;
        import android.database.sqlite.SQLiteDatabase;
        import android.database.sqlite.SQLiteOpenHelper;


public class DbHelper1 extends SQLiteOpenHelper {
    DbHelper1 context=this;



    public static  final int DATABASE_VERSION=1;
    public static  final String DATABASE_NAME="Database.db";
    public static final String TABLE_NAME = "FoodTable";

    public static final String COLUMN_FoodName = "FoodName";
    public static final String COLUMN_Calories = "Calories";

    public String sql1= "CREATE TABLE " + TABLE_NAME + "(" +
            COLUMN_FoodName + " TEXT PRIMARY KEY  , " +
            COLUMN_Calories + " INTEGER " +
            ")";
      public String sql2="INSERT INTO FoodTable VALUES('Idli Sambhar',69)";
      public String sql3 ="INSERT INTO FoodTable VALUES('Aloo Paratha',214)";
      public String sql4="INSERT INTO FoodTable VALUES('Pav Bhaji',118)";
      public String sql5="INSERT INTO FoodTable VALUES('Paneer Paratha',265)";
      public String sql6="INSERT INTO FoodTable VALUES('Butter Chicken',109)";
      public String sql7="INSERT INTO FoodTable VALUES('Omlette',93)";






   // public DbHelper1(Context context, String name, SQLiteDatabase.CursorFactory factory, int version)
    public DbHelper1(Context context){
        super(context, DATABASE_NAME,null, DATABASE_VERSION);
        // ctx=context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(sql1);
        sqLiteDatabase.execSQL(sql2);
        sqLiteDatabase.execSQL(sql3);
        sqLiteDatabase.execSQL(sql4);
        sqLiteDatabase.execSQL(sql5);
        sqLiteDatabase.execSQL(sql6);
        sqLiteDatabase.execSQL(sql7);



    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS" + TABLE_NAME);

    }


    public Cursor retrieveData(String food)
    {
        SQLiteDatabase sqLiteDatabase=this.getWritableDatabase();
        String sql=" Select * from FoodTable where FoodName = '" +food+"'";
        Cursor c=sqLiteDatabase.rawQuery(sql,null);
        return c;
    }
     public Cursor retrieveFoodNames()
     {
         SQLiteDatabase sqLiteDatabase=this.getWritableDatabase();
         String sql=" Select FoodName from FoodTable";
         Cursor c=sqLiteDatabase.rawQuery(sql,null);
         System.out.println("Hello");
         return c;
     }





}
