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
            addCurvedPath(path,r/4-r/2,0,r/2,0,60);
            addCurvedPath(path,-r/4+r/2,0,r/2,120,60);
            float a = 0;
            for(int i=0;i<2;i++) {
                path.addArc(new RectF((i-1)*r/4, -r / 8, i*r/4, r / 8), 180+i*a, 180-a);
            }
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
            return (x>=this.x-1.5f*r/2 && x<=this.x+1.5f*r/2 && y>=this.y-1.5f*r/2 && y<=this.y+1.5f*r/2);
        }
    }
    private void addCurvedPath(Path path,float x,float y,float r,float startDeg,float sweep) {
        for(float deg = startDeg;deg<=startDeg+sweep;deg+=5) {
            float newX = x+r*(float)Math.cos(deg*Math.PI/180),newY = y+r*(float)Math.sin(deg*Math.PI/180);
            path.lineTo(newX,newY);
        }
    }
    private class HeartState {
        private float deg=0,scale = 1,scaleDir = 0.2f,dir = 0;
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
            if(scale>=2) {
                scaleDir = -0.2f;
            }
            if(scale<=1f) {
                scaleDir = 0.2f;
            }
        }
        public void rotateDown() {
            deg -= 72;
            scale-=0.2f;
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
