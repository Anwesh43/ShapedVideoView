package com.anwesome.ui.shapedvideo;

import android.content.Context;
import android.graphics.*;
import android.util.AttributeSet;

import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Created by anweshmishra on 28/02/17.
 */
public class ColorlessFilterShapedVideoView extends ShapedVideoView {
    private ConcurrentLinkedQueue<ColorlessFilter> colorlessFilters = new ConcurrentLinkedQueue<>();
    private int currIndex = 0,time = 0;
    private int w = 200,h =200;
    public ColorlessFilterShapedVideoView(Context context) {
        super(context);
    }
    public ColorlessFilterShapedVideoView(Context context, AttributeSet attrs) {
        super(context,attrs);
    }
    public ColorlessFilter get(int index) {
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
        String colorHexes[] = {"#9E9E9E","#BDBDBD","#9E9E9E","#757575","#90A4AE","#B0BEC5"};
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
        time++;
    }
    public void handleTap(float x,float y) {

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
        }
        public void startScalingDown() {
            dir = -1;
        }
        public void startScalingUp() {
            dir = 1;
        }
        public int hashCode() {
            return colorHex.hashCode()+(int)deg+(int)dir;
        }
    }
}
