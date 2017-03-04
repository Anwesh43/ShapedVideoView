package com.anwesome.ui.shapedvideo;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;

/**
 * Created by anweshmishra on 04/03/17.
 */
public class LazyLoadShapedVideoView extends ShapedVideoView {
    private int time = 0;
    private Loader loader;
    public LazyLoadShapedVideoView(Context context, AttributeSet attrs) {
        super(context,attrs);
    }
    public void drawElements(Canvas canvas,Paint paint) {
        if(time == 0) {
            loader = new Loader(canvas.getWidth()/2,canvas.getHeight()/2,canvas.getWidth()/3);
        }
        time++;
        if(time < 24) {
            loader.draw(canvas,paint);
            try {
                Thread.sleep(100);
                invalidate();
            }
            catch (Exception ex) {

            }
        }
        else {
            super.start();
        }

    }
    public void start() {

    }
    private class Loader {
        private float x,y,deg = 0,radius;
        public Loader(float x,float y,float radius) {
            this.x = x;
            this.y = y;
            this.radius = radius;
        }
        public void draw(Canvas canvas,Paint paint) {
            paint.setColor(Color.GRAY);
            paint.setStrokeWidth(20);
            canvas.save();
            canvas.translate(x,y);
            canvas.drawArc(new RectF(-radius,-radius,radius,radius),deg,deg+30,false,paint);
            canvas.restore();
        }
        public void update() {
            deg+=30;
        }
    }
}
