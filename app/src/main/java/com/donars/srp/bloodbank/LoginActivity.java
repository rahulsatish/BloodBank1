package com.donars.srp.bloodbank;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;


public class LoginActivity extends AppCompatActivity {
    String myJSON,nb="",id,name,address,res="",username,password;
    Integer la;
    EditText uname,pass;
    TextView t1;
    Button b;
    JSONArray peoples = null;
    ArrayList<String> listItems=new ArrayList<>();
    ArrayAdapter<String> adapter;
    ListView list;
    @Bind(R.id.getlocbtn) Button _getlocbtn;
    @Bind(R.id.link_signup) TextView _signupLink;
    @Bind(R.id.getbloodbtn) Button _getbloodbtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //  ColorDrawable colorDrawable = new ColorDrawable(Color.parseColor("#cb4043"));
        //  getSupportActionBar().setBackgroundDrawable(colorDrawable);
        uname=(EditText)findViewById(R.id.uname);
        pass=(EditText)findViewById(R.id.pass);
        b=(Button)findViewById(R.id.btn_login);

        ButterKnife.bind(this);
        b.setOnClickListener(new View.OnClickListener() {
                                 @Override
                                 public void onClick(View v) {
                                     // t1=(TextView)findViewById(R.id.result);
                                     username = uname.getText().toString();
                                     password = pass.getText().toString();
               /* Animation animation=new AlphaAnimation(1.0f,0.0f);
                animation.setDuration(500);
                b.startAnimation(animation);*/
                                     //Toast.makeText(getApplicationContext(),username,Toast.LENGTH_LONG).show();


                                     getData();
                                 }
                             });
            _getlocbtn.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    // Start the Signup activity
                    Intent intent1 = new Intent(getApplicationContext(), RequestBlood.class);
                    startActivity(intent1);
                }
            });
            _signupLink.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    // Start the Signup activity
                    Intent intent = new Intent(getApplicationContext(),SignupActivity.class);
                    startActivity(intent);
                }
            });
        _getbloodbtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // Start the Signup activity
                Intent intent1 = new Intent(getApplicationContext(), InsertBloodDetails.class);
                startActivity(intent1);
            }
        });
    }
    public int showList(){
        try {
            JSONObject jsonObj = new JSONObject(myJSON);
            myJSON="";
            peoples = jsonObj.getJSONArray("result");
            // Toast.makeText(getApplicationContext(),"reached json",Toast.LENGTH_LONG).show();
            //t1.setText("");
            if(peoples.length()>=1) {
                Toast.makeText(getApplicationContext(), "Success", Toast.LENGTH_LONG).show();
                Intent log=new Intent(this,MainActivity.class);
                startActivity(log);
            }
            else
                Toast.makeText(getApplicationContext(),"Failed",Toast.LENGTH_LONG).show();
            for(int i=0;i<peoples.length();i++){

                JSONArray c = peoples.getJSONArray(i);
                for(int k=0;k<c.length();k++) {
                    JSONObject m=c.getJSONObject(k);
                    if((k+3)%3==0){
                        id=m.getString("id");}
                    else if((k+3)%3==1){
                        name=m.getString("name");}
                    else{ address=m.getString("address");}
                }
                // t1.setText("id="+id+"\n"+"name="+name+"\n"+"address="+address+"\nh\ny\ny\nj\nt\nt\nr\nt\nf\ne\nt\nl\ne");
            }
            /*ListAdapter adapter = new SimpleAdapter(
                   MainActivity.this, personList, R.layout.list_item,
                    new String[]{TAG_ID,TAG_NAME,TAG_ADD}, new int[]{R.id.id, R.id.name, R.id.address}
            );
            list.setAdapter(adapter);*/
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return 0;
    }
    public void getData(){
        class GetDataJSON extends AsyncTask<String, Void, Boolean>{
            @Override
            protected Boolean doInBackground(String... params) {
                try {
                    URL url = new URL("http://lifesaver.net23.net/login.php");
                    HttpURLConnection httpURLConnection=(HttpURLConnection)url.openConnection();
                    httpURLConnection.setRequestMethod("POST");
                    httpURLConnection.setDoOutput(true);
                    httpURLConnection.setDoInput(true);
                    OutputStream outputStream=httpURLConnection.getOutputStream();
                    BufferedWriter bufferedWriter=new BufferedWriter(new OutputStreamWriter(outputStream,"UTF-8"));
                    String data= URLEncoder.encode("username","UTF-8")+"="+URLEncoder.encode(username,"UTF-8")+"&"
                            +URLEncoder.encode("password","UTF-8")+"="+URLEncoder.encode(password,"UTF-8");
                    bufferedWriter.write(data);
                    bufferedWriter.flush();
                    bufferedWriter.close();
                    outputStream.close();
                    InputStream inputStream=httpURLConnection.getInputStream();
                    BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
                    String line="";
                    Integer i=1;
                    Boolean result=false;
                    while((line=bufferedReader.readLine())!=null)
                    {
                        if(line.equalsIgnoreCase("Success")) {
                            result = true;
                            break;
                        }
                        else
                            result=false;
                        res=res+line;
                        Log.d("res",line);
                    }
                    bufferedReader.close();
                    inputStream.close();
                    httpURLConnection.disconnect();
                    return result;
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return false;
            }
            @Override
            protected void onPostExecute(Boolean te){
//                myJSON=te;
//                //t1.setText(myJSON);
//                res="";
                Log.d("resulttt",te+"");
                Toast.makeText(getApplicationContext(),"return"+te,Toast.LENGTH_LONG).show();
                 if(te) {
                    //Toast.makeText(getApplicationContext(),"Success",Toast.LENGTH_LONG).show();

                    startActivity(new Intent(LoginActivity.this, MapsActivity.class));
                     Toast.makeText(getApplicationContext(), "Success", Toast.LENGTH_LONG).show();
                    finish();
                } else {
                    Toast.makeText(getApplicationContext(), "Failure", Toast.LENGTH_LONG).show();
                 //   startActivity(new Intent(LoginActivity.this, RequestBlood.class));
                }
//                la=showList();
//                if (te != null) return te;
//                // t1.setText(te);
            }
        }
        GetDataJSON g = new GetDataJSON();
        g.execute();
    }

}
