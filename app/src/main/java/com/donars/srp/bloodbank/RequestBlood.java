package com.donars.srp.bloodbank;

import android.app.ListActivity;
import android.content.Intent;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import com.donars.srp.bloodbank.fetcher.Fetcher;
import com.donars.srp.bloodbank.model.BloodModel;
import java.util.List;

/**
 * Created by Rahul Satish on 19-08-2016.
 */


public class RequestBlood extends ListActivity {
    //  @Bind(R.id.button_loc) TextView _buttonloc;
    protected LocationManager locationManager;
    protected LocationListener locationListener;
    public ListView lV;

    BloodListAdapter adapter;
    List<BloodModel> posts;


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hospital_list);
        //ButterKnife.bind(this);

        lV = (ListView) findViewById(android.R.id.list);

        adapter = new BloodListAdapter(getApplicationContext(),Fetcher.detailsList);
        lV.setAdapter(adapter);
        lV=getListView();
       lV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
           @Override
           public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
               System.out.printf("Succs\n"+position);
               Intent intent=new Intent(RequestBlood.this,BloodDonationActivity.class);
               intent.putExtra("position",position);
               startActivity(intent);
           }
       });
    }


}

