package com.example.hp.fitfeed;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.api.model.StringList;

import java.util.ArrayList;
import java.util.Iterator;

public class ViewProfileActivity extends AppCompatActivity {
  ArrayList<String> info=new ArrayList<String>();
    ArrayList<String> info1=new ArrayList<String>();
    TextView mTextView;
    String uname;
    ListView mListView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_profile);
        FirebaseAuth auth=FirebaseAuth.getInstance();
       final Firebase mref =new Firebase("https://fitfeed-7ae6a.firebaseio.com/");
        final FirebaseUser curuser=auth.getCurrentUser();

        Firebase child=mref.child("Users");
        uname=getIntent().getStringExtra("email");
        uname="Email  :  "+uname;
       // mTextView=(TextView)findViewById(R.id.infoTextView);
        mListView=(ListView)findViewById(R.id.infoListView);
        final ArrayAdapter<String> adapter=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,info1);
        mListView.setAdapter(adapter);

        child.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Iterable<DataSnapshot> itr=dataSnapshot.getChildren();
                Iterator i=itr.iterator();

                while((i.hasNext()))
                {
                    boolean flag=true;
                    DataSnapshot dataSnapshot1=(DataSnapshot)i.next();
                    Iterable<DataSnapshot> itr1=dataSnapshot1.getChildren();
                    Iterator j=itr1.iterator();
                    while(j.hasNext())
                    {
                        DataSnapshot dataSnapshot2=(DataSnapshot)j.next();
                        String s1=dataSnapshot2.getValue(String.class);
                        String s2=dataSnapshot2.getKey();
                        String s3;
                        s3=s2+"  :  "+s1;
                        info.add(s3);

                        if(s3.equals(uname))
                        {
                            flag=false;
                        }
                    }
                    if(flag==true)
                    {
                        Log.v("ViewP","clearing");
                        info.clear();
                    }

                   else
                    {
                        info1=info;

                    }
                    adapter.notifyDataSetChanged();
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
            public void onCancelled(FirebaseError firebaseError) {

            }
        });


    }
}
