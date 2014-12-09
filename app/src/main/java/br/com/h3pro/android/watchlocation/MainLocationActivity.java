package br.com.h3pro.android.watchlocation;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import br.com.h3pro.android.watchlocation.util.OPE;

public class MainLocationActivity extends Activity  {

    private MainLocationListener mainListener;
    private MainLocationPageAdapter mainPageAdapter;
    public double latitude = 0;
    public double longitude = 0;
    public double accuracy = 150;
    public long time = 0;
    public double bearing = 0;
    public double altitude = 0;
    public double speed = 0;
    private int lastHour = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mainListener = new MainLocationListener(this);

        setContentView(R.layout.rect_activity_main_location);
        ViewPager pagers = (ViewPager) findViewById(R.id.pager);
        mainPageAdapter = new MainLocationPageAdapter(getFragmentManager(),this);
        pagers.setAdapter(mainPageAdapter);

    }

    @Override
    public void finish() {
        mainListener.connect();
        super.finish();
    }

    @Override
    protected void onStart() {
        mainListener.connect();
        super.onStart();

    }

    @Override
    protected void onResume() {
        mainListener.connect();
        super.onResume();
    }

    @Override
    protected void onStop() {
        mainListener.disconnect();
        super.onStop();
    }

    @Override
    protected void onPause() {
        mainListener.disconnect();
        super.onPause();
    }

    private int setLastHour(long time) {
        return (int) ((time / (1000*60*60)) % 24);
    }

    private void setBackgroundImage(){

       int val = OPE.BACKGROUND_IMAGE[3];

       if ((lastHour >= 5)&&(lastHour <= 10)) {
           val = OPE.BACKGROUND_IMAGE[0];
       } else if ((lastHour >= 11)&&(lastHour <=14)){
           val = OPE.BACKGROUND_IMAGE[1];
       } else if ((lastHour >= 15)&&(lastHour <=18)){
           val = OPE.BACKGROUND_IMAGE[2];
       }

        findViewById(R.id.bgpager).setBackgroundResource(val);

    }

    public void updateItemData(int p){

        mainPageAdapter.updateItemFragments(p);
    }

    public void updateLocationData(){
        int h = setLastHour(time);
        if (h != lastHour) {
            lastHour = h;
            setBackgroundImage();
        }
        mainPageAdapter.updateLocationAllFragments();
    }
}
