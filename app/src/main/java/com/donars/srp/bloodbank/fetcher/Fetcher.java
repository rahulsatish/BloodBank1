package com.donars.srp.bloodbank.fetcher;

/**
 * Created by Lakshman on 9/19/2016.
 */

import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

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

public class Fetcher {
    public static List<BloodModel> detailsList;
    String res;
    public void getData() {
        GetDataJSON g = new GetDataJSON();
        g.execute();
    }
    private class GetDataJSON extends AsyncTask<String, Void, String> {
    @Override
    protected String doInBackground(String... params) {
        try {
            URL url = new URL("http://lifesaver.net23.net/getReq.php");
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setDoInput(true);
            InputStream inputStream = httpURLConnection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "iso-8859-1"));
            String line = "", res = "";
            Integer i = 1;
            while ((line = bufferedReader.readLine()) != null) {

                res = res + line;

            }
            res = res.substring(0, res.indexOf("<"));


            Log.d("response", res);
            bufferedReader.close();
            inputStream.close();
            httpURLConnection.disconnect();
            return res;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(String te) {
        String myJSON = te;
        //t1.setText(myJSON);
        //  Log.d("tete", te);
        //  Toast.makeText(getApplicationContext(), te, Toast.LENGTH_LONG).show();

        Gson gson = new Gson();
            /*
                JSONObject jsonObject = null;
                try {
                    jsonObject = new JSONObject(te);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                JSONArray jsonArray = null;
                try {
                    jsonArray = jsonObject.getJSONArray("result");
                    Log.d("te", jsonArray.toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }


                Type listType = new TypeToken<List<BloodModel>>() {
                }.getType();
                // Type collectionType = new TypeToken<List<NavItem>>() {

                //  List<NavItem> navigation = gson.fromJson(jsonString, collectionType);

              //  posts=(List<BloodModel>)gson.fromJson(jsonArray.toString(),listType);
                // t1.setText(te);

            }*/
        Type type = new TypeToken<List<BloodModel>>() {
        }.getType();
        detailsList = gson.fromJson(te, type);

    }
}
}