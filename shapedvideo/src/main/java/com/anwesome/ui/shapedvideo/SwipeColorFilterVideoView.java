package com.anwesome.ui.shapedvideo;

import android.content.Context;
import android.graphics.*;
import android.util.AttributeSet;
import android.view.*;
import java.util.*;

/**
 * Created by anweshmishra on 08/03/17.
 */
public class SwipeColorFilterVideoView extends ShapedVideoView {
    private int w,h,time = 0,indexDir=0;
    private boolean isAnimated = false;
    private GestureDetector swipeGestureDetector;
    private List<SwipedColorFilter> colorFilters = new ArrayList<>();
    private int currentIndex = 0;
    public SwipeColorFilterVideoView(Context context, AttributeSet attrs) {
        super(context,attrs);
        swipeGestureDetector = new GestureDetector(context,new SwipeGestureListener());
    }
    public SwipeColorFilterVideoView(Context context) {
        super(context);
        swipeGestureDetector = new GestureDetector(context,new SwipeGestureListener());
    }
    public boolean shouldDraw() {
        return true;
    }
    public void drawElements(Canvas canvas,Paint paint) {
        if(time == 0) {
            w = canvas.getWidth();
            h = canvas.getHeight();
            String colorHexes[] = {"f44336","3F51B5","0097A7","E91E63"};
            for(String colorHex:colorHexes) {
                colorFilters.add(new SwipedColorFilter(colorHex));
            }
        }
        paint.setStyle(Paint.Style.FILL);
        for(SwipedColorFilter colorFilter:colorFilters) {
            colorFilter.draw(canvas,paint);
        }
        time++;
        if(isAnimated) {
            if(currentIndex<colorFilters.size()) {
                colorFilters.get(currentIndex).update();
                if(colorFilters.get(currentIndex).stopped()) {
                    currentIndex+=indexDir;
                    if(currentIndex>=colorFilters.size()) {
                        currentIndex = 0;
                    }
                    if(currentIndex<0) {
                        currentIndex = colorFilters.size()-1;
                    }
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
    public boolean onTouchEvent(MotionEvent event) {
        return swipeGestureDetector.onTouchEvent(event);
    }
    private class SwipeGestureListener extends GestureDetector.SimpleOnGestureListener {
        public boolean onDown(MotionEvent event) {
            return true;
        }
        public boolean onSingleTapUp(MotionEvent event) {
            return true;
        }
        public boolean onFling(MotionEvent e1,MotionEvent e2,float velx,float vely) {
            if(!isAnimated && currentIndex<colorFilters.size()) {
                indexDir = e1.getX()<e2.getX()?1:-1;
                colorFilters.get(currentIndex).startMoving(indexDir);
                isAnimated = true;
                postInvalidate();
            }
            return true;
        }
    }
    private class SwipedColorFilter {
        private String colorHex = "#AA";
        private float x=0,y = 0,size,deg=0,dir = 0;
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
        public boolean stopped() {
            return dir == 0;
        }
        public void update() {
            x += dir*20;
            y-=Math.abs(dir)*10;
            if(x+size<0 || x-size>=w) {
                dir = 0;
            }
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
