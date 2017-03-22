package com.pothole.potholedetector;

import android.util.Log;

/**
 * Created by taha on 6/3/17.
 */

public class Potholes {

    private String longitude;
    private String latitude;
    private String status;
    private String time;

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Longitude: " + getLongitude() + " Latitude: " + getLatitude() + "\n" +
                "Status: " + getStatus() + "\n" + " Time: " + getTime() + "\n";
    }
}
