package com.example.kamil.projectapp;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.kamil.model.TaskArrayAdapter;
import com.example.kamil.model.Task;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Lists all tasks scheduled to run on the Raspberry Pi.
 */
public class ScheduleActivity extends Activity {

    private ListView listView;
    private ArrayList<Task> taskList;
    private TaskArrayAdapter arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule);

        Button newScheduleButton = (Button) findViewById(R.id.button_new_schedule);
        newScheduleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ScheduleActivity.this, NewScheduleActivity.class));
            }
        });

        listView = (ListView) findViewById(R.id.list_scheduled);

        taskList = new ArrayList<>();

        arrayAdapter = new TaskArrayAdapter(ScheduleActivity.this, R.layout.list_row_task, taskList);
        listView.setAdapter(arrayAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {

                // prompt the user for confirmation
                AlertDialog.Builder builder = new AlertDialog.Builder(ScheduleActivity.this);
                builder.setMessage("Delete this task?");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        new CancelSchedule().execute(taskList.get(position).taskId);
                    }
                });

                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

                builder.show();

            }
        });

        new GetScheduled().execute();

    }


    /**
     * An Asynchronous Task that retrieves the current scheduled tasks via HTTP request & displays
     * them within the view.
     */
    private class GetScheduled extends AsyncTask<Void, Void, JSONArray> {

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

            taskList.clear();

            try {

                for (int i = 0 ; i < jsonArray.length() ; i++){
                    JSONObject json = jsonArray.getJSONObject(i);

                    int taskId = json.getInt("task_id");
                    String taskType = json.getString("task_type");
                    Date date = parseToDate(json.getJSONObject("date"));

                    taskList.add(new Task(taskId, taskType, date.toString()));
                }

            } catch (JSONException e){
                String error = "Failed to retrieve schedule";
                Toast.makeText(getApplicationContext(), error, Toast.LENGTH_LONG).show();
            }

            arrayAdapter.notifyDataSetChanged();

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

    /**
     * An Asynchronous Task that turns the heating on or off via HTTP request.
     */
    private class CancelSchedule extends AsyncTask<Integer, Void, Void> {

        @Override
        protected Void doInBackground(Integer... params) {
            HTTPRequestHandler requestHandler = HTTPRequestHandler.getInstance();

            try {
                requestHandler.postCancelSchedule(params[0]);
            } catch (Exception e){

            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            new GetScheduled().execute();
        }
    }

}
