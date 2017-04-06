package com.anwesome.ui.shapedvideo;

import android.content.Context;
import android.graphics.*;
import android.util.AttributeSet;

/**
 * Created by anweshmishra on 06/04/17.
 */
public class FilterBarVideoView extends ShapedVideoView {
    private int time = 0,w,h;
    private FilterBar filterBar;
    private boolean isAnimated = false;
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
            filterBar = new FilterBar();
        }
        filterBar.draw(canvas,paint);
        time++;
        if(isAnimated) {
            filterBar.update();
            if(filterBar.stop()) {
                isAnimated = false;
            }
            try {
                Thread.sleep(50);
                invalidate();
            } catch (Exception ex) {

            }
        }
    }
    public void handleTap(float x,float y) {
        if(filterBar!=null && !isAnimated && filterBar.handleTap(x,y)) {
            isAnimated = true;
            postInvalidate();
        }
    }
    private class FilterBar {
        private float x,y,dir = 0,hSize = 0;
        private ArrowBtn arrowBtn;
        public FilterBar() {
            this.x = 0;
            this.hSize = h/10;
            this.y = h-hSize;
            arrowBtn = new ArrowBtn(w/10,h/20);
        }
        public void draw(Canvas canvas,Paint paint) {
            paint.setColor(Color.parseColor("#AA4DD0E1"));
            canvas.save();
            canvas.translate(x,y);
            canvas.drawRect(new RectF(0,0,w,hSize),paint);
            arrowBtn.draw(canvas,paint);
            canvas.restore();
        }
        public void update() {
            hSize+=(h/10)*dir;
            y-=(h/10)*dir;
            arrowBtn.update();
            if((hSize<=h/10 || hSize>=h)) {
                dir = 0;
            }
        }
        public boolean stop() {
            return arrowBtn.stop() && dir == 0;
        }
        public int hashCode() {
            return (int)(x+y+dir);
        }
        public boolean handleTap(float x,float y) {
            if(x>this.x && y>=this.y && arrowBtn.handleTap(x-this.x,y-this.y)) {
                dir = (hSize<=h/10)?1:-1;
                arrowBtn.startMoving(dir);
                return true;
            }
            return false;
        }
    }
    private class ArrowBtn {
        private float x,y,size,deg = 0,dir = 0;
        public ArrowBtn(float x,float y) {
            this.x = x;
            this.y = y;
            this.size = w/30;

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
        public void startMoving(float dir) {
            this.dir = dir;
        }
        public boolean stop() {
            return dir == 0;
        }
        public boolean handleTap(float x,float y) {
            return x>=this.x-size && x<=this.x+size && y>=this.y && y<=this.y+size;
        }
    }
}
