package com.example.kamil.projectapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Application entry point.
 */
public class MainActivity extends Activity {

    private Button loginButton;
    private EditText userInput;
    private EditText passInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loginButton = (Button) findViewById(R.id.button_login);
        userInput = (EditText) findViewById(R.id.user_input);
        passInput = (EditText) findViewById(R.id.password_input);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = userInput.getText().toString();
                String password = passInput.getText().toString();

                String message = "";

                if (username.isEmpty()){
                    message = "Please enter a username";
                    Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
                    return;
                }
                else if (password.isEmpty()){
                    message = "Please enter a password";
                    Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
                    return;
                }

                HTTPRequestHandler.setDomain("192.167.1.20");
                startActivity(new Intent(getApplicationContext(), HomeActivity.class));

            }
        });

    }

}
