package com.donars.srp.bloodbank;

import android.content.Context;
import android.database.DataSetObserver;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.donars.srp.bloodbank.model.BloodModel;

import java.util.List;

/**
 * Created by Rahul Satish on 24-08-2016.
 */

public class BloodListAdapter implements ListAdapter {

    List<BloodModel> myList;
    Context mCtx;

    BloodListAdapter(Context ctx, List<BloodModel> myList) {
        this.myList = myList;
        this.mCtx = ctx;
    }

    @Override
    public boolean areAllItemsEnabled() {
        return false;
    }

    @Override
    public boolean isEnabled(int i) {
        return false;
    }

    @Override
    public void registerDataSetObserver(DataSetObserver dataSetObserver) {

    }

    @Override
    public void unregisterDataSetObserver(DataSetObserver dataSetObserver) {

    }

    @Override
    public int getCount() {
        return myList.size();
    }

    @Override
    public Object getItem(int i) {
        return myList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater infl = (LayoutInflater) mCtx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = infl.inflate(R.layout.listitem, viewGroup, false);
        TextView tv = (TextView) v.findViewById(R.id.item);
        TextView tv1 = (TextView) v.findViewById(R.id.item1);
        TextView tv2 = (TextView) v.findViewById(R.id.item2);
        TextView tv3 = (TextView) v.findViewById(R.id.item3);
        TextView tv4 = (TextView) v.findViewById(R.id.item4);

        tv.setText(myList.get(i).getHop_name());
        tv1.setText(myList.get(i).getAddress());
        tv2.setText(myList.get(i).getBlood_group());
        tv3.setText(myList.get(i).getQuantity()+"");
        tv4.setText(myList.get(i).getName());

        return v;
    }

    @Override
    public int getItemViewType(int i) {
        return 1;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }
}
