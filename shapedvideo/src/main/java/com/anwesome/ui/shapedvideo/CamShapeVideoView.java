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
 * Created by anweshmishra on 27/03/17.
 */
public class CamShapeVideoView extends ShapedVideoView {
    private int time = 0,w,h;
    private ConcurrentLinkedQueue<CamShape> camShapes = new ConcurrentLinkedQueue<>();
    public CamShapeVideoView(Context context) {
        super(context);
    }
    public CamShapeVideoView(Context context, AttributeSet attrs) {
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
        for(CamShape camShape:camShapes) {
            camShape.draw(canvas,paint);
            camShape.update();
            if(camShape.stopped) {
                camShapes.remove(camShape);
            }
        }
        if(time % 20 == 0) {
            Random random = new Random();
            camShapes.add(new CamShape(random.nextInt(w),0));
        }
        time++;
        try {
            Thread.sleep(50);
            invalidate();
        }
        catch (Exception ex) {

        }
    }
    public void handleTap(float x,float y) {
        for(CamShape camShape:camShapes) {
            if(camShape.handleTap(x,y)) {
                camShapes.remove(camShape);
            }
        }
    }
    private class CamShape {
        private boolean stopped = false;
        private float deg = 0,x,y,size;
        public CamShape(float x,float y) {
            this.x = x;
            this.y = y;
            this.size = w/6;
        }
        public void draw(Canvas canvas,Paint paint) {
            canvas.save();
            canvas.translate(x,y);
            canvas.rotate(deg);
            drawCamShape(canvas,paint);
            canvas.restore();
        }
        public void drawCamShape(Canvas canvas,Paint paint) {
            paint.setStyle(Paint.Style.FILL);
            paint.setColor(Color.parseColor("#99616161"));
            canvas.drawRoundRect(new RectF(-size/2,-size/3,size/2,size/3),size/4,size/6,paint);
            canvas.drawRoundRect(new RectF(-size/5,-size/2,size/5,-size/2+size/5),size/10,size/15,paint);
            paint.setStrokeWidth(size/10);
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(Color.parseColor("#99263238"));
            canvas.drawCircle(0,0,size/8,paint);
        }
        public void update() {
            y+=h/30;
            deg+=15;
            if(y>=h) {
                stopped = true;
            }
        }
        public boolean handleTap(float x,float y) {
            return  (x>=this.x-size) && x<=this.x+size && y>=this.y-size && y<=this.y+size;
        }
        public int hashCode() {
            return (int)(x+y+deg);
        }
    }
}
