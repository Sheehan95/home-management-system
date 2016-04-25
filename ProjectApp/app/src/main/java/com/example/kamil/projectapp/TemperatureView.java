package com.example.kamil.projectapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.ExecutionException;

public class TemperatureView extends Activity {

    private Button getData;
    private Button backButton;
    private TextView result;
    private Object res;
    private Temperature t1;
    private GetResult test;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_temperature);

        getData = (Button)findViewById(R.id.getData);
        result = (TextView)findViewById(R.id.result);

        //Get data Button
        getData.setOnClickListener(new View.OnClickListener()
        {

            public void onClick(View v)
            {

               /* URL url = null; */

               /* try {
                    url = new URL("http://172.20.10.5:8080/temperature");
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }*/

                test = new GetResult();
                test.execute();



                //res = null;

                try {
                    test.get().toString();
                    //t1.toString();
                   // res = test.get();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }


                //Set up server connection and get data from server.
                result.setText(test);


            }
        });

    }



    }










