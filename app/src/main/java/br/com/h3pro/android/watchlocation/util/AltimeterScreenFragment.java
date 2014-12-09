package br.com.h3pro.android.watchlocation.util;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.hardware.GeomagneticField;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import br.com.h3pro.android.watchlocation.MainLocationActivity;
import br.com.h3pro.android.watchlocation.R;
import br.com.h3pro.android.watchlocation.view.Altimeter;
import br.com.h3pro.android.watchlocation.view.Compass;

/**
 * Created by HelioSilva on 24/11/2014.
 */
public class AltimeterScreenFragment extends Fragment implements SensorEventListener {

    private SensorManager mSensor;
    private Sensor sensor;
    private double sensorCompass = 0;

    MainLocationActivity mainLocation;


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        return inflater.inflate(R.layout.altimeter_fragment_location, container, false);
    }

    public void updateAll(){
        if (!isVisible()){
            return;
        }
        updateAltimeter();
        updateBearing();
        updateSpeed();
        updateTrueNorth();
        updateCompass();
    };


    public void updateAltimeter() {
        if (!isVisible()){
            return;
        }
        Altimeter altimeter = (Altimeter) getView().findViewById(R.id.viewAltimeter);
        altimeter.updateAltimeter(mainLocation.altitude);
        TextView a = (TextView) getView().findViewById(R.id.txtAltimeterValue);
        a.setText(String.valueOf(mainLocation.altitude)+"m");
    }

    public void updateBearing() {
        if (!isVisible()){
            return;
        }
        TextView b = (TextView) getView().findViewById(R.id.txtBearingValue);
        b.setText(String.valueOf(mainLocation.bearing)+"°");
    }

    public void updateSpeed() {
        if (!isVisible()){
            return;
        }
        TextView s = (TextView) getView().findViewById(R.id.txtSpeedValue);
        s.setText(String.valueOf((int) (mainLocation.speed*3.6))+"km/h");
    }

    public void updateTrueNorth() {
      if (!isVisible()){
          return;
      }
        TextView s = (TextView) getView().findViewById(R.id.txtCompassValue);
        String r = "E";
        GeomagneticField geo = new GeomagneticField((float) mainLocation.latitude,(float) mainLocation.longitude,0,mainLocation.time);
        double i = geo.getDeclination();
        if (i < 0) {
            i = i*-1;
            r = "W";
        }
        s.setText(String.format("%.2f",i)+"° "+r);
    }

    private void updateCompass() {
        Compass compass = (Compass) getView().findViewById(R.id.viewCompass);
        compass.reloadPosition((float) sensorCompass);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mSensor = (SensorManager) activity.getSystemService(Context.SENSOR_SERVICE);
        sensor = mSensor.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
        mainLocation = (MainLocationActivity) activity;
    }

    private void sensorStart() {
        mSensor.registerListener(this,sensor,SensorManager.SENSOR_DELAY_NORMAL,SensorManager.SENSOR_STATUS_ACCURACY_HIGH);
    }

    private void sensorClose(){
      mSensor.unregisterListener(this);
    };

    @Override
    public void onStart() {
        super.onStart();
        sensorStart();
    }

    @Override
    public void onResume() {
        super.onResume();
        sensorStart();
    }

    @Override
    public void onPause() {
        super.onPause();
        sensorClose();
    }

    @Override
    public void onStop() {
        super.onStop();
        sensorClose();
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
       if (event.sensor == sensor) {
           Log.v(OPE.TAG, String.valueOf(event.values[2]));
           sensorCompass = event.values[2];
           updateCompass();
       }

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
