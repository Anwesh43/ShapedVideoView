package com.anwesome.ui.shapedvideo;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;

import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Created by anweshmishra on 11/03/17.
 */
public class DrawInVideoView extends ShapedVideoView{
    private int time = 0;
    private boolean isAnimated = false;
    private CircularColor prev=null,curr=null;
    private float colorY;
    private ConcurrentLinkedQueue<CircularColor> circularColors = new ConcurrentLinkedQueue<>();
    public DrawInVideoView(Context context) {
        super(context);
    }
    public DrawInVideoView(Context context, AttributeSet attrs) {
        super(context,attrs);
    }
    public boolean shouldDraw() {
        return true;
    }
    public void stopAnimation() {
        isAnimated = false;
        prev = curr;
        curr = null;
    }
    public void drawElements(Canvas canvas,Paint paint) {
        if(time == 0) {
            String colorsHexes[]={"00BCD4","ef5350","F57F17","43A047","5E35B1"};
            float gap = (2*canvas.getWidth())/(3*colorsHexes.length+1),x = gap;
            colorY = (canvas.getHeight()*8)/10;
            for(String colorHex:colorsHexes) {
                circularColors.add(new CircularColor(colorHex,x,colorY+gap/2,gap));
                x+=(3*gap)/2;
            }
        }
        for(CircularColor circularColor:circularColors) {
            circularColor.draw(canvas,paint);
        }
        time++;
        if(isAnimated) {
            if(curr==null) {
                curr.update();
                if(prev!=null) {
                    prev.update();
                    if(prev.stopped() && curr.stopped()) {
                        stopAnimation();
                    }
                }
                else {
                    if(curr.stopped()) {
                        stopAnimation();
                    }
                }

            }
            try {
                Thread.sleep(50);
                invalidate();
            }
            catch (Exception ex) {

            }
        }
    }
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX(),y = event.getY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if(y>=colorY && !isAnimated && curr==null) {
                    for(CircularColor circularColor:circularColors) {
                        if(circularColor.scale==0.4f && circularColor.handleTap(x,y)) {
                            curr = circularColor;
                            break;
                        }
                    }
                    if(curr!=null) {
                        curr.startUpdating(1);
                        if(prev!=null) {
                            prev.startUpdating(-1);
                        }
                        isAnimated = true;
                        postInvalidate();
                    }
                }
                break;
            case MotionEvent.ACTION_MOVE:
                break;
            case MotionEvent.ACTION_UP:
                break;
            default:
                break;
        }
        return true;
    }
    private class CircularColor {
        private float x,y,r,scale = 0.4f,dir=0;
        private String colorHex="#99";
        public CircularColor(String colorHex,float x,float y,float r) {
            this.colorHex += colorHex.replace("#","");
            this.x = x;
            this.y = y;
            this.r = r;
        }
        public int getColor() {
            return Color.parseColor(colorHex);
        }
        public boolean handleTap(float x,float y) {
            boolean condition = x>=this.x-r && x<=this.x+r && y>=this.y-r && y<=this.y+r;
            return condition;
        }
        public boolean stopped() {
            return dir == 0;
        }
        public void startUpdating(float dir) {
            this.dir = dir;
        }
        public void draw(Canvas canvas, Paint paint) {
            paint.setStyle(Paint.Style.FILL);
            paint.setColor(Color.parseColor(colorHex));
            canvas.save();
            canvas.translate(x,y);
            canvas.scale(scale,scale);
            canvas.drawCircle(0,0,r,paint);
            canvas.restore();
        }
        public void update() {
            this.scale+=0.1f*dir;
            if(this.scale>=1) {
                this.scale = 1;
                this.dir = 0;
            }
            if(this.scale<=0.4f) {
                this.scale = 0.4f;
                this.dir = 0;
            }
        }
        public int hashCode() {
            return (int)(x+scale)+colorHex.hashCode();
        }
    }
}
