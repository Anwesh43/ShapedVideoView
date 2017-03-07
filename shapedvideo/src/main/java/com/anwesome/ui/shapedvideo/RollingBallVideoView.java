package com.anwesome.ui.shapedvideo;

import android.content.Context;
import android.graphics.*;
import android.util.AttributeSet;

/**
 * Created by anweshmishra on 07/03/17.
 */
public class RollingBallVideoView extends ShapedVideoView {
    private int w,h,time = 0;
    private boolean isAnimated = false;
    public RollingBallVideoView(Context context) {
        super(context);
    }
    public RollingBallVideoView(Context context, AttributeSet attrs) {
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
        time++;
    }
    public void handleTap(float x,float y) {

    }
    private class RollingBall {
        private float x=0,y = 0,dir = 0,initY=0,radius=50,deg=0,finalY;
        public RollingBall() {
            x = 0;
            y = h/2;
            initY = h/2;
            radius = w/4;
            finalY = h/4;
        }
        public void draw(Canvas canvas,Paint paint) {
            int colors[] = {Color.parseColor("#AA3F51B5"),Color.parseColor("#AAf44336")};
            int dir[] = {1,-1};
            canvas.save();
            canvas.translate(x,y);
            canvas.rotate(deg);
            for(int i=0;i<2;i++) {
                canvas.save();
                canvas.scale(1,dir[i]);
                canvas.drawArc(new RectF(-radius,-radius,radius,radius),0,180,true,paint);
                canvas.restore();
            }
            canvas.restore();
        }
        public void update() {
            x+=20;
            y-=((initY-finalY)/10)*dir;
            if(y<=finalY) {
                dir = -1;
                y = finalY;
            }
            if(y>=initY) {
                dir = 0;
                y = initY;
            }
        }
        public void jump() {
            dir = 1;
        }
    }
}
