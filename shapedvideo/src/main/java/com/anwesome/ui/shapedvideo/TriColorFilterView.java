package com.anwesome.ui.shapedvideo;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;

/**
 * Created by anweshmishra on 10/03/17.
 */
public class TriColorFilterView extends ShapedVideoView {
    private int w,h,time = 0;
    private TriColorFilter curr;
    public boolean shouldDraw() {
        return true;
    }
    public TriColorFilterView(Context context) {
        super(context);
    }
    public TriColorFilterView(Context context, AttributeSet attrs) {
        super(context);
    }
    public void drawElements(Canvas canvas, Paint paint) {
        if(time == 0) {
            w = canvas.getWidth();
            h = canvas.getHeight();
            String colorHexes[] = {"00BCD4","ef5350","F57F17","43A047","5E35B1"};
            curr = new TriColorFilter(colorHexes[0]);
            TriColorFilter root = curr;
            for(int i = 1;i<=4;i++) {
                root.next = new TriColorFilter(colorHexes[i]);
                root.next.prev = root;
                root = root.next;
            }
            root.next = curr;
            curr.prev = root;
        }
        time++;

    }
    public void handleTap(float x,float y) {

    }
    private class TriColorFilter {
        private float x,y,deg = 0,dir = 1,size = 100;
        private String colorHex = "#99";
        private TriColorFilter prev,next;
        public TriColorFilter(String colorHex) {
            x = w/2;
            y = h/2;
            size = h/4;
            this.colorHex += colorHex.replace("#","");
        }
        public void setPrev(TriColorFilter prev) {
            this.prev = prev;
        }
        public void setNext(TriColorFilter next) {
            this.next = next;
        }
        public void draw(Canvas canvas,Paint paint) {
            canvas.save();
            canvas.translate(x,y);
            canvas.rotate(deg);
            Path path = new Path();
            path.moveTo(-size/2,size/2);
            path.lineTo(size/2,size/2);
            path.lineTo(0,-size/2);
            path.lineTo(-size/2,size/2);
            canvas.drawPath(path,paint);
            canvas.restore();
        }
        public boolean stopped() {
            return dir == 0;
        }
        public void update() {
            if(dir == 1 && deg < 180) {
                deg+=36;
            }
            else if(dir == 1) {
                y+=h/12;
                if(y-size/2>=h) {
                    dir = 0;
                }
            }
            else if(dir == -1) {
                y-=h/12;
                if(y+size/2<=0) {
                    dir = 0;
                }
            }
        }
        public int hashCode() {
            return (int)(x+y+deg)+colorHex.hashCode();
        }
    }
}
