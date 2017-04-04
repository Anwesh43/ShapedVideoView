package com.anwesome.ui.shapedvideo;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;

import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Created by anweshmishra on 04/04/17.
 */
public class CircMoveVideoView extends ShapedVideoView {
    private int w,h,time = 0,SPEED_CONTROLLER = 5;
    private CircMove currentCirc;
    private ConcurrentLinkedQueue<CircMove> circMoves = new ConcurrentLinkedQueue<>();
    public CircMoveVideoView(Context context) {
        super(context);
    }
    public CircMoveVideoView(Context context, AttributeSet attrs) {
        super(context,attrs);
    }
    public void drawElements(Canvas canvas, Paint paint) {
        if(time == 0) {
            w = canvas.getWidth();
            h = canvas.getHeight();
            CircMove circMove = new CircMove(w/2,h/2);
            circMoves.add(circMove);
            currentCirc = circMove;
        }
        paint.setColor(Color.parseColor("#99FF5722"));
        paint.setStyle(Paint.Style.FILL);
        for(CircMove circMove:circMoves) {
            circMove.draw(canvas,paint);
            circMove.update();
            if(circMove.stop()) {
                currentCirc = circMove;
            }
        }
        time++;
        try {
            Thread.sleep(50);
            invalidate();
        }
        catch(Exception ex) {

        }
    }
    public boolean shouldDraw() {
        return true;
    }
    public void handleTap(float x,float y) {
        if(currentCirc!=null && currentCirc.stop()) {
            CircMove circMove = new CircMove(currentCirc.getX(),currentCirc.getY());
            circMove.setSpeed(x,y);
            circMoves.add(circMove);
        }
    }
    private class CircMove {
        private float x,y,k=0,xSpeed = 0,ySpeed = 0;
        public float getX() {
            return x;
        }
        public float getY() {
            return y;
        }
        public CircMove(float x,float y) {
            this.x = x;
            this.y = y;
        }
        public void setSpeed(float x,float y) {
            this.xSpeed = (x - this.x)/SPEED_CONTROLLER;
            this.ySpeed = (y-this.y)/SPEED_CONTROLLER;
        }
        public void draw(Canvas canvas,Paint paint) {
            canvas.drawCircle(x,y,w/10,paint);
        }
        public void update() {
            x+=xSpeed;
            y+=ySpeed;
            k++;
            if(k == SPEED_CONTROLLER) {
                xSpeed = 0;
                ySpeed = 0;
            }
        }
        public boolean stop() {
            return xSpeed == 0 && ySpeed == 0;
        }
        public int hashCode() {
            return (int)(x+y+xSpeed+ySpeed);
        }
    }
}
