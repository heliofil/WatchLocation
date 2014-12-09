package br.com.h3pro.android.watchlocation.util;

import br.com.h3pro.android.watchlocation.R;

/**
 * Created by HelioSilva on 22/11/2014.
 */
public class OPE {

    public static final int MILLISECOND = 1000;
    public static final int TIME_TO_FAST_INTERVAL = MILLISECOND * 1;
    public static final int TIME_TO_INTERVAL = MILLISECOND * 3;
    public static final String TAG = "WATCH_LOCATION";
    public static final int[] BACKGROUND_IMAGE = {
            R.drawable.bg_day,
            R.drawable.bg_before,
            R.drawable.bg_after,
            R.drawable.bg_night
    };

    public static final int[] COMPASS_POSITION ={
           R.string.txt_compass_north_abv,
           R.string.txt_compass_northeast_abv,
           R.string.txt_compass_east_abv,
           R.string.txt_compass_southeast_abv,
           R.string.txt_compass_south_abv,
           R.string.txt_compass_southwest_abv,
           R.string.txt_compass_west_abv,
           R.string.txt_compass_northwest_abv
    };


    public static String convertDecimalToGPS(double p,boolean i){
        String val=null;
        String f;

        if (i) {
            if(p >= 0) {
                f = "N";
            } else {
                f = "S";
            }
        } else {
            if(p >= 0) {
                f = "E";
            } else {
                f = "W";
            }
        }

        p = Math.abs(p);

        int g = (int) p;

        val = String.valueOf(g) + "° ";

        p = p - (double) g ;

        p = p*60;

        g = (int) p;

        val = val + String.valueOf(g) + "' ";

        p = p - (double) g ;

        p = p*60;

        val = val + String.format("%.3f",p) + "\" " + f;

        return val;
    }

}
