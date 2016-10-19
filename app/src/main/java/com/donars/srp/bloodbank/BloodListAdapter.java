package com.donars.srp.bloodbank;

import android.content.Context;
import android.database.DataSetObserver;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import android.widget.BaseAdapter;
import android.widget.TextView;

import com.donars.srp.bloodbank.model.BloodModel;

import java.util.List;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
/**
 * Created by Lakshman sai on 13-10-2016.
 */

public class BloodListAdapter extends BaseAdapter {

    List<BloodModel> myList;
    Context mCtx;
    LayoutInflater inflater;

    BloodListAdapter(Context ctx,List<BloodModel> myList) {
        this.myList = myList;
        this.mCtx = ctx;
        inflater=LayoutInflater.from(ctx);
    }
    private class ViewHolder{
        TextView tv,tv1,tv2,tv3,tv4;
    }

    @Override
    public int getCount() {
        return myList.size();
    }

    @Override
    public Object getItem(int position) {
        return myList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @NonNull
    @Override
    public View getView(int i, View v, ViewGroup parent) {
        final ViewHolder viewHolder;
        if(v==null) {
            v = inflater.inflate(R.layout.listitem,parent,false);
            viewHolder = new ViewHolder();
            viewHolder.tv = (TextView) v.findViewById(R.id.item);
            viewHolder.tv1 = (TextView) v.findViewById(R.id.item1);
            viewHolder.tv2 = (TextView) v.findViewById(R.id.item2);
            viewHolder.tv3 = (TextView) v.findViewById(R.id.item3);
            viewHolder.tv4 = (TextView) v.findViewById(R.id.item4);
            v.setTag(viewHolder);
        }
        else {
            viewHolder=(ViewHolder) v.getTag();
        }
        viewHolder.tv.setText(myList.get(i).getHop_name());
        viewHolder.tv1.setText(myList.get(i).getAddress());
        viewHolder.tv2.setText(myList.get(i).getBlood_group());
        viewHolder.tv3.setText(Integer.toString(myList.get(i).getQuantity()));
        viewHolder.tv4.setText(myList.get(i).getName());
        return v;
    }

    /*
       TextView tv = (TextView) v.findViewById(R.id.item);
        TextView tv1 = (TextView) v.findViewById(R.id.item1);
        TextView tv2 = (TextView) v.findViewById(R.id.item2);
        TextView tv3 = (TextView) v.findViewById(R.id.item3);
        TextView tv4 = (TextView) v.findViewById(R.id.item4);


     */

}
