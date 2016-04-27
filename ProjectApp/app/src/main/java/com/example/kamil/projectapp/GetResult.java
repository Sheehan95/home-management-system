package com.example.kamil.projectapp;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.TextView;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Date;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;

public class GetResult extends AsyncTask<URL, Integer, String> {

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    public String doInBackground(URL... params) {

        HttpURLConnection connection;
        StringBuffer response = new StringBuffer();

        try {
            connection = (HttpURLConnection) new URL("http://10.73.3.155:8080/Temperature").openConnection();
            connection.setRequestMethod("GET");

            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String nextLine = null;

            while ((nextLine = reader.readLine()) != null){
                response.append(nextLine);
            }


        } catch (MalformedURLException e){
            Log.e("ERROR", "MALFORMED URL EXCEPTION OCCURRED ON: " + e.toString());
        } catch (IOException e){
            Log.e("ERROR", "IO EXCEPTION OCCURRED ON: " + e.toString());
        }

        String result = "";

        try {

            JSONObject obj = new JSONObject(response.toString());
            double temperature = obj.getDouble("temp");
            result = String.format(Locale.getDefault(), "%f °C", temperature);

        } catch (JSONException e){
            result = "Failed to connect";
        }

        return result;

    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
    }

}
