package com.anwesome.ui.shapedvideo;

import android.content.Context;
import android.graphics.*;
import android.util.AttributeSet;


/**
 * Created by anweshmishra on 24/03/17.
 */
public class ChargingBarVideoView extends ShapedVideoView {
    ChargingBar chargingBar;
    private int w,h,time = 0;
    private boolean isAnimated = false;
    public ChargingBarVideoView(Context context) {
        super(context);
    }
    public ChargingBarVideoView(Context context, AttributeSet attrs) {
        super(context,attrs);
    }
    public void handleTap(float x,float y) {
        if(!isAnimated) {
            chargingBar.startMoving();
            isAnimated = true;
            postInvalidate();
        }
    }
    protected boolean shouldDraw() {
        return true;
    }
    public void drawElements(Canvas canvas, Paint paint) {
        if(time == 0) {
            w = canvas.getWidth();
            h = canvas.getHeight();
            chargingBar = new ChargingBar(w/2,h/2,w/2,h/2);
        }
        chargingBar.draw(canvas,paint);
        time++;
        if(isAnimated) {
            chargingBar.update();
            if(chargingBar.stopped()) {
                isAnimated = false;
            }
            try {
                Thread.sleep(50);
                invalidate();
            }
            catch (Exception ex) {

            }
        }
    }
    private class ChargingBar {
        private float x,y,w,h;
        private int n = 0,maxN = 6,dir = 1;
        public ChargingBar(float x,float y,float w,float h) {
            this.x = x;
            this.y = y;
            this.w = w;
            this.h = h;
        }
        public void draw(Canvas canvas,Paint paint) {
            canvas.save();
            canvas.translate(x,y);
            paint.setStyle(Paint.Style.FILL);
            paint.setColor(Color.parseColor("#99FF6F00"));
            drawBars(canvas,paint,-w/2,(h/2));
            canvas.drawRect(new RectF(-w/20,-h/2-w/10,w/20,-h/2),paint);
            paint.setStyle(Paint.Style.STROKE);
            canvas.drawRect(new RectF(-w/2,-h/2,w/2,h/2),paint);
            canvas.restore();
        }
        public void drawBars(Canvas canvas,Paint paint,float xStart,float yStart) {
            for(int i=0;i<n;i++) {
                canvas.drawRect(new RectF(xStart,yStart-h/maxN,xStart+w,yStart),paint);
                yStart-=h/maxN;
            }
        }
        public void update() {
            n+=dir;
            if(n == maxN-1 || n == 0) {
                dir = 0;
            }
        }
        public boolean stopped() {
            return dir == 0;
        }
        public void startMoving() {
            dir = n == 0?1:-1;
        }
    }
}
