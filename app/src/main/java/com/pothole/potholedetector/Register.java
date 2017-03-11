package com.pothole.potholedetector;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

public class Register extends AppCompatActivity {

    private EditText name;
    private EditText email;
    private EditText password;
    private EditText confirmPassword;
    private Button register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        name = (EditText) findViewById(R.id.name);
        email = (EditText) findViewById(R.id.email);
        password = (EditText) findViewById(R.id.password);
        confirmPassword = (EditText) findViewById(R.id. confirm_password);
        register = (Button) findViewById(R.id.register);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (name.getText().toString().equals("") || email.getText().toString().equals("") || password.getText().toString().equals("")) {
                    new AlertDialog.Builder(Register.this)
                            .setTitle("Empty fields")
                            .setMessage("Please make sure all fields are filled")
                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    // continue with delete
                                }
                            })
                            .show();
                } else if (!password.getText().toString().equals(confirmPassword.getText().toString())) {
                    new AlertDialog.Builder(Register.this)
                            .setTitle("Password mismatch")
                            .setMessage("The passwords you have entered do not match")
                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    // continue with delete
                                }
                            })
                            .show();
                } else if (!isNetworkAvailable()) {
                    new AlertDialog.Builder(Register.this)
                            .setTitle("No Internet")
                            .setMessage("Please make sure you are connected to the Internet")
                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    // continue with delete
                                }
                            })
                            .show();
                }// else if (emailExists(email.getText().toString())) {
//                    new AlertDialog.Builder(Register.this)
//                            .setTitle("Email exists")
//                            .setMessage("The email address you have entered already been registered")
//                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
//                                public void onClick(DialogInterface dialog, int which) {
//                                    // continue with delete
//                                }
//                            })
//                            .show();
//                }
                  else {
                    new addUser().execute(name.getText().toString(),email.getText().toString(),password.getText().toString());
                }
            }
        });

    }
    private boolean emailExists(String email) {
        try {

            String link="http://ec2-52-26-92-134.us-west-2.compute.amazonaws.com/potholeAppBackend/emailExists.php";
            String data  = URLEncoder.encode("email", "UTF-8") + "=" + URLEncoder.encode(email, "UTF-8");

            URL url = new URL(link);
            URLConnection conn = url.openConnection();

            conn.setDoOutput(true);
            OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());

            wr.write( data );
            wr.flush();

            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));

            StringBuilder sb = new StringBuilder();
            String line = null;

            // Read Server Response
            while((line = reader.readLine()) != null) {
                sb.append(line);
                break;
            }
            String result = sb.toString();
            Log.v("Result",sb.toString());

            if (result.equals("1")) {
                return true;
            } else {
                return false;
            }

        } catch(Exception e){
            return true;
        }
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    private class addUser extends AsyncTask {

        private ProgressDialog waitDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            waitDialog = new ProgressDialog(Register.this);
            waitDialog.setMessage("Registering...");
            waitDialog.show();
        }

        @Override
        protected Object doInBackground(Object[] params) {

            String name = (String)params[0];
            String email = (String)params[1];
            String password = (String)params[2];

            try {

                String link="http://ec2-52-26-92-134.us-west-2.compute.amazonaws.com/potholeAppBackend/addUser.php";
                String data  = URLEncoder.encode("name", "UTF-8") + "=" + URLEncoder.encode(name, "UTF-8");
                data += "&" + URLEncoder.encode("password", "UTF-8") + "=" + URLEncoder.encode(password, "UTF-8");
                data += "&" + URLEncoder.encode("email", "UTF-8") + "=" + URLEncoder.encode(email, "UTF-8");

                URL url = new URL(link);
                URLConnection conn = url.openConnection();

                conn.setDoOutput(true);
                OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());

                wr.write( data );
                wr.flush();

                BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));

                StringBuilder sb = new StringBuilder();
                String line = null;

                // Read Server Response
                while((line = reader.readLine()) != null) {
                    sb.append(line);
                    break;
                }
                return sb.toString();

            } catch(Exception e){
                return new String("Exception: " + e.getMessage());
            }
        }

        @Override
        protected void onPostExecute(Object o) {
            super.onPostExecute(o);

            if (o.toString() == "0")
                new AlertDialog.Builder(Register.this)
                        .setTitle("Something went wrong")
                        .setMessage("Something went wrong! Please try again later")
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // continue with delete
                            }
                        })
                        .show();
            else {
                if (waitDialog.isShowing()) {
                    waitDialog.dismiss();
                }
                Intent i = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(i);
                finish();
            }
        }
    }



}
