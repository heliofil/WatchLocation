package br.com.h3pro.android.watchlocation;

import android.app.Fragment;
import android.app.FragmentManager;
import android.hardware.GeomagneticField;
import android.support.v13.app.FragmentPagerAdapter;
import br.com.h3pro.android.watchlocation.util.AltimeterScreenFragment;
import br.com.h3pro.android.watchlocation.util.CallMyTracksFragment;
import br.com.h3pro.android.watchlocation.util.InfoFragment;
import br.com.h3pro.android.watchlocation.view.Altimeter;

/**
 * Created by HelioSilva on 24/11/2014.
 */
public class MainLocationPageAdapter  extends FragmentPagerAdapter {

    private MainLocationActivity mainLocation;
    private Fragment[] listFragment = new Fragment[3];

    public MainLocationPageAdapter(FragmentManager fm,MainLocationActivity m) {
        super(fm);
        mainLocation = m;
        listFragment[0] = new InfoFragment();
        listFragment[1] = new AltimeterScreenFragment();
        listFragment[2] = new CallMyTracksFragment();
    }

    @Override
    public Fragment getItem(int i) {
        return listFragment[i];
    }

    @Override
    public int getCount() {
         return listFragment.length;
    }

    public void updateItemFragments(int pos) {
        InfoFragment info = (InfoFragment) listFragment[0];
        AltimeterScreenFragment altm = (AltimeterScreenFragment) listFragment[1];
        switch (pos) {
            case 1:
                altm.updateSpeed();
                break;
            case 2:
                altm.updateAltimeter();
                break;
            case 3:
                altm.updateBearing();
                break;
        }
    }

    public void updateLocationAllFragments() {
        InfoFragment info = (InfoFragment) listFragment[0];
        info.setScreenData();
        AltimeterScreenFragment altm = (AltimeterScreenFragment) listFragment[1];
        altm.updateAll();
    }

}