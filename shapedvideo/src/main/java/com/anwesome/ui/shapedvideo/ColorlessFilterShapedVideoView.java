package com.anwesome.ui.shapedvideo;

import android.content.Context;
import android.graphics.*;
import android.util.AttributeSet;

import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Created by anweshmishra on 28/02/17.
 */
public class ColorlessFilterShapedVideoView extends ShapedVideoView {
    private boolean isAnimated = false;
    private ColorlessFilter current=null,previous=null;
    private ConcurrentLinkedQueue<ColorlessFilter> colorlessFilters = new ConcurrentLinkedQueue<>();
    private int currIndex = 0,time = 0;
    private int w = 200,h =200;
    public ColorlessFilterShapedVideoView(Context context) {
        super(context);
    }
    public ColorlessFilterShapedVideoView(Context context, AttributeSet attrs) {
        super(context,attrs);
    }
    public ColorlessFilter getColorlessFilter(int index) {
        int i = 0;
        for(ColorlessFilter colorlessFilter:colorlessFilters) {
            if(i == index) {
                return colorlessFilter;
            }
            i++;
        }
        return null;
    }
    public void initColorFilters() {
        String colorHexes[] = {"#9E9E9E","#212121","#37474F","#757575","#90A4AE","#B0BEC5"};
        for(String colorHex:colorHexes) {
            colorlessFilters.add(new ColorlessFilter(colorHex));
        }

    }
    public boolean shouldDraw() {
        return true;
    }
    public void drawElements(Canvas canvas,Paint paint) {
        if(time == 0) {
            w = canvas.getWidth();
            h = canvas.getHeight();
            initColorFilters();
        }
        for(ColorlessFilter colorlessFilter:colorlessFilters) {
            colorlessFilter.draw(canvas,paint);
        }
        time++;
        if(isAnimated) {
            if(previous == null) {
                updateCurrent();
            }
            else {
                if(previous.isStop()) {
                    updateCurrent();
                }
                else {
                    previous.update();
                    if(previous.isStop()) {
                        current.startAppearing();
                    }
                }
            }
            try {
                Thread.sleep(50);
                invalidate();
            } catch (Exception ex) {

            }
        }
    }
    private void updateCurrent() {
        if(current!=null) {
            current.update();
            if(current.isStop()) {
                previous = current;
                current = null;
                currIndex++;
                currIndex%=colorlessFilters.size();
                isAnimated = false;
            }
        }
    }
    public void handleTap(float x,float y) {
        if(!isAnimated && current == null)  {
            current = getColorlessFilter(currIndex);
            if(current!=null) {
                if(previous == null) {
                    current.startAppearing();
                }
                else {
                    previous.startDisappearing();
                }
                isAnimated = true;
                postInvalidate();

            }
        }
    }
    private class ColorlessFilter {
        private float deg = 0,scale = 0,degSpeed = 36,scaleSpeed = 0.1f,dir = 0;
        private String colorHex ="#AA";
        public ColorlessFilter(String colorHex) {
            this.colorHex += colorHex.replace("#","");
        }
        public void draw(Canvas canvas,Paint paint) {
            paint.setStyle(Paint.Style.FILL);
            paint.setColor(Color.parseColor(colorHex));
            canvas.save();
            canvas.translate(w/2,h/2);
            canvas.rotate(deg);
            canvas.scale(scale,scale);
            canvas.drawRect(new RectF(-w/2,-h/2,w/2,h/2),paint);
            canvas.restore();
        }
        public void update() {
            deg+=degSpeed*dir;
            scale+=scaleSpeed*dir;
            if(deg>=360 && scale>=1.0f) {
                dir = 0;
                scale = 1;
                deg = 360;
            }
            if(deg<=0 && scale<=0) {
                dir = 0;
                scale = 0;
                deg = 0;
            }
        }
        public boolean isStop() {
            return dir == 0;
        }
        public void startAppearing() {
            dir = 1;
        }
        public void startDisappearing() {
            dir = -1;
        }
        public int hashCode() {
            return colorHex.hashCode()+(int)deg+(int)dir;
        }
    }
}
