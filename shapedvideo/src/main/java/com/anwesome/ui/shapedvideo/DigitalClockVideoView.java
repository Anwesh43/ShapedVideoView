package com.anwesome.ui.shapedvideo;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;

/**
 * Created by anweshmishra on 30/03/17.
 */
public class DigitalClockVideoView extends ShapedVideoView {
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

    }
    public void handleTap(float x,float y) {

    }
    private class DigitalClock {
        private float x,y,w,h;
        private int counter = 0;
        public DigitalClock(float x,float y,float w,float h) {
            this.x = x;
            this.y = y;
            this.w = w;
            this.h = h;
        }
        public void draw(Canvas canvas,Paint paint) {

        }
        public void update() {
            counter++;
        }
    }
}
