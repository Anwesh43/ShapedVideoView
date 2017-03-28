package com.anwesome.ui.shapedvideo;

import android.content.Context;
import android.graphics.*;
import android.util.AttributeSet;

import java.util.Random;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Created by anweshmishra on 28/03/17.
 */
public class HeartDrawnVideoView extends ShapedVideoView {
    private int w,h,time = 0;
    private ConcurrentLinkedQueue<Heart> hearts = new ConcurrentLinkedQueue<>();
    public HeartDrawnVideoView(Context context) {
        super(context);
    }
    public HeartDrawnVideoView(Context context, AttributeSet attrs) {
        super(context,attrs);
    }
    protected boolean shouldDraw() {
        return true;
    }
    public void drawElements(Canvas canvas,Paint paint) {
        if(time == 0) {
            w = canvas.getWidth();
            h = canvas.getHeight();
        }
        for(Heart heart:hearts) {
            heart.draw(canvas,paint);
            heart.update();
            if(heart.stopped()) {
                hearts.remove(heart);
            }
        }
        if(time%10 == 0) {
            Random random = new Random();
            hearts.add(new Heart(random.nextInt(w),random.nextInt(h)));
        }
        time++;
        try {
            Thread.sleep(100);
            invalidate();
        }
        catch (Exception ex) {

        }
    }
    public void handleTap(float x,float y) {
        for(Heart heart:hearts) {
            if(heart.handleTap(x,y)) {
                heart.startRotatingDown();
            }
        }
    }
    private class Heart {
        private HeartState heartState = new HeartState();
        private HeartAnimationController controller = new HeartAnimationController(heartState);
        private float x,y,r;
        public Heart(float x,float y) {
            this.x = x;
            this.y = y;
            this.r = w/6;
        }
        public void draw(Canvas canvas,Paint paint) {
            paint.setStyle(Paint.Style.FILL);
            paint.setColor(Color.RED);
            canvas.save();
            canvas.translate(x,y);
            canvas.rotate(heartState.getDeg());
            canvas.scale(heartState.getScale(),heartState.getScale());
            Path path = new Path();
            path.moveTo(r/4,0);
            path.lineTo(0,r/2);
            path.lineTo(-r/4,0);
            path.addArc(new RectF(-r/4,-r/8,0,r/8),180,180);
            path.addArc(new RectF(0,-r/8,r/4,r/8),180,180);
            canvas.drawPath(path,paint);
            canvas.restore();
        }
        public void update() {
            controller.animate();
        }
        public boolean stopped() {
            return heartState.stopped();
        }
        public void startRotatingDown() {
            controller.startRotatingDown();
        }
        public int hashCode() {
            return (int)(x+y);
        }
        public boolean handleTap(float x,float y) {
            return (x>=this.x-1.5f*r && x<=this.x+1.5f*r && y>=this.y-1.5f*r && y<=this.y+1.5f*r);
        }
    }
    private class HeartState {
        private float deg=0,scale = 0.8f,scaleDir = 1,dir = 0;
        private boolean stop = false;
        public float getDeg() {
            return deg;
        }
        public float getScale() {
            return scale;
        }
        public void setScaleDir(float scaleDir) {
            this.scaleDir = scaleDir;
        }
        public void setDir(float dir) {
            this.dir = dir;
        }
        public void scaleUpDown() {
            scale+=scaleDir;
            if(scale>=1.2f) {
                scaleDir = -1;
            }
            if(scale<=0.8f) {
                scaleDir = 1;
            }
        }
        public void rotateDown() {
            deg -= (360/scale);
            scale-=0.1f;
            if(scale<=0) {
                stop = true;
            }
        }
        public int hashCode() {
            return (int)(deg+scale+scaleDir);
        }
        public boolean stopped() {
            return stop;
        }
    }
    private class HeartAnimationController {
        private HeartState heartState;
        private HeartAnimationState animationState = HeartAnimationState.SCALE_UP_DOWN;
        public HeartAnimationController(HeartState heartState) {
            this.heartState = heartState;
        }
        public void startRotatingDown() {
            animationState = HeartAnimationState.ROTATE_DOWN;
        }
        public void animate() {
            switch (animationState) {
                case SCALE_UP_DOWN:
                    heartState.scaleUpDown();
                    break;
                case ROTATE_DOWN:
                    heartState.rotateDown();
                    break;
                default:
                    break;
            }
        }
    }
    private enum HeartAnimationState {
        ROTATE_DOWN,SCALE_UP_DOWN;
    }
}
