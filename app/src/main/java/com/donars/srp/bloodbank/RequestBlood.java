
package com.donars.srp.bloodbank;

import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import com.donars.srp.bloodbank.fetcher.Fetcher;
import com.donars.srp.bloodbank.model.BloodModel;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import butterknife.ButterKnife;

/**
 * Created by Rahul Satish on 19-08-2016.
 */


public class RequestBlood extends AppCompatActivity {
    //  @Bind(R.id.button_loc) TextView _buttonloc;
    protected LocationManager locationManager;
    protected LocationListener locationListener;
    public ListView lV;

    BloodListAdapter adapter;
    List<BloodModel> posts;


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hospital_list);
        ButterKnife.bind(this);

//        _buttonloc.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                LocationManager lm = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
//                Location location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
//                double longitude = location.getLongitude();
//                double latitude = location.getLatitude();
//
//                Toast.makeText(getBaseContext(),Double.toString(longitude),Toast.LENGTH_LONG).show();
//            }
//        });
        lV = (ListView) findViewById(R.id.listview);

        adapter = new BloodListAdapter(getApplicationContext(), Fetcher.detailsList);
        lV.setAdapter(adapter);
    }


        }

