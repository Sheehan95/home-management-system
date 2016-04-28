package com.example.kamil.projectapp;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.kamil.model.Task;

import java.util.ArrayList;
import java.util.List;


/**
 * Schedules tasks on the Raspberry Pi.
 */
public class NewScheduleActivity extends Activity {

    public final static String SPINNER_ARM_ALARM = "Arm Alarm";
    public final static String SPINNER_DISARM_ALARM = "Disarm Alarm";
    public final static String SPINNER_TURN_ON_HEATING = "Turn on Heating";
    public final static String SPINNER_TURN_OFF_HEATING = "Turn off Heating";

    private DatePicker datePicker;
    private TimePicker timePicker;
    private Spinner taskSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_schedule);

        // setting up spinner
        List<String> spinnerArray = new ArrayList<String>();
        spinnerArray.add(SPINNER_ARM_ALARM);
        spinnerArray.add(SPINNER_DISARM_ALARM);
        spinnerArray.add(SPINNER_TURN_ON_HEATING);
        spinnerArray.add(SPINNER_TURN_OFF_HEATING);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, spinnerArray);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        taskSpinner = (Spinner) findViewById(R.id.spinner_task_type);
        taskSpinner.setAdapter(adapter);

        datePicker = (DatePicker) findViewById(R.id.datePicker);
        timePicker = (TimePicker) findViewById(R.id.timePicker);


        Button buttonSchedule = (Button) findViewById(R.id.button_add_schedule);
        buttonSchedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // getting the selected task from the spinner
                String taskType = "";

                switch (taskSpinner.getSelectedItem().toString()){
                    case SPINNER_ARM_ALARM:
                        taskType = Task.TASKTYPE_ARM_ALARM;
                        break;

                    case SPINNER_DISARM_ALARM:
                        taskType = Task.TASKTYPE_DISARM_ALARM;
                        break;

                    case SPINNER_TURN_ON_HEATING:
                        taskType = Task.TASKTYPE_TURN_ON_HEATING;
                        break;

                    case SPINNER_TURN_OFF_HEATING:
                        taskType = Task.TASKTYPE_TURN_OFF_HEATING;
                        break;

                    default:
                        taskType = Task.TASKTYPE_UNDEFINED;
                        break;
                }


                // getting the selected date, placing it into a string with the correct format
                int day = datePicker.getDayOfMonth();
                int month = datePicker.getMonth() + 1;
                int year = datePicker.getYear();
                int hours = timePicker.getCurrentHour();
                int minutes = timePicker.getCurrentMinute();
                String date = "%d/%d/%d %d:%d:%d";
                date = String.format(date, day, month, year, hours, minutes, 0);

                new PostSchedule().execute(taskType, date);

            }
        });

    }

    /**
     * An Asynchronous Task that schedules a task via HTTP request.
     */
    private class PostSchedule extends AsyncTask<String, Void, Boolean> {

        @Override
        protected Boolean doInBackground(String... params) {
            HTTPRequestHandler requestHandler = HTTPRequestHandler.getInstance();

            String taskType = params[0];
            String date = params[1];

            try {
                requestHandler.postSchedule(taskType, date);
            } catch (Exception e){
                return false;
            }

            return true;

        }

        @Override
        protected void onPostExecute(Boolean success) {

            super.onPostExecute(success);

            String result = "";

            if (success){
                result = "Task Successfully Scheduled";
            }
            else {
                result = "Failed to Schedule Task. Is the date & time valid?";
            }

            Toast.makeText(getApplicationContext(), result, Toast.LENGTH_LONG).show();

            if (success){
                startActivity(new Intent(getApplicationContext(), ScheduleActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
            }

        }
    }

}
