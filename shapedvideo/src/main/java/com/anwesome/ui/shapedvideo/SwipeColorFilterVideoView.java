package com.anwesome.ui.shapedvideo;

import android.content.Context;
import android.graphics.*;
import android.util.AttributeSet;

/**
 * Created by anweshmishra on 08/03/17.
 */
public class SwipeColorFilterVideoView extends ShapedVideoView {
    private int w,h,time = 0;
    public SwipeColorFilterVideoView(Context context, AttributeSet attrs) {
        super(context,attrs);
    }
    public SwipeColorFilterVideoView(Context context) {
        super(context);
    }
    public boolean shouldDraw() {
        return true;
    }
    public void drawElements(Canvas canvas,Paint paint) {
        if(time == 0) {
            w = canvas.getWidth();
            h = canvas.getHeight();
        }
        paint.setStyle(Paint.Style.FILL);
        time++;
    }
    private class SwipedColorFilter {
        private String colorHex = "#AA";
        private float x=0,y = 0,size,deg=0,dir = 1;
        public SwipedColorFilter(String colorHex) {
            this.colorHex += colorHex.replace("#","");
            x = w/2;
            y = h/2;
            size = w/2;
        }
        public void draw(Canvas canvas,Paint paint) {
            paint.setColor(Color.parseColor(colorHex));
            canvas.save();
            canvas.translate(x,y);
            canvas.rotate(deg);
            canvas.drawRoundRect(new RectF(-size/2,-size/2,size/2,size/2),size/8,size/8,paint);
            canvas.restore();
        }
        public void update() {
            x += dir*20;
            y-=Math.abs(dir)*10;
        }
        public int hashCode() {
            return (int)(x+y+deg);
        }
        public void startMoving(float dir) {
            this.dir = dir;
            deg = 30*dir;
        }
    }
}
