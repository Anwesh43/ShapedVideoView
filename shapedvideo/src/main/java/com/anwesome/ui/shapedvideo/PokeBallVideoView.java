package com.anwesome.ui.shapedvideo;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;

import java.util.Random;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Created by anweshmishra on 17/03/17.
 */
public class PokeBallVideoView extends ShapedVideoView {
    private int w,h,time = 0;
    private ConcurrentLinkedQueue<PokeBall> pokeBalls = new ConcurrentLinkedQueue<>();
    public PokeBallVideoView(Context context) {
        super(context);
    }
    public PokeBallVideoView(Context context, AttributeSet attrs) {
        super(context,attrs);
    }
    protected boolean shouldDraw() {
        return true;
    }
    public void drawElements(Canvas canvas, Paint paint) {
        if(time == 0) {
            w = canvas.getWidth();
            h = canvas.getHeight();
        }
        createBalls();
        for(PokeBall pokeBall:pokeBalls) {
            pokeBall.draw(canvas,paint);
            pokeBall.update();
            if(pokeBall.opened) {
                pokeBalls.remove(pokeBall);
            }
        }
        time++;
        try {
            Thread.sleep(50);
            invalidate();
        }
        catch (Exception ex) {

        }
    }
    private void createBalls() {
        if(time%10 == 5) {
            Random random = new Random();
            pokeBalls.add(new PokeBall(random.nextInt(w),random.nextInt(h)));
        }
    }
    public void handleTap(float x,float y) {
        for(PokeBall pokeBall:pokeBalls) {
            pokeBall.handleTap(x,y);
        }
    }
    private class PokeBall {
        private boolean opened = false;
        private float x,y,deg = 0,r=10,dir = 0;
        public PokeBall(float x,float y) {
            this.x = x;
            this.y = y;
            this.r = w/6;
        }
        public void update() {
            deg-=dir*9;
            if(deg<=-90) {
               opened = true;
            }
        }
        public void draw(Canvas canvas,Paint paint){
            canvas.save();
            canvas.translate(x,y);
            paint.setStyle(Paint.Style.FILL);
            paint.setColor(Color.parseColor("#EEEEEE"));
            canvas.drawArc(new RectF(-r,-r,r,r),0,180,true,paint);
            canvas.drawCircle(0,0,r/20,paint);
            canvas.drawCircle(0,0,r/40,paint);
            paint.setColor(Color.parseColor("#263238"));
            paint.setStyle(Paint.Style.STROKE);
            paint.setStrokeWidth(10);
            canvas.drawCircle(0,0,r/20,paint);
            paint.setStrokeWidth(7);
            canvas.drawCircle(0,0,r/40,paint);
            paint.setColor(Color.parseColor("#f44336"));
            paint.setStyle(Paint.Style.FILL);
            canvas.save();
            canvas.translate(-r,0);
            canvas.rotate(deg);
            canvas.drawArc(new RectF(0,-r,2*r,r),180,180,true,paint);
            canvas.restore();
            canvas.restore();
        }
        public void handleTap(float x,float y) {
            if(this.x>=this.x-r && x<=this.x+r && y>=this.y-r && y<=this.y+r && dir == 0) {
                dir = 1;
            }
        }
        public int hashCode() {
            return (int)(x+y+deg);
        }
    }
}
