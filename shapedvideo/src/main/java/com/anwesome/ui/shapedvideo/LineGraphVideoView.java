package com.anwesome.ui.shapedvideo;

import android.content.Context;
import android.graphics.*;
import android.util.AttributeSet;

import java.util.Random;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Created by anweshmishra on 31/03/17.
 */
public class LineGraphVideoView extends ShapedVideoView {
    private int time = 0,w,h;
    private Random random = new Random();
    private ConcurrentLinkedQueue<LineGraph> lineGraphs = new ConcurrentLinkedQueue<>();
    public LineGraphVideoView(Context context) {
        super(context);
    }
    public LineGraphVideoView(Context context, AttributeSet attrs) {
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
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.parseColor("#99FF5722"));
        for(LineGraph lineGraph:lineGraphs) {
            lineGraph.draw(canvas,paint);
            lineGraph.update();
            if(lineGraph.isStop()) {
                lineGraphs.remove(lineGraph);
            }
        }
        if(time%20 == 0) {
            float x  = w/10+random.nextInt(w-w/10),y = w/10+random.nextInt(h-w/10);
            int n = 3+random.nextInt(4);
            lineGraphs.add(new LineGraph(x,y,n));
        }
        time++;
        try {
            Thread.sleep(100);
            invalidate();
        }
        catch (Exception ex) {

        }
    }
    public void handleTap(float x,float y) {
        for(LineGraph lineGraph:lineGraphs) {
            lineGraph.handleTap(x,y);
        }
    }
    private class LineGraph {
        private float x,y,wSize,hSize,gap,r,deg = 0,dir = 0;
        private  boolean stop = false;
        private int n;
        public LineGraph(float x,float y,int n) {
            this.x = x;
            this.y = y;
            this.n = n;
            gap = w/30;
            r = w/60;
            hSize = h/9;
            wSize = gap*(n-1);
        }
        public void draw(Canvas canvas,Paint paint) {
            canvas.save();
            canvas.translate(x,y);
            canvas.rotate(deg);
            paint.setStrokeWidth(r/8);
            float x1 = -wSize/2,y1 = 0;
            canvas.drawLine(-wSize/2,0,wSize/2,0,paint);
            for(int i=0;i<n;i++) {
                float y = random.nextInt((int)hSize);
                canvas.drawLine(x1,0,x1,-y,paint);
                canvas.drawCircle(x1,-y-r,r,paint);
                x1+=gap;
            }
            canvas.restore();
        }
        public void update() {
            deg+=dir*20;
            if(deg == 180) {
                dir = 0;
                stop = true;
            }
        }
        public boolean isStop() {
            return stop;
        }
        public void handleTap(float x,float y) {
            boolean condition = dir == 0 && !stop && x>=this.x-wSize/2 && x<=this.x+wSize/2 && y<=this.y && y>=this.y-hSize;
            if(condition) {
                dir = 1;
            }
        }
        public int hashCode() {
            return (int)(x+y+n);
        }
    }
}
