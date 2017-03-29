package com.anwesome.ui.shapedvideo;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import java.util.Random;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Created by anweshmishra on 29/03/17.
 */
public class DrawAvatarVideoView extends ShapedVideoView {
    private Random random = new Random();
    private ConcurrentLinkedQueue<Avatar> avatars = new ConcurrentLinkedQueue<>();
    private int time = 0,w,h;
    public DrawAvatarVideoView(Context context) {
        super(context);
    }
    public DrawAvatarVideoView(Context context, AttributeSet attrs) {
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
        paint.setStyle(Paint.Style.FILL);
        if(time%20 == 0) {
            float x = (w/16)+random.nextInt(w-(w/16)),y = w/16+random.nextInt(w-(w/16));
            avatars.add(new Avatar(x,y));
        }
        try {
            Thread.sleep(50);
            invalidate();;
        }
        catch (Exception ex) {

        }
    }
    public void handleTap(float x,float y) {

    }
    private class Avatar {
        private float x,y,size,deg = 0;
        public Avatar(float x,float y) {
            this.x = x;
            this.y = y;
            this.size = w/8;
        }
        public void drawAvatar(Canvas canvas,Paint paint) {
            paint.setColor(Color.parseColor("#99FFFFFF"));
            canvas.save();
            canvas.translate(x,y);
            canvas.rotate(deg);
            drawFace(canvas,paint);
            drawShoulderAndNeck(canvas,paint);
            canvas.restore();
        }
        public void update() {
            deg+=20;
        }
        private void drawFace(Canvas canvas,Paint paint) {
            canvas.drawCircle(0,-size/4,size/8,paint);
        }
        private void drawShoulderAndNeck(Canvas canvas,Paint paint) {
            Path path = new Path();
            path.moveTo(size/2,size/2);
            DrawingCurveUtil.drawCircularPath(path,0,size/2,size/2,360,300);
            float newY = (float)((size/2)*(1-Math.sqrt(3)));
            DrawingCurveUtil.drawCircularPath(path,0,newY,size/2,60,120);
            DrawingCurveUtil.drawCircularPath(path,0,size/2,size/2,240,180);
            path.lineTo(size/2,size/2);
            canvas.drawPath(path,paint);


        }

        public boolean handleTap(float x,float y) {
            return x>=this.x-size/2 && x<=this.x+size/2 && y>=this.y-size/2 && y<=this.y+size/2;
        }
    }
}
