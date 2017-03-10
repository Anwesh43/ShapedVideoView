package com.anwesome.ui.shapedvideo;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;

/**
 * Created by anweshmishra on 10/03/17.
 */
public class TriColorFilterView extends ShapedVideoView {
    private int w,h,time = 0,dir = 0;
    private TriColorFilter curr;
    private boolean isAnimated = false;
    public boolean shouldDraw() {
        return true;
    }
    public TriColorFilterView(Context context) {
        super(context);
    }
    public TriColorFilterView(Context context, AttributeSet attrs) {
        super(context,attrs);
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
            initializePrevAndCurrFilters();
        }
        if(curr!=null) {
            paint.setStyle(Paint.Style.FILL);
            curr.draw(canvas,paint);
            if(curr.prev!=null) {
                curr.prev.draw(canvas,paint);
            }
            if(curr.next!=null) {
                curr.next.draw(canvas,paint);
            }
        }
        time++;
        if(isAnimated) {
            if(curr!=null) {
                curr.update();
                if(curr.prev!=null) {
                    curr.prev.update();
                }
                if(curr.next!=null) {
                    curr.next.update();
                }
                if(curr.stopped()) {
                    isAnimated = false;
                    if(curr.y-curr.size/2>=h) {
                        curr = curr.prev;
                    }
                    else if(curr.y+curr.size/2<=0) {
                        curr = curr.next;
                    }
                    if(curr!=null) {
                        initializePrevAndCurrFilters();
                    }
                }
            }
            try {
                Thread.sleep(100);
                invalidate();
            }
            catch (Exception ex) {

            }
        }
    }
    private void initializePrevAndCurrFilters() {
        curr.setStartingPosition(h/2);
        TriColorFilter prev = curr.prev;
        TriColorFilter next = curr.next;
        if(prev!=null) {
            prev.setStartingPosition(-next.size/2);
        }
        if(next!=null) {
            next.setStartingPosition(h+next.size/2);
        }
    }
    public void handleTap(float x,float y) {
        if(!isAnimated && curr!=null && dir == 0) {
            if(y<curr.y-curr.size/2) {
                dir = -1;
            }
            else if(y>curr.y+curr.size/2) {
                dir = 1;
            }
            isAnimated = true;
            postInvalidate();
        }
    }
    private class TriColorFilter {
        private float x,y,deg = 0,size = 100;
        private String colorHex = "#99";
        private TriColorFilter prev,next;
        public void setStartingPosition(float y) {
            this.y = y;
        }
        public TriColorFilter(String colorHex) {
            x = w/2;
            y = -h/2;
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
            paint.setColor(Color.parseColor(colorHex));
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
            if((dir == -1 && deg>0) || (dir == 1 && deg<180)) {
                deg+=dir*36;
            }
            else {
                y+=dir*(h/12);
                if(this.equals(curr) && ((dir == 1 && y-size/2>=h) || (dir == -1 && y+size/2<=0))) {
                    dir = 0;
                }
            }
        }
        public int hashCode() {
            return (int)(x+y+deg)+colorHex.hashCode();
        }
    }
}
