package com.pothole.potholedetector;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private EditText usernameField, passwordField;
    private TextView error;
    private Button loginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        usernameField = (EditText) findViewById(R.id.username);
        passwordField = (EditText) findViewById(R.id.password);
        SharedPreferences sharedPref = getSharedPreferences("userInfo", Context.MODE_PRIVATE);

        if (sharedPref.contains("name") && sharedPref.contains("id")) {
            //TO-DO User is already logged in. Go to next activtiy
            Intent home = new Intent(this,Home.class);
            startActivity(home);
            finish();
        }

        error = (TextView) findViewById(R.id.error);
        loginButton = (Button) findViewById(R.id.loginButton);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                error.setText("");
                String username = usernameField.getText().toString();
                String password = passwordField.getText().toString();
                if(username.equals("") || password.equals("")) {
                    error.setText("No field can be empty!");
                } else {
                    new SignIn(MainActivity.this,error).execute(username, password);
                }
            }
        });
    }
}
