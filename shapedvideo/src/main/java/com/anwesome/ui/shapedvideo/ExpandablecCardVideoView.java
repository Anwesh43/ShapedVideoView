package com.anwesome.ui.shapedvideo;

import android.content.Context;
import android.graphics.*;
import android.util.AttributeSet;

/**
 * Created by anweshmishra on 01/04/17.
 */
public class ExpandablecCardVideoView extends ShapedVideoView {
    private int w,h,time = 0;
    public ExpandablecCardVideoView(Context context) {
        super(context);
    }
    public ExpandablecCardVideoView(Context context, AttributeSet attrs) {
        super(context,attrs);
    }
    public void handleTap(float x,float y) {

    }
    public void drawElements(Canvas canvas, Paint paint) {
        if(time == 0) {
            w = canvas.getWidth();
            h = canvas.getHeight();
        }
        time++;
    }
    public boolean shouldDraw() {
        return true;
    }
    private class ExpandableCard {
        private float x,y;
        public ExpandableCard(float x,float y) {
            this.x = x;
            this.y = y;
        }
        public void draw(Canvas canvas,Paint paint) {

        }
        public void handleTap(float x,float y) {

        }
    }
    private class CloseButton {
        private float x,y,r,deg = 0,dir = 0;
        public CloseButton(float x,float y) {
            this.x = x;
            this.y = y;
            this.r = w/20;
        }
        public void draw(Canvas canvas,Paint paint) {
            canvas.save();
            canvas.translate(x,y);
            canvas.rotate(deg);
            for(int i=0;i<2;i++) {
                canvas.save();
                canvas.rotate(i*90);
                canvas.drawLine(-r,0,r,0,paint);
                canvas.restore()
            }
            canvas.restore();
        }
        public void update() {
            deg+=9*dir;
            if(deg >= 90 || deg<=0) {
                dir = 0;
            }
        }
        public void startMoving() {
            dir = deg == 0?1:-1;
        }
    }
}
