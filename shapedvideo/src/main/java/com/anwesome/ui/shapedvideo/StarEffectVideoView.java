package com.anwesome.ui.shapedvideo;

import android.content.Context;
import android.graphics.*;
import android.util.AttributeSet;

/**
 * Created by anweshmishra on 25/03/17.
 */
public class StarEffectVideoView extends ShapedVideoView {
    private int w,h,time = 0;
    public StarEffectVideoView(Context context) {
        super(context);
    }
    public StarEffectVideoView(Context context, AttributeSet attrs) {
        super(context,attrs);
    }
    public void drawElements(Canvas canvas,Paint paint) {
        if(time == 0) {
            w = canvas.getWidth();
            h = canvas.getHeight();
        }
        time++;
        try {
            Thread.sleep(50);
            invalidate();
        }
        catch (Exception ex) {

        }
    }
    public boolean shouldDraw() {
        return true;
    }
    private class Star {
        private float x,y,deg=0,r;
        public Star(float x,float y,float deg) {
            this.x = x;
            this.y = y;
            this.deg = deg;
            this.r = w/10;
        }
        public void draw(Canvas canvas,Paint paint) {
            canvas.save();
            canvas.translate(x,y);
            canvas.rotate(deg);
            drawStar(canvas,paint);
            canvas.restore();
        }
        public void drawStar(Canvas canvas,Paint paint) {
            Path path = new Path();
            float theta = 0;
            paint.setStyle(Paint.Style.FILL);
            paint.setColor(Color.parseColor("#889E9E9E"));
            path.moveTo(r/2,0);
            for(int i =0;i<6;i++) {
                PointF pr = getPointInCircle(r,theta+30);
                PointF pr2 = getPointInCircle(r/2,theta+60);
                path.lineTo(pr.x,pr.y);
                path.lineTo(pr2.x,pr2.y);
                theta+=60;
            }
            canvas.drawPath(path,paint);
        }
        private PointF getPointInCircle(float radius,float theta) {
            return  new PointF((float)(radius*Math.cos(theta*Math.PI/180)),(float)(radius*Math.sin(theta*Math.PI/180)));
        }
        public void update() {
            y+=h/30;
            deg+=15;
        }
        public boolean stopped() {
            return y>h;
        }
        public int hashCode() {
            return (int)(x+y+deg);
        }
    }
}
