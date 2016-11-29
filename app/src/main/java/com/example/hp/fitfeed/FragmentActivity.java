package com.example.hp.fitfeed;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.hp.fitfeed.com.example.hp.fitfeed.BreakfastFragment;
import com.example.hp.fitfeed.com.example.hp.fitfeed.DinnerFragment;
import com.example.hp.fitfeed.com.example.hp.fitfeed.LunchFragment;
import com.example.hp.fitfeed.com.example.hp.fitfeed.SnacksFragment;


public class FragmentActivity extends AppCompatActivity {

    ViewPager mviewPager=null;
    String cardSelected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment);
        mviewPager= (ViewPager)findViewById(R.id.pager);
        FragmentManager fragmentManager=getSupportFragmentManager();
       cardSelected=getIntent().getStringExtra("ID");
        mviewPager.setAdapter(new FragmentStatePagerAdapter(fragmentManager){
            @Override
            public Fragment getItem(int position) {
                Log.v("FragmentActivity",Integer.toString(position));
                Fragment fragment=null;
                if(position==0){
                    fragment=new BreakfastFragment();
                }
                if(position==1){
                    fragment=new LunchFragment();
                }
                if(position==2){
                    fragment=new SnacksFragment();
                }
                if(position==3){
                    fragment=new DinnerFragment();
                }

                return fragment;
            }

            @Override
            public int getCount() {
                return 4;
            }
        });

        if(cardSelected.equals("Breakfast")){
                Log.v("FragmentActivity","Pressed Breakfast");
                mviewPager.setCurrentItem(0);
            }
        if(cardSelected.equals("lunch")){
            mviewPager.setCurrentItem(1);
        } if(cardSelected.equals("snacks")){
            mviewPager.setCurrentItem(2);
        } if(cardSelected.equals("dinner")){
            mviewPager.setCurrentItem(3);
        }

    }
}
