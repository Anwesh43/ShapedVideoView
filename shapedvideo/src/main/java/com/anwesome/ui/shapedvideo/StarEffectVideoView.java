package com.anwesome.ui.shapedvideo;

import android.content.Context;
import android.graphics.*;
import android.util.AttributeSet;
import java.util.Random;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Created by anweshmishra on 25/03/17.
 */
public class StarEffectVideoView extends ShapedVideoView {
    private int w,h,time = 0,n=0,bounds[]=new int[2];
    private ConcurrentLinkedQueue<Star> stars = new ConcurrentLinkedQueue<>();
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
            bounds[0] = 0;
            bounds[1] = w/2;
        }
        for(Star star:stars) {
            star.draw(canvas,paint);
            star.update();
            if(star.stopped()) {
                stars.remove(star);
            }
        }
        if(time%6 == 0) {
            Random random = new Random();
            stars.add(new Star(bounds[n%2]+random.nextInt(w/2),0));
            n++;
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
        public Star(float x,float y) {
            this.x = x;
            this.y = y;
            this.r = w/15;
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
            paint.setColor(Color.parseColor("#88FFD54F"));
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
