package com.pothole.potholedetector;

import android.util.Log;

import java.util.ArrayList;
import org.json.*;

/**
 * Created by taha on 6/3/17.
 */

public class ParsePotholes {
    private String previousPotholeData;
    private ArrayList<Potholes> potholes;

    public ParsePotholes(String data) {
        previousPotholeData = data;
        potholes = new ArrayList<Potholes>();
    }

    public ArrayList<Potholes> getPotholes() {
        return potholes;
    }

    public boolean process() {
        try {
            JSONArray array = new JSONArray(previousPotholeData);
            for (int i = 0; i < array.length(); i++) {
                Potholes currentRecord = new Potholes();
                currentRecord.setReport_id(array.getJSONObject(i).getString("report_id"));
                currentRecord.setLongitude(array.getJSONObject(i).getString("longitude"));
                currentRecord.setLatitude(array.getJSONObject(i).getString("latitude"));
                currentRecord.setStatus(array.getJSONObject(i).getString("status"));
                currentRecord.setPriority(array.getJSONObject(i).getString("priority"));
                currentRecord.setTime(array.getJSONObject(i).getString("time"));
                potholes.add(currentRecord);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return true;
    }
}
