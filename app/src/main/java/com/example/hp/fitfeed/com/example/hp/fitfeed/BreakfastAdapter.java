package com.example.hp.fitfeed.com.example.hp.fitfeed;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.hp.fitfeed.DbHelper;
import com.example.hp.fitfeed.DbHelper1;
import com.example.hp.fitfeed.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class BreakfastAdapter extends RecyclerView.Adapter<BreakfastAdapter.MyViewHolder> {
    private LayoutInflater inflater;
    ArrayList<String> arrayListItem;
    Context context;

    public BreakfastAdapter(Context context)

    {

        this.context=context;

        inflater=LayoutInflater.from(context);

        String date = new SimpleDateFormat("dd-MM-yyyy").format(Calendar.getInstance().getTime());

       arrayListItem=DbHelper1.getData(date, "breakfast",context);

    }
    @Override
    public BreakfastAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= inflater.inflate(R.layout.list_item,parent,false);

        MyViewHolder holder= new MyViewHolder(view);

        return holder; }

    @Override
    public void onBindViewHolder(BreakfastAdapter.MyViewHolder holder, int position) {
holder.mTextView.setText(arrayListItem.get(position));
    }

    @Override
    public int getItemCount() {
        if(arrayListItem==null)

        {

            return 0;

        }

        else

            return arrayListItem.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView mTextView;
        public MyViewHolder(View itemView) {
            super(itemView);
           mTextView=(TextView) itemView.findViewById(R.id.listItemTextView);

        }
    }
}
