package Utilities;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

import Interfaces.MovementCallback;

public class MovementDetector {
    private SensorManager sensorManager;
    private Sensor sensor;
    private SensorEventListener sensorEventListener;
    private MovementCallback movementCallback;
    private  long timestamp = 0;

    public MovementDetector(Context context,MovementCallback movementCallback){
        sensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        this.movementCallback = movementCallback;
        initEventListener();
    }
    private void initEventListener(){
        sensorEventListener = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent event) {
                float z = event.values[0]; //Side Movements - player
                float x = event.values[2];

                calculteMovement(z,x);
            }
            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {

            }
        };
    }
    private void calculteMovement(float z,float x){
        if(System.currentTimeMillis() - timestamp > 500){
            timestamp = System.currentTimeMillis();
            if(z>=3.0){
                if(movementCallback != null)
                    movementCallback.moveZleft();
            }
            else if(z<=-3.0){
                if(movementCallback != null)
                    movementCallback.moveZright();
            }
            if(x>=3.0){
                if(movementCallback != null)
                    movementCallback.moveXup();
            }
            else if(x<=-3.0)
                if(movementCallback !=null)
                    movementCallback.moveXdown();

        }
    }
    public void start(){
        sensorManager.registerListener(sensorEventListener,sensor,SensorManager.SENSOR_DELAY_NORMAL);
    }
    public void stop(){
        sensorManager.unregisterListener(sensorEventListener,sensor);
    }
}
