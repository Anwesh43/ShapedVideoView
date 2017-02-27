package com.anwesome.ui.shapedvideo;

import android.content.Context;
import android.graphics.*;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by anweshmishra on 27/02/17.
 */
public class ColorFilterShapedVideoView extends ShapedVideoView{
    private int time = 0;
    public ColorFilterShapedVideoView(Context context) {
        super(context);
    }
    public ColorFilterShapedVideoView(Context context, AttributeSet attrs) {
        super(context,attrs);
    }
    protected boolean shouldDraw() {
        return  true;
    }
    public void drawElements(Canvas canvas, Paint paint) {

    }
    public boolean onTouchEvent(MotionEvent event) {
        return true;
    }
    private class ColorFilterRect {
        private float x,y,finalX;
        private int w,h,color,speed,dir = 0;
        public ColorFilterRect(float x,float y,int w,int h,int color) {
            this.x = x;
            this.y = y;
            this.w = w;
            this.h = h;
            this.finalX = x;
            this.speed = w/10;
            this.color = color;
        }
        public void draw(Canvas canvas,Paint paint) {

        }
        public void startMoving(int dir,float finalX) {
            this.dir = dir;
            this.finalX = finalX;
        }
        public boolean stop() {
            return dir == 0;
        }
        public void move() {
            this.x+=dir;
            if((dir == 1 && x>=finalX) || (dir == -1 && x<=finalX)) {
                x = finalX;
                dir = 0;
            }
        }
        public int hashCode() {
            return color+(int)x+(int)y;
        }
    }
}
