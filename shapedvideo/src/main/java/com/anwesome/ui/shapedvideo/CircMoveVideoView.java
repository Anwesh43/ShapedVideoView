package com.anwesome.ui.shapedvideo;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;

/**
 * Created by anweshmishra on 04/04/17.
 */
public class CircMoveVideoView extends ShapedVideoView {
    private int w,h,time = 0;
    public CircMoveVideoView(Context context) {
        super(context);
    }
    public CircMoveVideoView(Context context, AttributeSet attrs) {
        super(context,attrs);
    }
    public void drawElements(Canvas canvas, Paint paint) {
        if(time == 0) {
            w = canvas.getWidth();
            h = canvas.getHeight();
        }
    }
    public boolean shouldDraw() {
        return true;
    }
    public void handleTap(float x,float y) {

    }
    private class CircMove {
        private float x,y,k=0,xSpeed = 0,ySpeed = 0;
        public CircMove(float x,float y) {
            this.x = x;
            this.y = y;
        }
        public void setSpeed(float x,float y) {
            this.xSpeed = (this.x - x)/10;
            this.ySpeed = (this.y - y)/10;
        }
        public void draw(Canvas canvas,Paint paint) {
            canvas.drawCircle(x,y,w/10,paint);
        }
        public void update() {
            x+=xSpeed;
            y+=ySpeed;
            k++;
            if(k == 10) {
                xSpeed = 0;
                ySpeed = 0;
            }
        }
        public boolean stop() {
            return xSpeed == 0 && ySpeed == 0;
        }
        public int hashCode() {
            return (int)(x+y+xSpeed+ySpeed);
        }
    }
}
