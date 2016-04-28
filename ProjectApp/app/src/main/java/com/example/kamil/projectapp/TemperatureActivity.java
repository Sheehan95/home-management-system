package com.example.kamil.projectapp;

import android.app.Activity;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.kamil.model.Task;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Displays the current temperature as returned by the Raspberry Pi sensors.
 */
public class TemperatureActivity extends Activity {

    private Timer timer;
    private boolean heatingOn;
    private static final int TIMER_INTERVAL = 5000;

    // GUI element references
    private TextView temperatureResult;
    private TextView heatingResult;
    private TextView heatingScheduledResult;
    private TextView heatingScheduledLabel;

    private Button toggleHeatingButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_temperature);

        // initializing GUI elements
        temperatureResult = (TextView) findViewById(R.id.result_temperature);
        heatingResult = (TextView) findViewById(R.id.result_heating);
        heatingScheduledResult = (TextView) findViewById(R.id.result_scheduled_heating);
        heatingScheduledLabel = (TextView) findViewById(R.id.label_heating_scheduled);

        toggleHeatingButton = (Button) findViewById(R.id.button_toggle_heating);

        new GetScheduledHeating().execute();

        toggleHeatingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new PostTemperature().execute();
            }
        });

    }

    @Override
    protected void onStart() {

        super.onStart();

        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        new GetTemperature().execute();
                    }
                });
            }
        }, 0, TIMER_INTERVAL);

    }

    @Override
    protected void onStop() {
        super.onStop();
        timer.cancel();
        timer.purge();
    }


    /**
     * An Asynchronous Task that turns the heating on or off via HTTP request.
     */
    private class PostTemperature extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... params) {
            HTTPRequestHandler requestHandler = HTTPRequestHandler.getInstance();
            requestHandler.postTemperature(!heatingOn);
            return null;
        }

    }

    /**
     * An Asynchronous Task that retrieves the current temperature via HTTP request & displays it
     * in the view.
     */
    private class GetTemperature extends AsyncTask<Void, Void, JSONObject> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected JSONObject doInBackground(Void... params) {
            HTTPRequestHandler req = HTTPRequestHandler.getInstance();
            return req.getTemperature();
        }

        @Override
        protected void onPostExecute(JSONObject json) {
            try {

                double temperature = json.getDouble("temperature");

                if (temperature < 20){
                    temperatureResult.setTextColor(Color.BLUE);
                }
                else if (temperature > 60){
                    temperatureResult.setTextColor(Color.RED);
                }
                else {
                    temperatureResult.setTextColor(Color.BLACK);
                }

                temperatureResult.setText(Double.toString(temperature) + " °C");


                heatingOn = json.getBoolean("heating");

                if (heatingOn){
                    heatingResult.setText(R.string.label_heating_on);
                    toggleHeatingButton.setText(R.string.button_turn_off_heating);
                }
                else {
                    heatingResult.setText(R.string.label_heating_off);
                    toggleHeatingButton.setText(R.string.button_turn_on_heating);
                }

            } catch (JSONException e){
                temperatureResult.setText(R.string.error_generic);
            }
        }

    }


    /**
     * An Asynchronous Task that retrieves the current scheduled times heating will be turned on
     * via HTTP request & displays itin the view.
     */
    private class GetScheduledHeating extends AsyncTask<Void, Void, JSONArray> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected JSONArray doInBackground(Void... params) {
            HTTPRequestHandler req = HTTPRequestHandler.getInstance();
            return req.getScheduled();
        }

        @Override
        protected void onPostExecute(JSONArray jsonArray) {
            try {

                for (int i = 0 ; i < jsonArray.length() ; i++){
                    JSONObject json = jsonArray.getJSONObject(i);

                    if (json.getString("task_type").equals(Task.TASKTYPE_TURN_ON_HEATING)){
                        heatingScheduledLabel.setText(R.string.label_heating_scheduled);
                        Date date = parseToDate(json.getJSONObject("date"));
                        heatingScheduledResult.setText(date.toString());
                        return;
                    }
                }

            } catch (JSONException e){
                temperatureResult.setText(R.string.error_generic);
            }
        }

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
                calendar.set(Calendar.HOUR_OF_DAY, date.getInt("hour"));
                calendar.set(Calendar.MINUTE, date.getInt("minute"));
                calendar.set(Calendar.SECOND, date.getInt("second"));
                calendar.set(Calendar.MILLISECOND, date.getInt("microsecond"));
                calendar.set(Calendar.DAY_OF_MONTH, date.getInt("day"));
                calendar.set(Calendar.MONTH, date.getInt("month") - 1);
                calendar.set(Calendar.YEAR, date.getInt("year"));

            } catch (JSONException e){
                return null;
            }

            return calendar.getTime();

        }

    }

}










