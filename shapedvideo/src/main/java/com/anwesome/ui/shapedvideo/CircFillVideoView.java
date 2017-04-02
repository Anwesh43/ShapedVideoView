package com.anwesome.ui.shapedvideo;

import android.content.Context;
import android.graphics.*;
import android.util.AttributeSet;

/**
 * Created by anweshmishra on 02/04/17.
 */
public class CircFillVideoView extends ShapedVideoView{
    private int time = 0,w,h;
    public CircFillVideoView(Context context) {
        super(context);
    }
    public CircFillVideoView(Context context, AttributeSet attrs) {
        super(context,attrs);
    }
    public void drawElements(Canvas canvas, Paint paint) {
        if(time == 0) {
            w = canvas.getWidth();
            h = canvas.getHeight();
        }
        time++;
        try {
            Thread.sleep(50);
            invalidate();
        }
        catch (Exception ex) {

        }
    }
    protected boolean shouldDraw() {
        return true;
    }
    public void handleTap(float x,float y) {

    }
    private class CircFill {
        private int color;
        private float x,y,deg=0,endDeg = 0,r;
        private boolean remove = false,stop = false;
        public CircFill(int color) {
            this.color = color;
            x = w/2;
            y = h/2;
            r = Math.min(w,h)/3;
        }
        public void draw(Canvas canvas,Paint paint) {
            paint.setColor(color);
            canvas.save();
            canvas.translate(x,y);
            canvas.drawArc(new RectF(-r,-r,r,r),deg,endDeg-deg,true,paint);
            canvas.restore();
        }
        public void startIncreasing() {
            endDeg += 10;
            if(endDeg == 360) {
                cancelPendingInputEvents()
            }
        }
        public boolean shouldRemove() {
            return remove;
        }
        public boolean shouldStop() {
            return stop;
        }
        public void startDecreasing() {
            deg+=10;
            if(endDeg-deg<=0) {
                remove = true;
            }
        }
        public int hashCode() {
            return (int)(deg+endDeg)+color;
        }
    }
}
