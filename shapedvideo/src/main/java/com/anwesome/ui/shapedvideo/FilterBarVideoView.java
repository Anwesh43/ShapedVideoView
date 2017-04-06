package com.anwesome.ui.shapedvideo;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;

/**
 * Created by anweshmishra on 06/04/17.
 */
public class FilterBarVideoView extends ShapedVideoView {
    private int time = 0,w,h;
    public FilterBarVideoView(Context context) {
        super(context);
    }
    public FilterBarVideoView(Context context, AttributeSet attrs) {
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
        time++;
    }
    public void handleTap(float x,float y) {

    }
    private class FilterBar {
        private float x,y,dir = 0,hSize = 0;
        public FilterBar(float x,float y) {
            this.x = x;
            this.y = y;
            this.hSize = h/10;
        }
        public void draw(Canvas canvas,Paint paint) {
            paint.setColor(Color.parseColor("#AA4DD0E1"));
            canvas.save();
            canvas.translate(x,y);
            canvas.drawRect(new RectF(0,0,w,hSize),paint);
            canvas.restore();
        }
        public void update() {
            hSize+=(h/10)*dir;
            if(hSize<=h/10 || hSize>=h) {
                dir = 0;
            }
            if(hSize>=h) {
                dir = 0;
            }
        }
        public int hashCode() {
            return (int)(x+y+dir);
        }
    }
}
