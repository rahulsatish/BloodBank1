package com.donars.srp.bloodbank;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputFilter;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.donars.srp.bloodbank.fetcher.Fetcher;

public class BloodDonationActivity extends AppCompatActivity {
    private int position;
    EditText quantity;
    Button _submit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blood_donation);
        Intent intent=getIntent();
        position=intent.getIntExtra("position",-11);
        TextView tv = (TextView) findViewById(R.id.item);
        TextView tv1 = (TextView) findViewById(R.id.item1);
        TextView tv2 = (TextView)findViewById(R.id.item2);
        TextView tv3 = (TextView) findViewById(R.id.item3);
        TextView tv4 = (TextView) findViewById(R.id.item4);
        quantity=(EditText)findViewById(R.id.quantity);
        quantity.setFilters(new InputFilter[]{ new InputFilterMinMax("1", Fetcher.detailsList.get(position).getQuantity()+"")});
        _submit=(Button)findViewById(R.id.btn_submit);
        tv.setText(Fetcher.detailsList.get(position).getHop_name());
        tv1.setText(Fetcher.detailsList.get(position).getAddress());
        tv2.setText(Fetcher.detailsList.get(position).getBlood_group());
        tv3.setText(Fetcher.detailsList.get(position).getQuantity()+"");
        tv4.setText(Fetcher.detailsList.get(position).getName());
        _submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Use the AlertDialog.Builder to configure the AlertDialog.
                AlertDialog.Builder alertDialogBuilder =
                        new AlertDialog.Builder(BloodDonationActivity.this)
                                .setTitle("Success")
                                .setMessage("You Request has been accepted");
                AlertDialog alertDialog = alertDialogBuilder.show();
                alertDialog.setCanceledOnTouchOutside(true);
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
