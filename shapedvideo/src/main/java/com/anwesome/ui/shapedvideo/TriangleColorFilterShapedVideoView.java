package com.anwesome.ui.shapedvideo;

import android.content.Context;
import android.graphics.*;
/**
 * Created by anweshmishra on 06/03/17.
 */
public class TriangleColorFilterShapedVideoView extends ShapedVideoView {
    private int w,h,time = 0;
    public TriangleColorFilterShapedVideoView(Context context) {
        super(context);
    }
    protected boolean shouldDraw() {
        return true;
    }
    public void drawElements(Canvas canvas,Paint paint) {
        if(time == 0) {
            w = canvas.getWidth();
            h = canvas.getHeight();
        }
        paint.setStyle(Paint.Style.FILL);
        time++;
    }
    public void handleTap(float x,float y) {

    }
    private class TriangleColorFilter {
        private float x,y,size,scale=0,rot=0,dir =0;
        private String colorHex = "#99";
        public TriangleColorFilter(String colorHex) {
            this.x = w/2;
            this.y = h/2;
            this.size = w/4;
            this.colorHex+=colorHex.replace("#","");
        }
        public void draw(Canvas canvas,Paint paint) {
            paint.setColor(Color.parseColor(colorHex));
            canvas.save();
            canvas.translate(x,y);
            canvas.scale(scale,scale);
            canvas.rotate(rot);
            canvas.restore();
        }
        public void update() {
            rot+=10*dir;
        }
        public void show() {
            dir  = 1;
        }
        public void clear() {
            dir = -1;
        }
        public boolean stopped() {
            return dir == 0;
        }
        public int hashCode() {
            return (int)(scale+rot)+colorHex.hashCode();
        }
    }
}
