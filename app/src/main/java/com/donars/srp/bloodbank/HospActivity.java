package com.donars.srp.bloodbank;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.donars.srp.bloodbank.fetcher.Details;
import com.donars.srp.bloodbank.model.Hospital;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HospActivity extends AppCompatActivity {
    @BindView(R.id.input_name)
    TextView _nameText;
    @BindView(R.id.input_email) TextView _emailText;
    @BindView(R.id.input_address) TextView _address;
    @BindView(R.id.input_chief_doc)
    TextView _nameCheifDoc;
    @BindView(R.id.input_phone)
    TextView _phoneNo;

    String gender,name,pass,email,myJSON,res,address,cheifdoctor,phone;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hosp);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ButterKnife.bind(this);
        init();
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(HospActivity.this,RequestBloodHosp.class);
                startActivity(intent);
            }
        });
    }
    public void init()
    {
        _nameText.setText("Hospital:"+Details.hospital.getName());
                _emailText.setText("Email:"+Details.hospital.getUsername());
        _address.setText("Address:"+Details.hospital.getAddress());
                _nameCheifDoc.setText("Chief Doctor:"+Details.hospital.getChiefdoctor());
        _phoneNo.setText("Phone no:"+Details.hospital.getPhone());
    }


}
