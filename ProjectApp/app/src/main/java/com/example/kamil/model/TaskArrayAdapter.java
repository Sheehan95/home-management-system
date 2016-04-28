package com.example.kamil.model;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.kamil.projectapp.R;

import java.util.ArrayList;

/**
 * Adapter class for an ArrayList of type Task.
 */
public class TaskArrayAdapter extends ArrayAdapter<Task> {

    private Activity activity;
    private ArrayList<Task> taskList;
    private int layoutResourceId;

    /**
     * Constructor.
     *
     * @param activity the calling activity
     * @param textViewResourceId the layout resource
     * @param taskList the ArrayList of Task objects to display
     */
    public TaskArrayAdapter(Activity activity, int textViewResourceId, ArrayList<Task> taskList){
        super(activity, textViewResourceId, taskList);

        this.layoutResourceId = textViewResourceId;
        this.activity = activity;
        this.taskList = taskList;

    }

    /**
     * Inner class to hold the view elements to display.
     */
    public static class ViewHolder {
        public TextView displayTaskName;
        public TextView displayDate;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View row = convertView;
        ViewHolder holder = null;

        if (row == null){

            LayoutInflater inflater = activity.getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);

            holder = new ViewHolder();
            holder.displayTaskName = (TextView) row.findViewById(R.id.task_name);
            holder.displayDate = (TextView) row.findViewById(R.id.task_date);

            row.setTag(holder);
        }
        else {
            holder = (ViewHolder) row.getTag();
        }

        Task task = taskList.get(position);
        holder.displayTaskName.setText(task.taskType);
        holder.displayDate.setText(task.date);

        return row;

    }

}
