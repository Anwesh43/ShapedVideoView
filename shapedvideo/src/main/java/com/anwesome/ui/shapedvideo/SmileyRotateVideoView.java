package com.anwesome.ui.shapedvideo;


import android.content.Context;
import android.graphics.*;
import android.util.AttributeSet;

import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Created by anweshmishra on 16/03/17.
 */
public class SmileyRotateVideoView extends ShapedVideoView {
    private int w,h,time = 0;
    ConcurrentLinkedQueue<RotatingSmiley> smileys = new ConcurrentLinkedQueue<>();
    public SmileyRotateVideoView(Context context) {
        super(context);
    }
    public SmileyRotateVideoView(Context context, AttributeSet attrs) {
        super(context,attrs);
    }
    public boolean shouldDraw() {
        return true;
    }
    public void drawElements(Canvas canvas,Paint paint) {
        if(time == 0) {
            w = canvas.getWidth();
            h = canvas.getHeight();
        }
        for(RotatingSmiley smiley:smileys) {
            smiley.draw(canvas,paint);
            smiley.update();
        }
        time++;
        try {
            Thread.sleep(50);
            invalidate();
        }
        catch (Exception ex) {

        }
    }
    public void handleTap(float x,float y) {
        smileys.add(new RotatingSmiley(x,y,w/10));
    }
    private class RotatingSmiley {
        private float deg = 0,time = 1,x,y,radius;
        public RotatingSmiley(float x,float y,float radius) {
            this.x = x;
            this.y = y;
            this.radius = radius;
        }
        public void draw(Canvas canvas,Paint paint) {
            canvas.save();
            canvas.translate(x,y);
            drawFace(canvas,paint);
            drawEyes(canvas,paint);
            drawSmile(canvas,paint);
            canvas.restore();
        }
        private void drawEyes(Canvas canvas,Paint paint) {
            int dir[] = {1,-1};
            paint.setStyle(Paint.Style.FILL);
            paint.setColor(Color.parseColor("#FFEA00"));
            canvas.save();
            canvas.translate(0,-radius/3);
            for(int i=0;i<2;i++) {
                canvas.save();
                canvas.scale(dir[i],1);
                float x = -radius/4,y = 0;
                canvas.drawArc(new RectF(x-radius/20,y-radius/10,x+radius/20,y+radius/10),0,360,true,paint);
                canvas.restore();
            }
            canvas.restore();
        }
        private void drawFace(Canvas canvas,Paint paint) {
            paint.setStyle(Paint.Style.FILL);
            paint.setColor(Color.parseColor("#FFC107"));
            canvas.drawCircle(0,0,radius,paint);
        }
        private void drawSmile(Canvas canvas,Paint paint) {
            float smileRadius = (radius)/2;
            paint.setColor(Color.parseColor("#212121"));
            paint.setStrokeWidth(6);
            paint.setStyle(Paint.Style.STROKE);
            canvas.save();
            canvas.translate(0,smileRadius);
            canvas.rotate(deg);
            canvas.drawArc(new RectF(-smileRadius/2,-smileRadius/4,smileRadius/2,smileRadius/4),7.5f,165,false,paint);
            canvas.restore();
        }
        public void update() {
            if(time %20 == 0) {
                deg+=20;
                if(deg%180 == 0) {
                    time++;
                }
            }
            else {
                time++;
            }
        }
        public int hashCode() {
            return (int)(x+y+radius+deg+time);
        }
    }
}
