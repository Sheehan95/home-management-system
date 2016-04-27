package com.example.kamil.projectapp;

import android.util.Log;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class HTTPRequestHandler {

    public final static String TASKTYPE_UNDEFINED = "undefined";
    public final static String TASKTYPE_ARM_ALARM = "arm_alarm";
    public final static String TASKTYPE_DISARM_ALARM = "disarm_alarm";
    public final static String TASKTYPE_TURN_ON_HEATING = "turn_on_heating";
    public final static String TASKTYPE_TURN_OFF_HEATING = "turn_off_heating";

    public final static String ENDPOINT_INDEX = "/";
    public final static String ENDPOINT_TEMPERATURE = "/Temperature";
    public final static String ENDPOINT_ALARM = "/Alarm";
    public final static String ENDPOINT_SCHEDULE = "/Schedule";
    public final static String ENDPOINT_SCHEDULE_CANCEL = "/Schedule/Cancel";
    public final static String ENDPOINT_TWITTER = "/Twitter";
    public final static String ENDPOINT_CAMERA = "/Camera";
    public final static String ENDPOINT_BREAKIN = "/BreakIn";

    private String domain;

    public HTTPRequestHandler(String domain){
        this.domain = domain;
    }


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

        Log.e("JSON", json.toString());
        return json;

    }

//    /**
//     * Sends a HTTP POST request to the endpoint {@link #ENDPOINT_TEMPERATURE}.
//     *
//     * @param data to be POSTed to the server
//     * @return a response from the server
//     */
//    public String postTemperature(String data){
//
//        HttpURLConnection connection;
//        StringBuffer response = new StringBuffer();
//
//        try {
//
//            connection = getConnection(ENDPOINT_TEMPERATURE);
//            connection.setRequestMethod("POST");
//            connection.setDoOutput(true);
//
//            JSONObject json = new JSONObject();
//            json.put("name",  data);
//
//            DataOutputStream output = new DataOutputStream(connection.getOutputStream());
//            output.writeBytes(json.toString());
//            output.flush();
//            output.close();
//
//            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
//            String nextLine = null;
//
//            while ((nextLine = reader.readLine()) != null){
//                response.append(nextLine);
//            }
//
//
//        } catch (MalformedURLException e){
//            System.out.println("MALFORMED URL EXCEPTION OCCURED ON: " + e.toString());
//        } catch (IOException e){
//            System.out.println("IO EXCEPTION OCCURED ON: " + e.toString());
//        }
//
//        return response.toString();
//
//    }
//
//
//
//    /**
//     * Sends a HTTP GET request to the endpoint {@link #ENDPOINT_TEMPERATURE}.
//     *
//     * @return a response from the server
//     */
//    public String getAlarmStatus(){
//
//        HttpURLConnection connection;
//        StringBuffer response = new StringBuffer();
//
//        try {
//
//            connection = getConnection(ENDPOINT_ALARM);
//            connection.setRequestMethod("GET");
//
//            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
//            String nextLine = null;
//
//            while ((nextLine = reader.readLine()) != null){
//                response.append(nextLine);
//            }
//
//
//        } catch (MalformedURLException e){
//            System.out.println("MALFORMED URL EXCEPTION OCCURED ON: " + e.toString());
//        } catch (IOException e){
//            System.out.println("IO EXCEPTION OCCURED ON: " + e.toString());
//        }
//
//        return response.toString();
//
//    }
//
//    /**
//     * Sends a HTTP POST request to the endpoint {@link #ENDPOINT_TEMPERATURE}.
//     *
//     * @param data to be POSTed to the server
//     * @return a response from the server
//     */
//    public String postAlarmStatus(boolean arm){
//
//        HttpURLConnection connection;
//        StringBuffer response = new StringBuffer();
//
//        try {
//
//            connection = getConnection(ENDPOINT_ALARM);
//            connection.setRequestMethod("POST");
//            connection.setDoOutput(true);
//
//            JSONObject json = new JSONObject();
//            json.put("arm", arm);
//
//            DataOutputStream output = new DataOutputStream(connection.getOutputStream());
//            output.writeBytes(json.toString());
//            output.flush();
//            output.close();
//
//            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
//            String nextLine = null;
//
//            while ((nextLine = reader.readLine()) != null){
//                response.append(nextLine);
//            }
//
//
//        } catch (MalformedURLException e){
//            System.out.println("MALFORMED URL EXCEPTION OCCURED ON: " + e.toString());
//        } catch (IOException e){
//            System.out.println("IO EXCEPTION OCCURED ON: " + e.toString());
//        }
//
//        return response.toString();
//
//    }
//
//
//
//    /**
//     * Sends a HTTP POST request to the endpoint {@link #ENDPOINT_TEMPERATURE}.
//     *
//     * @param data to be POSTed to the server
//     * @return a response from the server
//     */
//    public String postSchedule(String type){
//
//        HttpURLConnection connection;
//        StringBuffer response = new StringBuffer();
//
//        try {
//
//            connection = getConnection("http://localhost:8080/Schedule");
//            connection.setRequestMethod("POST");
//            connection.setDoOutput(true);
//
//            Calendar cal = Calendar.getInstance();
//            cal.setTime(new Date());
//            cal.add(Calendar.SECOND, 10);
//
//            JSONObject json = new JSONObject();
//            json.put("task_type",  type);
//            json.put("date", cal.getTime());
//
//
//            DataOutputStream output = new DataOutputStream(connection.getOutputStream());
//            output.writeBytes(json.toString());
//            output.flush();
//            output.close();
//
//            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
//            String nextLine = null;
//
//            while ((nextLine = reader.readLine()) != null){
//                response.append(nextLine);
//            }
//
//
//        } catch (MalformedURLException e){
//            System.out.println("MALFORMED URL EXCEPTION OCCURED ON: " + e.toString());
//        } catch (IOException e){
//            System.out.println("IO EXCEPTION OCCURED ON: " + e.toString());
//        }
//
//        return response.toString();
//
//    }
//
//
//
//    /**
//     * Sends a HTTP GET request to the endpoint {@link #ENDPOINT_TEMPERATURE}.
//     *
//     * @return a response from the server
//     */
//    public String getSchedule(){
//
//        HttpURLConnection connection;
//        StringBuffer response = new StringBuffer();
//
//        try {
//
//            connection = getConnection("http://localhost:8080/Schedule");
//            connection.setRequestMethod("GET");
//
//            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
//            String nextLine = null;
//
//            while ((nextLine = reader.readLine()) != null){
//                response.append(nextLine);
//            }
//
//
//        } catch (MalformedURLException e){
//            System.out.println("MALFORMED URL EXCEPTION OCCURED ON: " + e.toString());
//        } catch (IOException e){
//            System.out.println("IO EXCEPTION OCCURED ON: " + e.toString());
//        }
//
//        return response.toString();
//
//    }
//
//

    /**
     * Parses a JSON object to an object of type Date.
     *
     * @param date a JSON date object to parse
     * @return the date described by the JSON, or null if parsing fails
     */
    private Date parseToDate(JSONObject date){

        Calendar calendar = null;

        try {

            calendar = GregorianCalendar.getInstance();
            calendar.set(Calendar.HOUR, date.getInt("hour"));
            calendar.set(Calendar.MINUTE, date.getInt("minute"));
            calendar.set(Calendar.SECOND, date.getInt("second"));
            calendar.set(Calendar.MILLISECOND, date.getInt("microsecond"));
            calendar.set(Calendar.DAY_OF_MONTH, date.getInt("day") - 1);
            calendar.set(Calendar.MONTH, date.getInt("month") - 1);
            calendar.set(Calendar.YEAR, date.getInt("year"));

        } catch (JSONException e){
            return null;
        }

        return calendar.getTime();

    }

}
