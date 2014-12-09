package br.com.h3pro.android.watchlocation.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import br.com.h3pro.android.watchlocation.R;
import br.com.h3pro.android.watchlocation.util.OPE;

/**
 * Created by HelioSilva on 26/11/2014.
 */
public class Compass extends View {

    private Paint paintCUp = new Paint();
    private Paint paintCDw = new Paint();
    private Paint paintPosition = new Paint();
    private int RefPosition = 0;
    private float RefDegrees = (float) 0;
    float fsize = 0;



    public Compass(Context context, AttributeSet attrs) {
        super(context, attrs);
        int cwhite = getResources().getColor(R.color.H3_White);
        int cred = getResources().getColor(R.color.H3_Red);
        int cblack = getResources().getColor(R.color.H3_Black);
        fsize = getResources().getDimension(R.dimen.H3_FontSize);
        paintCUp.setColor(cred);
        paintCDw.setColor(cwhite);
        paintPosition.setColor(cwhite);
        paintPosition.setTextSize(fsize);
        paintPosition.setTextAlign(Paint.Align.CENTER);
        this.setBackgroundColor(cblack);
    }

    public void reloadPosition(float p) {
        setReferences(p);
        this.invalidate();

    }

    private void setReferences(float p) {

        double ref = 22.5;
        double d =  p - ref;
        RefPosition = 0;
        double w = ref;
        RefDegrees = p;

        for(int i=0;i<OPE.COMPASS_POSITION.length;i++) {
            if (d <= w) {
                RefPosition = i;
                break;
            }
            w = w + (ref*2);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        Path pathCUp = new Path();
        Path pathCDw = new Path();


        float w = getRight();
        float h = getBottom();
        float blk = w / 8;
        float pad[] = {0, 0};
        if (w != h) {
            if (w > h) {
                pad[0] = ((w - h) / 2);
                blk = h/8;
            } else {
                pad[1] = ((h - w) / 2);
            }
        }

        canvas.drawText(String.format("%.1f",RefDegrees) + "° " + getResources().getString(OPE.COMPASS_POSITION[RefPosition]),(blk*4),fsize,paintPosition);

        pathCUp.moveTo(pad[0] + (blk*4),pad[1] + (blk*4));
        pathCUp.lineTo(pad[0] + (blk * 3), pad[1] + (blk * 4));
        pathCUp.lineTo(pad[0] + (blk*4),pad[1] + blk);
        pathCUp.lineTo(pad[0] + (blk*5),pad[1] + (blk*4));
        pathCUp.lineTo(pad[0] + (blk*4),pad[1] + (blk*4));

        pathCDw.moveTo(pad[0] + (blk*4),pad[1] + (blk*4));
        pathCDw.moveTo(pad[0] + (blk*3),pad[1] + (blk*4));
        pathCDw.lineTo(pad[0] + (blk*4),pad[1] + (blk*7));
        pathCDw.lineTo(pad[0] + (blk*5),pad[1] + (blk*4));
        pathCDw.lineTo(pad[0] + (blk*4),pad[1] + (blk*4));

        Matrix m = new Matrix();
        m.postRotate(RefDegrees,pad[0] + (blk*4),pad[1] + (blk*4));

        paintPosition.setTextAlign(Paint.Align.RIGHT);
        pathCUp.transform(m);
        pathCDw.transform(m);

        canvas.drawPath(pathCUp,paintCUp);
        canvas.drawPath(pathCDw,paintCDw);


    }

}
