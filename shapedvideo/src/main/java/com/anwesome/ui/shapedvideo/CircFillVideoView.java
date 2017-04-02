package com.anwesome.ui.shapedvideo;

import android.content.Context;
import android.graphics.*;
import android.util.AttributeSet;

import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Created by anweshmishra on 02/04/17.
 */
public class CircFillVideoView extends ShapedVideoView{
    private int time = 0,w,h;
    private boolean isAnimated = false;
    private CircFill currCirc,prevCirc;
    private ConcurrentLinkedQueue<CircFill> circFills = new ConcurrentLinkedQueue<>();
    public CircFillVideoView(Context context) {
        super(context);
    }
    public CircFillVideoView(Context context, AttributeSet attrs) {
        super(context,attrs);
    }
    private CircFill getCircFillAt(int index) {
        int i = 0;
        for(CircFill circFill:circFills) {
            if(i == index) {
                return circFill;
            }
            i++;
        }
        return null;
    }
    private void stopCondition() {
        prevCirc = currCirc;
        currCirc = getCircFillAt(1);
        if(currCirc == null) {
            addCircFills();
            currCirc = getCircFillAt(1);
        }
        isAnimated = false;
    }
    public void drawElements(Canvas canvas, Paint paint) {
        if(time == 0) {
            w = canvas.getWidth();
            h = canvas.getHeight();
            addCircFills();
            currCirc = getCircFillAt(0);
            prevCirc = null;
        }
        time++;
        if(isAnimated) {
            if(currCirc!=null) {
                currCirc.startIncreasing();
                if (prevCirc != null) {
                    prevCirc.startDecreasing();
                    if(currCirc.shouldStop() && prevCirc.shouldRemove()) {
                        circFills.remove(currCirc);
                        stopCondition();
                    }
                }
                else  {
                    if(currCirc.shouldStop()) {
                        stopCondition();
                    }
                }
            }

            try {
                Thread.sleep(50);
                invalidate();
            } catch (Exception ex) {

            }
        }
    }
    public void addCircFills() {
        int colors[] = {Color.parseColor("#99f44336"),Color.parseColor("#99009688"),Color.parseColor("#993F51B5"),Color.parseColor("#99673AB7"),Color.parseColor("#99E91E63")};
        for(int color:colors) {
            circFills.add(new CircFill(color));
        }
    }
    protected boolean shouldDraw() {
        return true;
    }
    public void handleTap(float x,float y) {
        if(!isAnimated && currCirc!=null) {
            isAnimated = true;
        }
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
                stop = true;
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
