package com.anwesome.ui.shapedvideo;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

/**
 * Created by anweshmishra on 11/03/17.
 */
public class DrawInVideoView {
    private class CircularColor {
        private float x,y,r,scale = 0.4f,dir=0;
        private String colorHex="#99";
        public CircularColor(String colorHex,float x,float y,float r) {
            this.colorHex += colorHex.replace("#","");
            this.x = x;
            this.y = y;
            this.r = r;
        }
        public boolean handleTap(float x,float y) {
            boolean condition = x>=this.x-r && x<=this.x+r && y>=this.y-r && y<=this.y+r;
            return condition;
        }
        public void startUpdating(float dir) {
            this.dir = dir;
        }
        public void draw(Canvas canvas, Paint paint) {
            paint.setStyle(Paint.Style.FILL);
            paint.setColor(Color.parseColor(colorHex));
            canvas.save();
            canvas.translate(x,y);
            canvas.scale(scale,scale);
            canvas.drawCircle(0,0,r,paint);
            canvas.restore();
        }
        public void update() {
            this.scale+=0.1f*dir;
            if(this.scale>=1) {
                this.scale = 1;
                this.dir = 0;
            }
        }
        public int hashCode() {
            return (int)(x+scale)+colorHex.hashCode();
        }
    }
}
