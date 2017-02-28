package com.anwesome.ui.shapedvideo;

import android.content.Context;
import android.graphics.*;
import android.util.AttributeSet;

import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Created by anweshmishra on 28/02/17.
 */
public class ColorlessFilterShapedVideoView extends ShapedVideoView {
    public ColorlessFilterShapedVideoView(Context context) {
        super(context);
    }
    public ColorlessFilterShapedVideoView(Context context, AttributeSet attrs) {
        super(context,attrs);
    }
    public boolean shouldDraw() {
        return true;
    }
    public void drawElements(Canvas canvas,Paint paint) {

    }
    public void handleTap(float x,float y) {

    }
    private class ColorlessFilter {
        private float x,y,deg = 0,scale = 0,degSpeed = 36,scaleSpeed = 0.1f,dir = 0;
        private int color = Color.parseColor("#AAFFFFFF");
        public ColorlessFilter(float x,float y,float deg,int color) {
            this.x = x;
            this.y = y;
            this.deg = deg;
            this.color = color;
        }
        public void draw(Canvas canvas,Paint paint) {
            paint.setStyle(Paint.Style.FILL);
            paint.setColor(color);
            canvas.save();
            canvas.translate(x,y);
            canvas.rotate(deg);
            canvas.scale(scale,scale);
            canvas.drawRect(new RectF(),paint);
            canvas.restore();
        }
        public void update() {
            deg+=degSpeed*dir;
        }
        public void startScalingDown() {
            dir = -1;
        }
        public void startScalingUp() {
            dir = 1;
        }
        public int hashCode() {
            return color+(int)deg+(int)dir;
        }
    }
}
