package com.anwesome.ui.shapedvideo;

import android.content.Context;
import android.graphics.*;
import android.util.AttributeSet;

/**
 * Created by anweshmishra on 28/03/17.
 */
public class HeartDrawnVideoView extends ShapedVideoView {
    private int w,h,time = 0;
    public HeartDrawnVideoView(Context context) {
        super(context);
    }
    public HeartDrawnVideoView(Context context, AttributeSet attrs) {
        super(context,attrs);
    }
    public void drawElements(Canvas canvas,Paint paint) {
        if(time == 0) {
            w = canvas.getWidth();
            h = canvas.getHeight();
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

    }
    private class Heart {
        private HeartState heartState = new HeartState();
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
            path.addArc(new RectF(-r/4,-r/2,r/4,r/2),0,180);
            path.addArc(new RectF(-r/4,-r/8,0,r/8),180,180);
            path.addArc(new RectF(0,r/4,-r/8,r/8),180,180);
            canvas.restore();
        }
        public void update() {

        }
        public int hashCode() {
            return (int)(x+y);
        }
    }
    private class HeartState {
        private float deg=0,scale = 0.8f,scaleDir = 1,dir = 0;
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
