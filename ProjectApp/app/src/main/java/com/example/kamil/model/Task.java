package com.example.kamil.model;

public class Task {

    public final static String TASKTYPE_UNDEFINED = "undefined";
    public final static String TASKTYPE_ARM_ALARM = "arm_alarm";
    public final static String TASKTYPE_DISARM_ALARM = "disarm_alarm";
    public final static String TASKTYPE_TURN_ON_HEATING = "turn_on_heating";
    public final static String TASKTYPE_TURN_OFF_HEATING = "turn_off_heating";

    public int taskId;
    public String taskType;
    public String date;

    /**
     * Defines a task of a certain type to execute at a given date.
     *
     * @param taskId the ID of the task
     * @param taskType the type of task
     * @param date the date the task will execute on in the format DD/MM/YYYY HH:MM:SS
     */
    public Task(int taskId, String taskType, String date){

        switch (taskType){

            case TASKTYPE_ARM_ALARM:
                this.taskType = "Arm Alarm";
                break;

            case TASKTYPE_DISARM_ALARM:
                this.taskType = "Disarm Alarm";
                break;

            case TASKTYPE_TURN_ON_HEATING:
                this.taskType = "Turn on Heating";
                break;

            case TASKTYPE_TURN_OFF_HEATING:
                this.taskType = "Turn off Heating";
                break;

            default:
                this.taskType = TASKTYPE_UNDEFINED;
                break;

        }

        this.taskId = taskId;
        this.date = date;

    }

    @Override
    public String toString() {
        return String.format("%d] %s is set to run at %s", taskId, taskType, date);
    }
}
