package com.example.hp.fitfeed.com.example.hp.fitfeed;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.TextView;

import com.example.hp.fitfeed.DbHelper;
import com.example.hp.fitfeed.DbHelper1;
import com.example.hp.fitfeed.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;


public class BreakfastFragment extends Fragment {
        AutoCompleteTextView mAutoCompleteTextView;
   Button mEnterButton;
    DbHelper dbHelper;
 RecyclerView mRecyclerView;
    @Override
    public View onCreateView(LayoutInflater inflater,  ViewGroup container,  Bundle savedInstanceState) {
        View inflatedView= inflater.inflate(R.layout.fragment_breakfast, container, false);
        mRecyclerView =(RecyclerView)inflatedView.findViewById(R.id.breakfastRecyclerView);

        BreakfastAdapter breakfastAdapter=new com.example.hp.fitfeed.com.example.hp.fitfeed.BreakfastAdapter(this.getContext());

        mRecyclerView.setAdapter(breakfastAdapter);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));

        dbHelper = new DbHelper(this.getContext());
        mAutoCompleteTextView = (AutoCompleteTextView) inflatedView.findViewById(R.id.AutoCompleteTextView1);
        Cursor cname = dbHelper.getData();

        final String[] names = new String[cname.getCount() - 1];
        for (int i = 0; i < cname.getCount() - 1; i++) {
            cname.moveToNext();
            names[i] = cname.getString(0);
            Log.v("HomeActivity", names[i]);

        }
        ArrayAdapter<String> ard = new ArrayAdapter<String>(this.getContext(), android.R.layout.simple_dropdown_item_1line, names);
        mAutoCompleteTextView.setAdapter(ard);
        final Context context=this.getContext();
        mEnterButton=(Button)inflatedView.findViewById(R.id.enterBreakfastButton);
        mEnterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String foodName=mAutoCompleteTextView.getText().toString();
                DbHelper1 dbHelper1=new DbHelper1(context);
                DbHelper dbHelper=new DbHelper(context);
                String date = new SimpleDateFormat("dd-MM-yyyy").format(Calendar.getInstance().getTime());

                int calories=dbHelper.getCalorie(foodName);
                dbHelper1.addData(foodName,date,"breakfast",calories);
            }
        });
        return inflatedView;
    }

}

