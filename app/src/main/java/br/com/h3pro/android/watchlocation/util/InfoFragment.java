package br.com.h3pro.android.watchlocation.util;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;

import br.com.h3pro.android.watchlocation.MainLocationActivity;
import br.com.h3pro.android.watchlocation.R;


public class InfoFragment extends Fragment {

    private MainLocationActivity mainLocation;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View v =  inflater.inflate(R.layout.info_fragment_location, container, false);
        return v;
    }

    @Override
    public void onStart() {
        super.onStart();
        setScreenData();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mainLocation = (MainLocationActivity) activity;
     }

    public void setScreenData() {

       View v = getView();

        if (!isVisible()) { return; }

            TextView lng = (TextView) v.findViewById(R.id.textLongitudeValue);
            lng.setText(OPE.convertDecimalToGPS(mainLocation.longitude, false));

            TextView lat = (TextView) v.findViewById(R.id.textLatitudeValue);
            lat.setText(OPE.convertDecimalToGPS(mainLocation.latitude, true));

            Date dateTimeUTC = new Date(mainLocation.time);

            TextView tim = (TextView) getView().findViewById(R.id.textTimeValue);
            tim.setText(new SimpleDateFormat("HH:mm").format(dateTimeUTC) + " (UTC)");

            TextView dat = (TextView) getView().findViewById(R.id.textDateValue);
            dat.setText(new SimpleDateFormat("MM/dd/yyyy").format(dateTimeUTC));

            TextView acc = (TextView) v.findViewById(R.id.textAccuracyValue);
            acc.setText(String.format("%.1f", mainLocation.accuracy) + "m");

    }

}
