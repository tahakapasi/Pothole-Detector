package com.pothole.potholedetector;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

/**
 * Created by taha on 28/1/17.
 */

public class PotholeRecorder extends AsyncTask {
    @Override
    protected Object doInBackground(Object[] params) {

        String longitude = (String) params[0];
        String latitude = (String) params[1];
        int userId = (int) params[2];

        try {
            String link="http://ec2-52-26-92-134.us-west-2.compute.amazonaws.com/potholeAppBackend/potholerecorder.php";
            String data  = URLEncoder.encode("longitude", "UTF-8") + "=" + URLEncoder.encode(longitude, "UTF-8");
            data += "&" + URLEncoder.encode("latitude", "UTF-8") + "=" + URLEncoder.encode(latitude, "UTF-8");
            data += "&" + URLEncoder.encode("userId", "UTF-8") + "=" + URLEncoder.encode(String.valueOf(userId), "UTF-8");

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
}
