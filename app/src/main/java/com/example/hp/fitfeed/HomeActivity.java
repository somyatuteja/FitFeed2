package com.example.hp.fitfeed;


import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hp.fitfeed.com.example.hp.fitfeed.CardViewAdapter;
import com.firebase.client.Firebase;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.Random;

public class HomeActivity extends AppCompatActivity {
    TextView t1, t2;
    DbHelper db;
    AutoCompleteTextView mAutoCompleteTextView;
    RecyclerView mHomeCardView;
    FirebaseAuth auth = FirebaseAuth.getInstance();
    CardViewAdapter cardViewAdapter;
    Integer[] exerciseImage = {R.drawable.walking, R.drawable.aerobics, R.drawable.cycling, R.drawable.yoga, R.drawable.swimming, R.drawable.running};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        FirebaseUser firebaseUser = auth.getCurrentUser();
        if (firebaseUser != null) {
            Log.v("HomeActivity", firebaseUser.getUid());
            DatabaseReference firebaseDatabase = FirebaseDatabase.getInstance().getReference().child("Users").child(firebaseUser.getUid());
            firebaseDatabase.addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                    String key = dataSnapshot.getKey();
                    try {
                        String value = dataSnapshot.getValue(String.class);
                        Log.v("HomeActivity", key + " : " + value);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }

                @Override
                public void onChildChanged(DataSnapshot dataSnapshot, String s) {

                }

                @Override
                public void onChildRemoved(DataSnapshot dataSnapshot) {

                }

                @Override
                public void onChildMoved(DataSnapshot dataSnapshot, String s) {

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }


        t1 = (TextView) findViewById(R.id.textView2);
        t2 = (TextView) findViewById(R.id.textView3);
        Button b = (Button) findViewById(R.id.b1);
        db = new DbHelper(getBaseContext());
        mAutoCompleteTextView = (AutoCompleteTextView) findViewById(R.id.AutoCompleteTextView1);
        Cursor cname = db.getData();

        String[] names = new String[cname.getCount() - 1];
        for (int i = 0; i < cname.getCount() - 1; i++) {
            cname.moveToNext();
            names[i] = cname.getString(0);
            Log.v("HomeActivity", names[i]);

        }
        ArrayAdapter<String> ard = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, names);
        mAutoCompleteTextView.setAdapter(ard);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.v("HomeActivity", "Button Pressed");
                String food = mAutoCompleteTextView.getText().toString();
                Log.v("HomeActivity", food);

                if (food != null) {

                    checkUpdatedFood(food);

                }

            }


        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.v("HomeActivity", "In onStart");
        mHomeCardView = (RecyclerView) findViewById(R.id.homeCardView);
        cardViewAdapter = new CardViewAdapter(getApplicationContext());
        mHomeCardView.setAdapter(cardViewAdapter);
        mHomeCardView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
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
        }
    }

    void logout() {
        SharedPreferences sharedPreferences = getSharedPreferences("LoggedInUser", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.commit();
        Context context = getApplicationContext();
        Toast.makeText(context, "Logging you out", Toast.LENGTH_LONG).show();
        auth.signOut();
        Intent intent = new Intent(getBaseContext(), MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();

    }

    void viewprofile() {
        Intent intent = getIntent();
        // String uID=intent.getStringExtra("user");
        FirebaseUser curuser = auth.getCurrentUser();
    }

    void checkUpdatedFood(String foodname) {
        Log.v("HomeActivity", "inCheck Updated Food");

        DbHelper databaseHelper = new DbHelper(getApplicationContext());
        int status = databaseHelper.getHealthyStatus(foodname);
        if (status == 1) {
            Log.v("HomeActivity", "Got 1 status");

            final Dialog dialog = new Dialog(this);
            dialog.setContentView(R.layout.health_food_alertbox);
            final Button button = (Button) findViewById(R.id.okDialogBoxOkButton);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });
            dialog.show();
        }
        if (status == 0) {
            int exerciseID = getRandomNumber();
            Dialog dialog = getUnhealthyDialog(exerciseID, foodname);
            dialog.show();
        }

    }

    int getRandomNumber() {
        Random random = new Random();
        int randomNumber = random.nextInt(6) + 1;
        return randomNumber;
    }

    Dialog getUnhealthyDialog(int exerciseId, String food) {
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.unhealthy_food_dialog_box);
        TextView mUnhealthyTextView = (TextView) findViewById(R.id.unhealthyTextView);
        TextView mAlternativeTextView = (TextView) findViewById(R.id.alternativeTextView);
        ImageView mExerciseImageView = (ImageView) findViewById(R.id.unhealthyImageView);
        DbHelper dbHelper = new DbHelper(getBaseContext());
        String exerciseName = dbHelper.getExerciseName(exerciseId);
        int exerciseCalorie = dbHelper.getExerciseCalorie(exerciseId);
        int foodcalorie = dbHelper.getCalorie(food);
        float exerciseTime = foodcalorie / exerciseCalorie;
        DecimalFormat df = new DecimalFormat("##.##");
        df.setRoundingMode(RoundingMode.DOWN);


        mUnhealthyTextView.setText("This is Not a Healthy Option. You would have to do " + exerciseName + " for " + df.format(exerciseTime));
        return dialog;

    }
}


