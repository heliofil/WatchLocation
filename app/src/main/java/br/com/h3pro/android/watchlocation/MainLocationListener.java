package br.com.h3pro.android.watchlocation;

import android.location.Location;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.wearable.Wearable;

import br.com.h3pro.android.watchlocation.util.OPE;

/**
 * Created by HelioSilva on 22/11/2014.
 */
public class MainLocationListener implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener,LocationListener {

    private MainLocationActivity mainLocation;

    private GoogleApiClient mGoogleApiClient;

    public MainLocationListener(MainLocationActivity m) {
        mainLocation = m;
        mGoogleApiClient = new GoogleApiClient.Builder(mainLocation)
                .addApi(LocationServices.API)
                .addApi(Wearable.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();
    }



    @Override
    public void onLocationChanged(Location location) {


        if (location.hasAccuracy()) {
            mainLocation.accuracy = location.getAccuracy();
            mainLocation.updateItemData(0);
        }
        if (location.hasSpeed()) {
            mainLocation.speed = location.getSpeed();
            mainLocation.updateItemData(1);
        }
        if (location.hasAltitude()) {
            mainLocation.altitude =  location.getAltitude();
            mainLocation.updateItemData(2);
        }
       if (location.hasBearing()) {
            mainLocation.bearing = location.getBearing();
           mainLocation.updateItemData(3);
        }

       mainLocation.longitude = location.getLongitude();
       mainLocation.latitude = location.getLatitude();
       mainLocation.time = location.getTime();

       mainLocation.updateLocationData();

    }

    @Override
    public void onConnected(Bundle bundle) {
        LocationRequest locationRequest = LocationRequest.create()
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                .setInterval(OPE.TIME_TO_INTERVAL)
                .setFastestInterval(OPE.TIME_TO_FAST_INTERVAL);

        LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, locationRequest, this);

    }

    @Override
    public void onConnectionSuspended(int i) {


    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }

    public void connect() {
        if (!mGoogleApiClient.isConnected()) {
            mGoogleApiClient.connect();
        }
    }

    public void disconnect(){
        if (mGoogleApiClient.isConnected()) {
            mGoogleApiClient.disconnect();
        }
    }



}
