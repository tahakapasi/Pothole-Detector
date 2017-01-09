package com.pothole.potholedetector;

import android.app.Service;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.content.Context;
import android.hardware.SensorEvent;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

public class Ride extends Service implements SensorEventListener {


    @Override
    public void onSensorChanged(SensorEvent event) {
        Sensor mySensor = event.sensor;
        if(mySensor.getType() == Sensor.TYPE_ACCELEROMETER){
            Log.d("Hello accelerometer", "onSensorChanged: Hi I detected a change !!");
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
    public Ride() {
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // Let it continue running until it is stopped.
        Toast.makeText(this, "Service Started", Toast.LENGTH_SHORT).show();
        SensorManager senSensorManager=(SensorManager)getSystemService(Context.SENSOR_SERVICE);
        Sensor senAccelerometer=senSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        senSensorManager.registerListener(this, senAccelerometer, SensorManager.SENSOR_DELAY_NORMAL);

        return START_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onDestroy() {
        SensorManager senSensorManager=(SensorManager)getSystemService(Context.SENSOR_SERVICE);
        super.onDestroy();
        senSensorManager.unregisterListener(this);
        Toast.makeText(this, "Service Destroyed", Toast.LENGTH_SHORT).show();
    }

}
