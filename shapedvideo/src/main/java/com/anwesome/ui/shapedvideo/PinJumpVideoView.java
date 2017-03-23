package com.anwesome.ui.shapedvideo;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;

/**
 * Created by anweshmishra on 23/03/17.
 */
public class PinJumpVideoView extends ShapedVideoView {
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

    }
    public void handleTap(float x,float y) {

    }
    private class JumpingPin {
        private float x,y,radius,dir = 0,deg = 0,initRadius;
        public JumpingPin(float x,float y,float radius) {
            this.x = x;
            this.y = y;
            this.radius = radius;
            this.initRadius = radius;
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
