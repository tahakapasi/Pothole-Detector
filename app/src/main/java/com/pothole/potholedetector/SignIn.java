package com.pothole.potholedetector;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

import javax.xml.transform.Result;

/**
 * Created by taha on 5/1/17.
 */

public class SignIn extends AsyncTask {

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
        Log.d("DownloadData", "Result was: " + result);
    }


}
