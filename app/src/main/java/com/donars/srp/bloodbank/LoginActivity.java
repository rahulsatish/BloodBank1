
package com.donars.srp.bloodbank;
import android.app.ProgressDialog;
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

import com.donars.srp.bloodbank.fetcher.Details;
import com.donars.srp.bloodbank.model.Hospital;
import com.donars.srp.bloodbank.model.User;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

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


import butterknife.BindView;
import butterknife.ButterKnife;


public class LoginActivity extends AppCompatActivity {
    String myJSON,nb="",id,name,address,res="",username,password;
    Integer la;
    EditText uname,pass;
    ProgressDialog progress;
    TextView t1;
    Button b;
    JSONArray peoples = null;
    ArrayList<String> listItems=new ArrayList<>();

    ArrayAdapter<String> adapter;
    ListView list;
    @BindView(R.id.link_signup) TextView _signupLink;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //  ColorDrawable colorDrawable = new ColorDrawable(Color.parseColor("#cb4043"));
        //  getSupportActionBar().setBackgroundDrawable(colorDrawable);
        uname=(EditText)findViewById(R.id.uname);
        pass=(EditText)findViewById(R.id.pass);
        b=(Button)findViewById(R.id.btn_login);
        progress=new ProgressDialog(LoginActivity.this);
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

        _signupLink.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // Start the Signup activity
                Intent intent = new Intent(getApplicationContext(),SignupActivity.class);
                startActivity(intent);
            }
        });

    }

    public void getData(){
        class GetDataJSON extends AsyncTask<String, Void, Boolean>{
            @Override
            protected void onPreExecute() {
                res="";
                progress.setMessage("Logging in");
                progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                progress.setIndeterminate(true);
                progress.show();
            }

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
                        res=res+line;
                        Log.d("res",line);
                    }
                    res = res.substring(0, res.indexOf("<"));
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
                Log.d("resulttt",res+(res.equalsIgnoreCase("Failure"))+"");
         //       Toast.makeText(getApplicationContext(),"return"+te,Toast.LENGTH_LONG).show();
                if(res.length()>0 && !(res.equalsIgnoreCase("Failure"))) {
                    //Toast.makeText(getApplicationContext(),"Success",Toast.LENGTH_LONG).show();
                    try {
                        Gson gson = new Gson();
                        Details.user = gson.fromJson(res, User.class);
                        System.out.println(gson.toJson(Details.user));
                    }catch (Exception e){e.printStackTrace();}

                    startActivity(new Intent(LoginActivity.this, MapsActivity.class));
                    Toast.makeText(getApplicationContext(), "Success", Toast.LENGTH_LONG).show();
                    finish();
                } else {
                    Toast.makeText(getApplicationContext(), "Failure", Toast.LENGTH_LONG).show();
                    b.setError("Invalid Username or Password");
                    //   startActivity(new Intent(LoginActivity.this, RequestBlood.class));
                }
                progress.dismiss();
//                la=showList();
//                if (te != null) return te;
//                // t1.setText(te);
            }
        }
        GetDataJSON g = new GetDataJSON();
        g.execute();
    }
}
