package com.anwesome.ui.shapedvideo;

import android.content.Context;
import android.util.AttributeSet;
import android.graphics.*;
import android.view.MotionEvent;

/**
 * Created by anweshmishra on 01/03/17.
 */
public class CircularColorFilterShapeVideoView extends ShapedVideoView{
    private boolean isAnimated = false;
    private int time = 0;
    private CircularColorFilter circularColorFilter = new CircularColorFilter();
    public CircularColorFilterShapeVideoView(Context context) {
        super(context);
    }
    public CircularColorFilterShapeVideoView(Context context, AttributeSet attrs) {
        super(context,attrs);
    }
    public boolean shouldDraw() {
        return true;
    }
    public void drawElements(Canvas canvas,Paint paint) {
        if(time == 0) {
            circularColorFilter.setDimensions(canvas.getWidth(),canvas.getHeight());
        }
        circularColorFilter.draw(canvas,paint);
        if(isAnimated) {
            try {
                Thread.sleep(50);
                invalidate();
            } catch (Exception ex) {

            }
        }
    }
    public void handleTap(float x,float y) {

    }
    private class CircularColorFilter {
        private float x,y,r,sweep = 0,dir = 0;
        public void setDimensions(float w,float h) {
            x = w/2;
            y = h/2;
            r = w/2;
        }
        public void draw(Canvas canvas,Paint paint) {
            paint.setStyle(Paint.Style.FILL);
            paint.setColor(Color.parseColor("#AAE65100"));
            canvas.drawArc(new RectF(x-r,y-r,x+r,y+r),0,sweep,true,paint);
        }
        public void startMoving() {
            dir = 1;
        }
        public void update() {
            sweep+=24*dir;
            if(sweep>=360) {
                dir = -1;
            }
            if(sweep<=0) {
                dir = 0;
                isAnimated = false;
            }
        }
        public int hashCode() {
            return (int)(x+y+r);
        }
    }
}
