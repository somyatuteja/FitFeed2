package com.example.hp.fitfeed.com.example.hp.fitfeed;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.example.hp.fitfeed.R;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by HP on 11/3/2016.
 */
public class CardViewAdapter extends RecyclerView.Adapter<CardViewAdapter.ListViewHolder> {
    private LayoutInflater inflater;
    ArrayList<String> arrayListItem ;
    Integer[] drawableArray={R.drawable.breakfast,R.drawable.lunch,R.drawable.snacks,R.drawable.dinner,R.drawable.exercise};
    Context context;
    public CardViewAdapter(Context context)
    {
        Log.v("CardViewAdapter", "In adapter");

        this.context=context;
        inflater=LayoutInflater.from(context);
        arrayListItem =new ArrayList<String>();
        arrayListItem.add("BreakFast");
        arrayListItem.add("Lunch");
        arrayListItem.add("Snacks");
        arrayListItem.add("Dinner");
        arrayListItem.add("Exercise");

    }
    @Override
    public ListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Log.v("CardViewAdapter", "In OnCreateViewHolder");
        View view= inflater.inflate(R.layout.card_view,parent,false);
        ListViewHolder holder= new ListViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(CardViewAdapter.ListViewHolder holder, int position) {
        Log.v("CardViewAdapter", "In OnBind");
        String listItem=arrayListItem.get(position);
        Integer listImage=drawableArray[position];
        holder.mTextView.setText(listItem);
        holder.mImageView.setImageResource(listImage);
        holder.mImageView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);

    }

    @Override
    public int getItemCount() {

            return 5;

    }
    class  ListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        TextView mTextView;
       ImageView mImageView;
        public ListViewHolder(View itemView) {
            super(itemView);

            mTextView=(TextView) itemView.findViewById(R.id.cardTextView);
            mImageView=(ImageView) itemView.findViewById(R.id.cardImageView);


        }
        @Override
        public void onClick(View v) {
           /* Intent intent = new Intent(context, Description_Activity.class);
            Log.v("MainActivity","inOnClick");
            ToDoListItem listItem = arrayListItem.get(getPosition());
            intent.putExtra("ID",listItem.id);
            context.startActivity(intent);*/
        }
    }
}