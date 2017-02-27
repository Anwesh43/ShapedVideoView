package com.anwesome.ui.shapedvideo;

import android.content.Context;
import android.graphics.*;
import android.util.AttributeSet;
import android.view.MotionEvent;

import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Created by anweshmishra on 27/02/17.
 */
public class ColorFilterShapedVideoView extends ShapedVideoView{
    private int time = 0;
    private boolean isAnimated = false;
    private ConcurrentLinkedQueue<ColorFilterRect> rects = new ConcurrentLinkedQueue<>();
    private String colorHexes[] = {"#AAF57C00","#AAE53935","#AA43A047","#AA0277BD"};
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
        if(time == 0) {
            initRects(canvas.getWidth(),canvas.getHeight());
        }
        for(ColorFilterRect rect:rects) {
            rect.draw(canvas,paint);
        }
        time++;
        if(isAnimated) {
            try {
                Thread.sleep(50);
                invalidate();
            }
            catch (Exception ex) {

            }
        }
    }
    private void initRects(int w,int h) {
        for(int i=0;i<colorHexes.length;i++) {
            rects.add(new ColorFilterRect(i*w,0,w,h,Color.parseColor(colorHexes[i])));
        }
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
            paint.setColor(color);
            canvas.drawRect(new RectF(x,y,x+w,y+h),paint);
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
