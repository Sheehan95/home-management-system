package com.example.kamil.projectapp;

import java.util.Date;

public class Temperature {

    public Temperature(double temperature, Date date){
        this.temperature = temperature;
        this.date = date;
    }

    public double temperature;
    public Date date;

    @Override
    public String toString() {
        return "It was " + temperature + " on " + date.toString();
    }

}
