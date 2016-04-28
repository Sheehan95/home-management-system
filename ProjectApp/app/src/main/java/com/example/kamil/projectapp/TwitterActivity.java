package com.example.kamil.projectapp;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Displays and allows the user to change the current Twitter Handle set on the Raspberry Pi.
 */
public class TwitterActivity extends Activity {

    private TextView currentHandle;
    private EditText handleInput;
    private Button setHandleButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_twitter);

        currentHandle = (TextView) findViewById(R.id.label_twitter_handle);
        handleInput = (EditText) findViewById(R.id.twitter_handle_input);
        setHandleButton = (Button) findViewById(R.id.button_set_twitter_handle);

        setHandleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newHandle = handleInput.getText().toString();

                if (newHandle.isEmpty()){
                    Toast.makeText(getApplicationContext(), "Enter a handle", Toast.LENGTH_LONG).show();
                }
                else {
                    new PostTwitter().execute(newHandle);
                }

            }
        });

        new GetTwitter().execute();

    }

    /**
     * An Asynchronous Task that gets the current Twitter Handle via HTTP request.
     */
    private class GetTwitter extends AsyncTask<Void, Void, String> {

        @Override
        protected String doInBackground(Void... params) {

            HTTPRequestHandler req = HTTPRequestHandler.getInstance();
            String handle = req.getTwitterHandle();
            return handle;

        }

        @Override
        protected void onPostExecute(String result) {

            super.onPostExecute(result);

            if (result.isEmpty()){
                return;
            }
            currentHandle.setText(result);
        }
    }


    /**
     * An Asynchronous Task that sets the Twitter Handle via HTTP request.
     */
    private class PostTwitter extends AsyncTask<String, Void, Boolean> {

        @Override
        protected Boolean doInBackground(String... params) {

            HTTPRequestHandler req = HTTPRequestHandler.getInstance();

            try {
                req.postTwitterHandle(params[0]);
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
                result = "Handle successfully set";
            }
            else {
                result = "Failed to set Twitter Handle";
            }

            Toast.makeText(getApplicationContext(), result, Toast.LENGTH_LONG).show();

            if (success){
                new GetTwitter().execute();
            }

        }

    }

}
