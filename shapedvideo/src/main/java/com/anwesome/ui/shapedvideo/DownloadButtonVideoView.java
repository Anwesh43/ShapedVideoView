package com.anwesome.ui.shapedvideo;

import android.content.Context;
import android.graphics.*;
import android.util.AttributeSet;

import java.util.Random;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Created by anweshmishra on 03/04/17.
 */
public class DownloadButtonVideoView extends ShapedVideoView {
    private int time = 0,w,h;
    private Random random = new Random();
    private ConcurrentLinkedQueue<DownloadButton> buttons = new ConcurrentLinkedQueue<>();
    public DownloadButtonVideoView(Context context) {
        super(context);
    }
    public DownloadButtonVideoView(Context context, AttributeSet attrs) {
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
        for(DownloadButton button:buttons) {
            button.draw(canvas,paint);
            button.update();
            if(button.shouldRemove()) {
                buttons.remove(button);
            }
        }
        if(time %20 == 0) {
            buttons.add(new DownloadButton(random.nextInt(w),random.nextInt(w)));
        }
        time++;
        try {
            Thread.sleep(50);
            invalidate();
        }
        catch (Exception ex) {

        }
    }
    public void handleTap(float x,float y) {
        for(DownloadButton button:buttons) {
            button.handleTap(x,y);
        }
    }
    private class DownloadButton {
        private int mode = -1;
        private float x,y,deg = -180,k = 0,size;
        public DownloadButton(float x,float y) {
            this.x = x;
            this.y = y;
            this.size = w/10;
        }
        public void draw(Canvas canvas,Paint paint) {
            paint.setColor(Color.parseColor("#99FFFFFF"));
            canvas.save();
            canvas.translate(x,y);
            canvas.rotate(deg);
            canvas.drawRect(new RectF(-size/2,-size/2,size/2,-size/3),paint);
            canvas.save();
            canvas.translate(0,k);
            Path path = new Path();
            path.moveTo(-size/6,-size/3);
            path.lineTo(-size/6,size/3);
            path.lineTo(-size/3,size/3);
            path.lineTo(0,size/2);
            path.lineTo(size/3,size/3);
            path.lineTo(size/6,size/3);
            path.lineTo(size/6,-size/3);
            canvas.drawPath(path,paint);
            canvas.restore();
            canvas.restore();
        }
        public void update() {
            switch(mode) {
                case 0:
                    deg+=20;
                    if(deg >=0){
                        mode = 1;
                    }
                    break;
                case 1:
                    k+=20;
                    if(k>=h-y) {
                        mode = 2;
                    }
                    break;
                default:
                    break;
            }
        }
        public boolean shouldRemove() {
            return mode == 2;
        }
        public int hashCode() {
            return (int)(x+y+deg);
        }
        public void handleTap(float x,float y) {
            if(mode == -1 && x>=this.x-size/2 && x<=this.x+size/2 && y>=this.y-size/2 && y<=this.y+size/2) {
                mode = 0;
            }
        }
    }
}
