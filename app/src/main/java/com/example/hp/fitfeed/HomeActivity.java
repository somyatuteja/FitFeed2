package com.example.hp.fitfeed;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.io.IOException;

public class HomeActivity extends AppCompatActivity {
    TextView t1,t2;
    DbHelper1 db;
    AutoCompleteTextView actv;
    Context context=null;
    //String str[]={"Anant","Akshay"};
    FirebaseAuth auth=FirebaseAuth.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        t1=(TextView)findViewById(R.id.textView2);
        t2=(TextView)findViewById(R.id.textView3);
        Button b=(Button)findViewById(R.id.b1);
        db=new DbHelper1(getBaseContext());
        actv=(AutoCompleteTextView)findViewById(R.id.AutoCompleteTextView1);
        Cursor cname = db.retrieveFoodNames();

         String[] names = new String[cname.getCount()];
        for(int i = 0; i < cname.getCount(); i++){
            cname.moveToNext();

            names[i] = cname.getString(0);
            Log.v("HomeActivity",names[i]);
        }
        ArrayAdapter<String> ard=new ArrayAdapter<String>(this,android.R.layout.simple_dropdown_item_1line,names);
        actv.setAdapter(ard);
        //Button mButton=(Button)findViewById(R.id.b1);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String food = actv.getText().toString();
                if (food != null) {
                    Cursor result = db.retrieveData(food);
                    if (result.getCount() == 0) {
                        Toast t = Toast.makeText(getApplicationContext(), "No such food in database", Toast.LENGTH_SHORT);
                        t.show();
                    } else {
                        String FoodName = "Food Name:\n";
                        String Calories = "Calories:\n";
                        int rows = result.getCount();
                        result.moveToFirst();
                        for (int ii = 0; ii < rows; ii++) {
                            FoodName=FoodName.concat(result.getString(0));
                            //FoodName = FoodName.concat(result.getString(result.getColumnIndex(")));
                            FoodName = FoodName.concat("\n");
                             Calories=Calories.concat(result.getString(1));
                            //Calories = Calories.concat(result.getString(result.getColumnIndex("Calories")));
                            Calories = Calories.concat("\n");
                            result.moveToNext();

                        }
                        t1.setText(FoodName);
                        t2.setText(Calories);

                    }
                }
            }

        });

           /**DbHelper dbObject=new DbHelper(context);
            try {
                dbObject.createDataBase();
            } catch (IOException e) {
                e.printStackTrace();
            }
           Cursor cursor= dbObject.getCalories(food);
            String[] from = new String[] {"Database.FoodTable.Food Name","Database.FoodTable.Calories" };
            int[] to = new int[] { R.id.TextView1, R.id.TextView2 };

            SimpleCursorAdapter adapter = new SimpleCursorAdapter(
                    getApplicationContext(), R.layout.activity_home, cursor, from, to);
            ListView list = (ListView) findViewById(R.id.ListView1);

            list.setAdapter(adapter);**/


        }


    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.main_menu,menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle presses on the action bar items
        switch (item.getItemId()) {
            case R.id.logoutMenu:
                logout();
                return true;
            case R.id.viewProfileMenu:
                viewprofile();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }}
        void logout()
    {
        SharedPreferences sharedPreferences = getSharedPreferences("LoggedInUser", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.commit();
        Context context = getApplicationContext();
        Toast toast = Toast.makeText(context, "Logging you out", Toast.LENGTH_LONG);
        toast.show();
        auth.signOut();
        Intent intent = new Intent(getBaseContext(), MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();

    }
 void viewprofile()
 {

     FirebaseUser curuser=auth.getCurrentUser();

 }
    }



