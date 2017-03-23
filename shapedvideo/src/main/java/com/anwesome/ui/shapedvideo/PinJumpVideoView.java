package com.anwesome.ui.shapedvideo;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Created by anweshmishra on 23/03/17.
 */
public class PinJumpVideoView extends ShapedVideoView {
    private int w,h,time = 0;
    private ConcurrentLinkedQueue<JumpingPinContainer> jumpingPinContainers = new ConcurrentLinkedQueue<>();
    public PinJumpVideoView(Context context) {
        super(context);
    }
    public PinJumpVideoView(Context context, AttributeSet attrs) {
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
        paint.setColor(Color.parseColor("#99FF6F00"));
        paint.setStyle(Paint.Style.FILL);
        for(JumpingPinContainer jumpingPinContainer:jumpingPinContainers) {
            jumpingPinContainer.draw(canvas,paint);
            jumpingPinContainer.update();
            if(jumpingPinContainer.isStopped()) {
                jumpingPinContainers.remove(jumpingPinContainer);
            }
        }
        time++;
        try {
            Thread.sleep(20);
            invalidate();
        }
        catch (Exception ex) {

        }
    }
    public void handleTap(float x,float y) {
        JumpingPinContainer jumpingPinContainer = new JumpingPinContainer(x,y);
        jumpingPinContainers.add(jumpingPinContainer);
    }
    private class JumpingPinContainer {
        private List<JumpingPin> jumpingPins = new ArrayList<>();
        private float x,y;
        public boolean stopped = false;
        private int currIndex = 0;
        public JumpingPinContainer(float x,float y) {
            this.x = x;
            this.y = y;
            initJumpingPins();
        }
        public void initJumpingPins() {
            int n = 12;
            float gap = 360/n;
            for(int i=0;i<n;i++) {
                jumpingPins.add(new JumpingPin(x,y,gap*i));
            }
            if(jumpingPins.size()>0) {
                jumpingPins.get(currIndex).startMoving();
            }
        }
        public void draw(Canvas canvas,Paint paint) {
            for(JumpingPin jumpingPin:jumpingPins) {
                jumpingPin.draw(canvas,paint);
            }
        }
        public void update() {
            if(jumpingPins.size()>0 && currIndex<jumpingPins.size()) {
                JumpingPin currPin = jumpingPins.get(currIndex);
                currPin.update();
                if(currPin.stopped()) {
                    stopped = false;
                    currIndex++;
                    if(currIndex<jumpingPins.size()) {
                        jumpingPins.get(currIndex).startMoving();
                    }
                }
            }
            else {
                stopped = true;
            }
        }
        public boolean isStopped() {
            return stopped;
        }
        public int hashCode() {
            return (int)(x+y+currIndex)+jumpingPins.hashCode();
        }
    }
    private class JumpingPin {
        private float x,y,radius,dir = 0,deg = 0,initRadius;
        public JumpingPin(float x,float y,float deg) {
            this.x = x;
            this.y = y;
            this.radius = w/12;
            this.initRadius = this.radius;
            this.deg = deg;
        }
        public void draw(Canvas canvas,Paint paint) {
            canvas.save();
            canvas.translate(x,y);
            canvas.rotate(deg);
            canvas.drawLine(0,0,0,radius,paint);
            canvas.drawCircle(0,radius+initRadius/8,initRadius/8,paint);
            canvas.restore();
        }
        public void update() {
            radius+=dir*initRadius/4;
            if(radius>=2*initRadius) {
                dir = -1;
            }
            if(radius<=initRadius) {
                dir = 0;
            }
        }
        public boolean stopped() {
            return dir == 0;
        }
        public int hashCode() {
            return (int)(x+y+dir+deg+radius);
        }
        public void startMoving() {
            dir = 1;
        }
    }
}
