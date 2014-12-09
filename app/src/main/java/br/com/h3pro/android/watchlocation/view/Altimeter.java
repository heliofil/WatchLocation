package br.com.h3pro.android.watchlocation.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import br.com.h3pro.android.watchlocation.R;
import br.com.h3pro.android.watchlocation.util.OPE;

/**
 * Created by HelioSilva on 25/11/2014.
 */
public class Altimeter extends View {
    private Paint paint = new Paint();
    private double padHeight = (float) 0.10;
    private int currentPosition = 0;
    private float maxAltimeter = 50;
    private float[] positions = {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0};

    public Altimeter(Context context, AttributeSet attrs) {
        super(context, attrs);
        paint.setColor(getResources().getColor(R.color.H3_Green));
        paint.setStrokeWidth(3);
        this.setBackgroundColor(getResources().getColor(R.color.H3_Black));
    }

    private void updateMaxAltimeter(float i) {
        if ((i > maxAltimeter )) {
            maxAltimeter = (float) (i +(i * padHeight));
        }
    }

    private void setPosition(float i){
        currentPosition++;
        if (currentPosition >= positions.length) {
            currentPosition = 0;
        }
        positions[currentPosition] = i;

    };

    public void updateAltimeter(double i) {
        setPosition((float) i);
        updateMaxAltimeter((float) i);
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        float lht = (float) (getBottom() * padHeight);
        float lhb = (float) (getBottom() - lht);
        float ph = ((lhb - lht)/maxAltimeter);
        float pw = (float) (getRight()/positions.length);
        float sX = getRight();
        float sY = lhb - (positions[positions.length-1] * ph);
        float fX;
        float fY;

        for (int i=0;i<positions.length;i++) {
            fY = lhb - (positions[i] * ph) ;
            fX = sX - pw;
            canvas.drawLine(sX,sY,fX,fY,paint);
            sX = fX;
            sY = fY;
        }

    }

}
