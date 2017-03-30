package com.anwesome.ui.shapedvideo;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;

import java.util.*;

/**
 * Created by anweshmishra on 30/03/17.
 */
public class DigitalClockVideoView extends ShapedVideoView {
    private int time = 0,w,h;
    private DigitalClock digitalClock;
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
            digitalClock = new DigitalClock(paint);
        }
        digitalClock.draw(canvas);
        time++;
        try {
            digitalClock.update();
            Thread.sleep(50);
            invalidate();
        }
        catch (Exception ex) {

        }
    }
    public void handleTap(float x,float y) {

    }
    private class DigitalClock {
        private float x,y,wClock,hClock;
        private TimePart timePart = new TimePart();
        private Paint paint;
        private Separator separator = new Separator();
        public DigitalClock(Paint paint) {
            this.x = w/2;
            this.y = h/2;
            this.wClock = 4*w/5;
            this.hClock = 2*h/11;
            this.paint = paint;
            paint.setStyle(Paint.Style.FILL);
            paint.setColor(Color.parseColor("#FAFAFA"));
            measureTimePartAndSeparators();
        }
        public void measureTimePartAndSeparators() {
            paint.setTextSize(hClock);
            String timeString = getTimeString();
            if (!(paint.measureText(timeString) < this.wClock)) {
                hClock*=0.9F;
                measureTimePartAndSeparators();
            }
        }
        public String getTimeString() {
            String timeString = timePart.getH()+":"+timePart.getM()+":"+timePart.getS();
            if(!separator.show()) {
                timeString = timeString.replaceAll(":"," ");
            }
            return timeString;
        }
        public void draw(Canvas canvas) {
            String timeString = getTimeString();
            canvas.drawText(timeString,x-paint.measureText(timeString)/2,y,paint);
        }
        public void update() {
            timePart.update();
            separator.update();
        }
    }
    private class Separator {
        private int counter = 0;
        public void update() {
            counter++;
        }
        public boolean show() {
            return counter%20 >= 0 && counter%20<10;
        }
        public int hashCode() {
            return counter;
        }
    }
    private class TimePart {
        private String h,m,s;
        public TimePart() {
            getTime();
        }
        public void getTime() {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(new Date());
            int hour = calendar.get(Calendar.HOUR_OF_DAY),min = calendar.get(Calendar.MINUTE),sec = calendar.get(Calendar.SECOND);
            h = getDoubleDigitString(hour);
            m = getDoubleDigitString(min);
            s = getDoubleDigitString(sec);
        }
        public String getDoubleDigitString(int num) {
            return num>=10?""+num:"0"+num;
        }
        public void update() {
            getTime();
        }
        public String getH() {
            return h;
        }
        public String getM() {
            return m;
        }
        public String getS() {
            return s;
        }
        public int hashCode() {
            return h.hashCode()+m.hashCode()+s.hashCode();
        }
    }
}
