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


public class GetResult extends AsyncTask<URL, Integer, Object> {

    URL url;
    HttpURLConnection connection;
    StringBuffer response = new StringBuffer();


    public Object doInBackground(URL... params){

        double temperature = 0;
        Date date = new Date();
        JSONObject obj;

        try {

            //Connection
            url = new URL("http://localhost:8080/Temperature");
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String nextLine = null;

            while ((nextLine = reader.readLine()) != null) {
                response.append(nextLine);
            }


            obj = new JSONObject(response.toString());

            date = parseToDate(obj.getJSONObject("date"));
            temperature = obj.getDouble("temperature");


        } catch (MalformedURLException e) {
            System.out.println("MALFORMED URL EXCEPTION OCCURED ON: " + e.toString());
        } catch (IOException e) {
            System.out.println("IO EXCEPTION OCCURED ON: " + e.toString());
        } catch (JSONException e) {
            System.out.println("JSON EXCEPTION OCCURED ON:" + e.toString());
        }

        return new Temperature(temperature, date);
        /*String result = null;

        try {
            //not really needed127.0.0.0
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
        */
    }

   /* public Temperature getTemperature()
    {


        double temperature = 0;
        Date date = new Date();
        JSONObject obj;

        try {

            //Connection
            url = new URL("http://localhost:8080/Temperature");
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String nextLine = null;

            while ((nextLine = reader.readLine()) != null) {
                response.append(nextLine);
            }


            obj = new JSONObject(response.toString());

            date = parseToDate(obj.getJSONObject("date"));
            temperature = obj.getDouble("temperature");


        } catch (MalformedURLException e) {
            System.out.println("MALFORMED URL EXCEPTION OCCURED ON: " + e.toString());
        } catch (IOException e) {
            System.out.println("IO EXCEPTION OCCURED ON: " + e.toString());
        } catch (JSONException e) {
            System.out.println("JSON EXCEPTION OCCURED ON:" + e.toString());
        }

        return new Temperature(temperature, date);
    } */

    private Date parseToDate(JSONObject date){


        Calendar calendar = GregorianCalendar.getInstance();
        try {
            System.out.println(date);

            calendar.set(Calendar.HOUR, date.getInt("hour"));
            calendar.set(Calendar.MINUTE, date.getInt("minute"));
            calendar.set(Calendar.SECOND, date.getInt("second"));
            calendar.set(Calendar.MILLISECOND, date.getInt("microsecond"));
            calendar.set(Calendar.DAY_OF_MONTH, date.getInt("day") - 1);
            calendar.set(Calendar.MONTH, date.getInt("month") - 1);
            calendar.set(Calendar.YEAR, date.getInt("year"));

        }catch(JSONException e){
            System.out.println("JSON EXCEPTION OCCURED ON:" + e.toString());
        }
        return calendar.getTime();

    }

}
