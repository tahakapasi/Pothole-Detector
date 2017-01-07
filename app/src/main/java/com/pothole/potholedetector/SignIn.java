package com.pothole.potholedetector;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

/**
 * Created by taha on 5/1/17.
 */

public class SignIn extends AsyncTask {

    public Context activity;
    private TextView error;

    public SignIn(Context main, TextView errorBox) {
        activity = main;
        error = errorBox;
    }

    @Override
    protected void onPreExecute() {

    }

    @Override
    protected Object doInBackground(Object... params) {

        try {
            String username = (String)params[0];
            String password = (String)params[1];

            String link="http://ec2-52-38-227-51.us-west-2.compute.amazonaws.com/potholeAppBackend/login.php";
            String data  = URLEncoder.encode("username", "UTF-8") + "=" + URLEncoder.encode(username, "UTF-8");
            data += "&" + URLEncoder.encode("password", "UTF-8") + "=" + URLEncoder.encode(password, "UTF-8");

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
    protected void onPostExecute(Object result) {

        if (result.equals("0")) {
            error.setText("Invalid credentials");
        } else {

            JSONObject getResult = null;
            try {
                getResult = new JSONObject(result.toString());
                SharedPreferences sharedPref = activity.getSharedPreferences("userInfo", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putInt("id",Integer.parseInt(String.valueOf(getResult.get("id"))));
                editor.putString("name",String.valueOf(getResult.get("name")));
                editor.putString("email",String.valueOf(getResult.get("email")));
                editor.commit();

                Intent home = new Intent(activity,Home.class);
                activity.startActivity(home);
                ((Activity)activity).finish();


            } catch (JSONException e) {
                e.printStackTrace();
            }
            error.setTextColor(Color.GREEN);
            error.setText("Login successful!");
        }
    }
}
