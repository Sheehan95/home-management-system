package com.example.kamil.projectapp;

import android.os.AsyncTask;

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

public class GetTemperature extends AsyncTask<URL, Integer, String> {

    public String doInBackground(URL... params) {
        double temperature = Math.random();
        return temperature + " °C";
    }

}
