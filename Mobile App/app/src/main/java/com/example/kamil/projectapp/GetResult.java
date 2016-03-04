package com.example.kamil.projectapp;

import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;


public class GetResult extends AsyncTask<URL, Integer, String> {


    protected String doInBackground(URL... params) {

        String result = null;

        try {
            //not really needed
            URL url = new URL("http://172.20.10.5:8080/temperature");

            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("User-Agent", "Mozilla/5.0");
            //BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));

            result = connection.getHeaderField("temperature");
           // result = in.readLine();
           // in.close();
        } catch (MalformedURLException e) {
            result = "Malformed URL";
        } catch (IOException e) {
            result = "IO Error";
        }

        return result;

    }
}
