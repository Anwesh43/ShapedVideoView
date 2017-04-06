package com.anwesome.ui.shapedvideo;

import android.content.Context;
import android.graphics.*;
import android.util.AttributeSet;

/**
 * Created by anweshmishra on 06/04/17.
 */
public class FilterBarVideoView extends ShapedVideoView {
    private int time = 0,w,h;
    public FilterBarVideoView(Context context) {
        super(context);
    }
    public FilterBarVideoView(Context context, AttributeSet attrs) {
        super(context,attrs);
    }
    public boolean shouldDraw() {
        return true;
    }
    public void drawElements(Canvas canvas, Paint paint) {
        if(time == 0) {
            w = canvas.getWidth();
            h = canvas.getHeight();
        }
        time++;
    }
    public void handleTap(float x,float y) {

    }
    private class FilterBar {
        private float x,y,dir = 0,hSize = 0;
        public FilterBar(float x,float y) {
            this.x = x;
            this.y = y;
            this.hSize = h/10;
        }
        public void draw(Canvas canvas,Paint paint) {
            paint.setColor(Color.parseColor("#AA4DD0E1"));
            canvas.save();
            canvas.translate(x,y);
            canvas.drawRect(new RectF(0,0,w,hSize),paint);
            canvas.restore();
        }
        public void update() {
            hSize+=(h/10)*dir;
            if(hSize<=h/10 || hSize>=h) {
                dir = 0;
            }
            if(hSize>=h) {
                dir = 0;
            }
        }
        public int hashCode() {
            return (int)(x+y+dir);
        }
        public void handleTap(float x,float y) {
            if(x>this.x && y>=this.y) {

            }
        }
    }
    private class ArrowBtn {
        private float x,y,size,deg = 0,dir = 0;
        public ArrowBtn(float x,float y) {
            this.x = x;
            this.y = y;
            this.size = w/30;
            arrowBtn = new ArrowBtn(x,y);
        }
        public void draw(Canvas canvas,Paint paint) {
            canvas.save();
            canvas.translate(x,y);
            Path path = new Path();
            path.moveTo(-size,size);
            path.lineTo(0,0);
            path.lineTo(size,size);
            canvas.drawPath(path,paint);
            canvas.restore();
        }
        public void update() {
            deg+=dir*20;
            if(deg>=180 || deg<=0) {
                dir = 0;
            }
        }
        public boolean stop() {
            return dir == 0;
        }
        public boolean handleTap(float x,float y) {
            return x>=this.x-size && x<=this.x+size && y>=this.y && y<=this.y+size;
        }
    }
}
