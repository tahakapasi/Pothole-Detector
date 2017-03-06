package com.pothole.potholedetector;

import android.util.Log;

/**
 * Created by taha on 6/3/17.
 */

public class Potholes {
    private String report_id;
    private String longitude;
    private String latitude;
    private String status;
    private String priority;
    private String time;

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getReport_id() {
        return report_id;
    }

    public void setReport_id(String report_id) {
        this.report_id = report_id;
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

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    @Override
    public String toString() {
        return "Report ID: " + getReport_id() + "\n" +
                "Longitude: " + getLongitude() + " Latitude: " + getLatitude() + "\n" +
                "Status: " + getStatus() + " Priority: " + getPriority() + " Time:" + getTime() + "\n";
    }
}
