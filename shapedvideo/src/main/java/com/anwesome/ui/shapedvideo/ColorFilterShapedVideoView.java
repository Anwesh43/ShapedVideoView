package com.anwesome.ui.shapedvideo;

import android.content.Context;
import android.graphics.*;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;

import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Created by anweshmishra on 27/02/17.
 */
public class ColorFilterShapedVideoView extends ShapedVideoView{
    private int time = 0;
    private boolean isAnimated = false;
    private int currIndex = 0;
    private ColorFilterRect currRect  = null,prevRect = null;
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
            if(prevRect!=null && currRect!=null) {
                prevRect.move();
                currRect.move();
                if(prevRect.stop() && currRect.stop()) {
                    isAnimated = false;
                    prevRect = null;
                    currRect = null;
                }
            }
            try {
                Thread.sleep(50);
                invalidate();
            }
            catch (Exception ex) {

            }
        }
    }
    private ColorFilterRect getIndex(int i) {
        int index = 0;
        ColorFilterRect colorFilterRect = null;
        for(ColorFilterRect rect:rects) {
            if(index == i) {
                colorFilterRect = rect;
                break;
            }
            index++;
        }
        return colorFilterRect;
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
        public float getNextX(int dir) {
            return x+w*dir;
        }
        public float getPrevX(int dir) {
            return x-w*dir;
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
        public void setX(float x) {
            this.x = x;
        }
    }
    private class FilterGestureListener extends GestureDetector.SimpleOnGestureListener {
        public boolean onDown(MotionEvent event) {
            return true;
        }
        public boolean onSingleTapUp(MotionEvent event) {
            return true;
        }
        public boolean onFling(MotionEvent e1,MotionEvent e2,float velx,float vely) {
            if(prevRect == null && currRect == null && !isAnimated) {
                if(e1.getX()<e2.getX()) {
                    prevRect = getIndex(currIndex);
                    if(currIndex == 0){
                        currRect = getIndex(rects.size()-1);
                    }
                    if(prevRect!=null  && currRect!=null){
                        prevRect.startMoving(1,prevRect.getNextX(1));
                        currRect.setX(prevRect.getPrevX(1));
                        currRect.startMoving(1,0);
                    }
                }
                else if(e1.getX()>e2.getX()) {
                    prevRect = getIndex(currIndex);
                    if(currIndex == rects.size()-1){
                        currRect = getIndex(0);
                    }
                    if(prevRect!=null  && currRect!=null){
                        prevRect.startMoving(-1,prevRect.getNextX(-1));
                        currRect.setX(prevRect.getPrevX(-1));
                        currRect.startMoving(-1,0);
                    }
                }
                if(prevRect!=null && currRect!=null) {
                    isAnimated = true;
                    postInvalidate();
                }
            }
            return true;
        }
    }
}
