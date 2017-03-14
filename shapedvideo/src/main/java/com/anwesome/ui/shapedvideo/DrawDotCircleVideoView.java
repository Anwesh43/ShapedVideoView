package com.anwesome.ui.shapedvideo;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;

/**
 * Created by anweshmishra on 14/03/17.
 */
public class DrawDotCircleVideoView extends ShapedVideoView{
    private final float dot_radius = 10,rSpeed = 15;
    public DrawDotCircleVideoView(Context context, AttributeSet attrs) {
        super(context,attrs);
    }
    public DrawDotCircleVideoView(Context context) {
        super(context);
    }
    protected boolean shouldDraw() {
        return true;
    }
    private class Dot {
        private float dir = 0,r,deg,initR;
        private boolean neighborShouldMove = false;
        public Dot(float deg,float r) {
            this.deg = deg;
            this.r = r;
            this.initR = r;
        }
        public void draw(Canvas canvas, Paint paint) {
            float x = (float)(r*Math.cos(deg*Math.PI/180)), y = (float)(r*Math.sin(deg*Math.PI/180));
            canvas.drawCircle(x,y,dot_radius,paint);
        }
        public void update() {
            r+=rSpeed*dir;
            if(r>=initR+rSpeed*6)  {
                dir=-1;
                neighborShouldMove = true;
            }
            if(r<=initR) {
                dir = 0;
                r = initR;
            }
        }
        public void startMoving() {
            if(dir == 0) {
                dir = 1;
            }
        }
    }
}
