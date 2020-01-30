package com.example.mgpa1;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

public class OrientationData implements SensorEventListener {

    private SensorManager manager;
    private Sensor accelermoter;
    private Sensor magnometer;

    private float[] accelOutput;
    private float[] magOutput;

    private float[] orentation = new float[3];

    public float [] getOrentation(){
        return  orentation;
    }

    private float[] startOrienation = null;
    public float[] getStartOrientation() {
        return startOrienation;
    }

    public void newGame(){
        startOrienation = null;
    }

    public OrientationData(){
        manager = (SensorManager) Constants.CURRENT_CONTEXT.getSystemService(Context.SENSOR_SERVICE);
        accelermoter = manager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        magnometer = manager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
    }

    public void register(){
        manager.registerListener(this,accelermoter,SensorManager.SENSOR_DELAY_GAME);
        manager.registerListener(this,magnometer,SensorManager.SENSOR_DELAY_GAME);

    }

    public void pause(){
        manager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
            if (sensorEvent.sensor.getType() == Sensor.TYPE_ACCELEROMETER){
                accelOutput = sensorEvent.values;
            }
            else if (sensorEvent.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD){
                magOutput = sensorEvent.values;
            }
            if (accelOutput != null && magOutput != null){
                float[] R = new float[9];
                float[] I = new float[9];
                boolean success = SensorManager.getRotationMatrix(R, I,accelOutput,magOutput);
                if (success){
                    SensorManager.getOrientation(R,orentation);
                    if (startOrienation == null){
                        startOrienation = new float[orentation.length];
                        System.arraycopy(orentation, 0, startOrienation, 0, orentation.length);
                    }
                }
            }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }




}
