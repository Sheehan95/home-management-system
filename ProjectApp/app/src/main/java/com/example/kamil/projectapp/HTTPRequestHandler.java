package com.example.kamil.projectapp;

import android.util.Log;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Handles all HTTP requests being made from the App.
 */
public class HTTPRequestHandler {

    public final static String ENDPOINT_INDEX = "/";
    public final static String ENDPOINT_TEMPERATURE = "/Temperature";
    public final static String ENDPOINT_ALARM = "/Alarm";
    public final static String ENDPOINT_SCHEDULE = "/Schedule";
    public final static String ENDPOINT_SCHEDULE_CANCEL = "/Schedule/Cancel";
    public final static String ENDPOINT_TWITTER = "/Twitter";
    public final static String ENDPOINT_CAMERA = "/Camera";
    public final static String ENDPOINT_BREAKIN = "/BreakIn";


    private static String domain;
    private static HTTPRequestHandler instance = null;


    private HTTPRequestHandler(){}

    public static HTTPRequestHandler getInstance(){
        if (instance == null){
            instance = new HTTPRequestHandler();
        }
        return instance;
    }


    /**
     *
     *
     * @param domain
     */
    public static void setDomain(String domain){
        HTTPRequestHandler.domain = domain;
    }


    /**
     * Gets the current temperature from the Raspberry Pi.
     *
     * @return the current temperature
     */
    public JSONObject getTemperature(){

        JSONObject json = null;
        HttpURLConnection connection = null;
        StringBuffer response = new StringBuffer();

        try {

            connection = (HttpURLConnection) new URL(String.format("http://%s:8080%s", domain, ENDPOINT_TEMPERATURE)).openConnection();
            connection.setRequestMethod("GET");

            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String nextLine = null;

            while ((nextLine = reader.readLine()) != null){
                response.append(nextLine);
            }

            json = new JSONObject(response.toString());

        } catch (MalformedURLException e){
            Log.e("ERROR", "MALFORMED URL EXCEPTION OCCURRED ON: " + e.toString());
        } catch (IOException e){
            Log.e("ERROR", "IO EXCEPTION OCCURRED ON: " + e.toString());
        } catch (JSONException e){
            Log.e("ERROR", "JSON EXCEPTION ON: " + e.toString());
        }

        return json;

    }

    /**
     * Turns the heating on or off on the Raspberry Pi.
     *
     * @param heatingOn true to turn the heating on, false to turn it off
     */
    public void postTemperature(boolean heatingOn){

        JSONObject json = null;
        HttpURLConnection connection = null;
        StringBuffer response = new StringBuffer();

        try {

            connection = (HttpURLConnection) new URL(String.format("http://%s:8080%s", domain, ENDPOINT_TEMPERATURE)).openConnection();
            connection.setRequestMethod("POST");
            connection.setDoOutput(true);

            json = new JSONObject();
            json.put("heating_on",  heatingOn);

            DataOutputStream output = new DataOutputStream(connection.getOutputStream());
            output.writeBytes(json.toString());
            output.flush();
            output.close();

            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String nextLine = null;

            while ((nextLine = reader.readLine()) != null){
                response.append(nextLine);
            }

        } catch (MalformedURLException e){
            Log.e("ERROR", "MALFORMED URL EXCEPTION OCCURRED ON: " + e.toString());
        } catch (IOException e){
            Log.e("ERROR", "IO EXCEPTION OCCURRED ON: " + e.toString());
        } catch (JSONException e){
            Log.e("ERROR", "JSON EXCEPTION ON: " + e.toString());
        }

    }


    /**
     * Gets a list of all scheduled tasks from the Raspberry Pi.
     *
     * @return a list of scheduled tasks
     */
    public JSONArray getScheduled(){

        JSONArray json = null;
        HttpURLConnection connection = null;
        StringBuffer response = new StringBuffer();

        try {

            connection = (HttpURLConnection) new URL(String.format("http://%s:8080%s", domain, ENDPOINT_SCHEDULE)).openConnection();
            connection.setRequestMethod("GET");

            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String nextLine = null;

            while ((nextLine = reader.readLine()) != null){
                response.append(nextLine);
            }

            json = new JSONArray(response.toString());

        } catch (MalformedURLException e){
            Log.e("ERROR", "MALFORMED URL EXCEPTION OCCURRED ON: " + e.toString());
        } catch (IOException e){
            Log.e("ERROR", "IO EXCEPTION OCCURRED ON: " + e.toString());
        } catch (JSONException e){
            Log.e("ERROR", "JSON EXCEPTION ON: " + e.toString());
        }

        return json;

    }

    /**
     * Adds a new task that's scheduled to run on the Raspberry Pi.
     *
     * @param taskType the type of task to run
     * @param date for the task to execute in the format DD/MM/YYYY HH:MM:SS
     * @throws IOException if an error occurs while connecting to the Raspberry Pi
     * @throws JSONException if there's an error in parsing the result
     */
    public void postSchedule(String taskType, String date) throws IOException, JSONException {

        JSONObject json = null;
        HttpURLConnection connection = null;
        StringBuffer response = new StringBuffer();

        connection = (HttpURLConnection) new URL(String.format("http://%s:8080%s", domain, ENDPOINT_SCHEDULE)).openConnection();
        connection.setRequestMethod("POST");
        connection.setDoOutput(true);

        json = new JSONObject();
        json.put("task_type", taskType);
        json.put("date", date);

        DataOutputStream output = new DataOutputStream(connection.getOutputStream());
        output.writeBytes(json.toString());
        output.flush();
        output.close();

        BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String nextLine = null;

        while ((nextLine = reader.readLine()) != null){
            response.append(nextLine);
        }

    }

    /**
     * Cancels a task that's scheduled to run on the Raspberry Pi.
     *
     * @param taskId the id of the task to cancel
     * @throws IOException if an error occurs while connecting to the Raspberry Pi
     * @throws JSONException if there's an error in parsing the result
     */
    public void postCancelSchedule(int taskId) throws IOException, JSONException {

        HttpURLConnection connection = null;
        StringBuffer response = new StringBuffer();

        connection = (HttpURLConnection) new URL(String.format("http://%s:8080%s/%d", domain, ENDPOINT_SCHEDULE_CANCEL, taskId)).openConnection();
        connection.setRequestMethod("POST");
        connection.setDoOutput(true);

        BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String nextLine = null;

        while ((nextLine = reader.readLine()) != null){
            response.append(nextLine);
        }

    }


    /**
     * Gets the current Twitter Handle stored in the Raspberry Pi.
     *
     * @return a Twitter Handle
     */
    public String getTwitterHandle(){

        String result = "";
        HttpURLConnection connection = null;
        StringBuffer response = new StringBuffer();

        try {

            connection = (HttpURLConnection) new URL(String.format("http://%s:8080%s", domain, ENDPOINT_TWITTER)).openConnection();
            connection.setRequestMethod("GET");

            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String nextLine = null;

            while ((nextLine = reader.readLine()) != null){
                response.append(nextLine);
            }

            result = response.toString();

        } catch (MalformedURLException e){
            Log.e("ERROR", "MALFORMED URL EXCEPTION OCCURRED ON: " + e.toString());
        } catch (IOException e){
            Log.e("ERROR", "IO EXCEPTION OCCURRED ON: " + e.toString());
        }

        return result;

    }

    /**
     * Sets a new Twitter Handle on the Raspberry Pi.
     *
     * @param twitterHandle the new Twitter Handle
     * @throws IOException if an error occurs while connecting to the Raspberry Pi
     * @throws JSONException if there's an error in parsing the result
     */
    public void postTwitterHandle(String twitterHandle) throws IOException, JSONException {

        HttpURLConnection connection = null;
        StringBuffer response = new StringBuffer();

        connection = (HttpURLConnection) new URL(String.format("http://%s:8080%s/%s", domain, ENDPOINT_TWITTER, twitterHandle)).openConnection();
        connection.setRequestMethod("POST");
        connection.setDoOutput(true);

        BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String nextLine = null;

        while ((nextLine = reader.readLine()) != null){
            response.append(nextLine);
        }

    }

}
