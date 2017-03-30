package com.anwesome.ui.shapedvideo;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by anweshmishra on 30/03/17.
 */
public class DigitalClockVideoView extends ShapedVideoView {
    private int time = 0,w,h;
    public DigitalClockVideoView(Context context) {
        super(context);
    }
    public DigitalClockVideoView(Context context, AttributeSet attrs) {
        super(context,attrs);
    }
    public boolean shouldDraw() {
        return true;
    }
    public void drawElements(Canvas canvas, Paint paint) {
        if(time == 0) {
            w = canvas.getWidth();
            h = canvas.getHeight();
        }
        time++;
    }
    public void handleTap(float x,float y) {

    }
    private class DigitalClock {
        private float x,y,w,h;
        private List<Separator> separators = new ArrayList<>();
        public DigitalClock() {
            this.x = w/2;
            this.y = h/2;
            this.w = 4*w/5;
            this.h = h/5;
        }
        public void draw(Canvas canvas,Paint paint) {

        }
        public void update() {
        }
    }
    private class Separator {
        private float x,y,r;
        private int counter = 0;
        private Separator(float x,float y) {
            this.x = x;
            this.y = 0;
            this.r = w/10;
        }
        public void update(int counter) {
            counter++;
        }
        public void draw(Canvas canvas,Paint paint) {
            if(counter%2 != 0) {
                canvas.drawCircle(x, -2 * r, r, paint);
                canvas.drawCircle(x, 2 * r, r, paint);
            }
        }
        public int hashCode() {
            return (int)(x);
        }
    }
}
