package com.anwesome.ui.shapedvideo;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import java.util.concurrent.ConcurrentLinkedQueue;


/**
 * Created by anweshmishra on 14/03/17.
 */
public class DrawDotCircleVideoView extends ShapedVideoView{
    private ConcurrentLinkedQueue<DotCircle> dotCircles = new ConcurrentLinkedQueue<>();
    private final float dot_radius = 10,rSpeed = 15,gap = 10,dotCircleRadius=100;
    public DrawDotCircleVideoView(Context context, AttributeSet attrs) {
        super(context,attrs);
    }
    public DrawDotCircleVideoView(Context context) {
        super(context);
    }
    protected boolean shouldDraw() {
        return true;
    }
    public void drawElements(Canvas canvas,Paint paint) {
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.parseColor("#99f44336"));
        for(DotCircle dotCircle:dotCircles) {
            dotCircle.draw(canvas,paint);
            dotCircle.update();
        }
        try {
            Thread.sleep(50);
            invalidate();
        }
        catch (Exception ex) {

        }
    }
    public void handleTap(float x,float y) {
        dotCircles.add(new DotCircle(x,y));
    }
    private class DotCircle {
        private int index = 0;
        private ConcurrentLinkedQueue<Dot> dots = new ConcurrentLinkedQueue<>();
        private float x,y;
        public DotCircle(float x,float y) {
            initDots();
            this.x = x;
            this.y = y;
        }
        private Dot getDot(int index) {
            int i =0 ;
            for(Dot dot:dots) {
                if(i == index) {
                    return dot;
                }
                i++;
            }
            return null;
        }
        public void initDots() {
            int n = 36;
            for(int i=0;i<n;i++) {
                dots.add(new Dot(i*gap,dotCircleRadius));
            }
            Dot firstDot = getDot(0);
            if(firstDot!=null) {
                firstDot.startMoving();
            }
        }
        public void draw(Canvas canvas,Paint paint) {
            canvas.save();
            canvas.translate(x,y);
            for(Dot dot:dots) {
                dot.draw(canvas,paint);
            }
            canvas.restore();
        }
        public void update() {
            int index = 0;
            for(Dot dot:dots) {
                dot.update();
                if(dot.neighborShouldMove) {
                    Dot nextDot = getDot(index+1);
                    if(nextDot!=null) {
                        nextDot.startMoving();
                    }
                    dot.neighborShouldMove = false;
                }
                index++;
            }
        }
        public int hashCode() {
            return dots.hashCode()+index;
        }

    }
    private class Dot {
        private float dir = 0,r,deg,initR;
        private boolean neighborShouldMove = false;
        public Dot(float deg,float r) {
            this.deg = deg;
            this.r = r;
            this.initR = r;
        }
        public void draw(Canvas canvas, Paint paint) {
            float x = (float)(r*Math.cos(deg*Math.PI/180)), y = (float)(r*Math.sin(deg*Math.PI/180));
            canvas.drawCircle(x,y,dot_radius,paint);
        }
        public void update() {
            if(dir !=0) {
                r += rSpeed * dir;
                if (r >= initR + rSpeed * 6) {
                    dir = -1;
                    neighborShouldMove = true;
                }
                if (r <= initR) {
                    dir = 0;
                    r = initR;
                }
            }
        }
        public void startMoving() {
            if(dir == 0) {
                dir = 1;
            }
        }
        public int hashCode() {
            return (int)(deg+r);
        }
    }
}
