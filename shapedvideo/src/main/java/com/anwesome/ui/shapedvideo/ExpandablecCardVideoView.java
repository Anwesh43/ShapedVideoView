package com.anwesome.ui.shapedvideo;

import android.content.Context;
import android.graphics.*;
import android.util.AttributeSet;

/**
 * Created by anweshmishra on 01/04/17.
 */
public class ExpandablecCardVideoView extends ShapedVideoView {
    private int w,h,time = 0;
    private ExpandableCard expandableCard;
    public ExpandablecCardVideoView(Context context) {
        super(context);
    }
    public ExpandablecCardVideoView(Context context, AttributeSet attrs) {
        super(context,attrs);
    }
    public void handleTap(float x,float y) {
        expandableCard.handleTap(x,y);
    }
    public void drawElements(Canvas canvas, Paint paint) {
        if(time == 0) {
            w = canvas.getWidth();
            h = canvas.getHeight();
            expandableCard = new ExpandableCard();
        }
        expandableCard.draw(canvas,paint);
        time++;
        try {
            expandableCard.update();
            Thread.sleep(30);
            invalidate();
        }
        catch (Exception ex) {

        }
    }
    public boolean shouldDraw() {
        return true;
    }
    private class ExpandableCard {
        private float x,y,wSize,hBar,hMov=0,hFinal,dir = 0;
        private CloseButton closeButton;
        public ExpandableCard() {
            this.x = w/10;
            this.y = w/3;
            this.wSize = w/2;
            this.hFinal = w/2;
            this.hBar = h/20;
            closeButton = new CloseButton(this.x+this.wSize*0.85f,this.y+this.hBar/2);
        }
        public void draw(Canvas canvas,Paint paint) {
            paint.setStyle(Paint.Style.FILL);
            paint.setColor(Color.parseColor("#99FAFAFA"));
            canvas.drawRect(new RectF(x,y,x+wSize,y+hBar),paint);
            closeButton.draw(canvas,paint);
            paint.setColor(Color.parseColor("#99e53935"));
            canvas.drawRect(new RectF(x,y+hBar,x+wSize,y+hBar+hMov),paint);
        }
        private void startMoving() {
            dir = hMov == 0?1:-1;
        }
        public void handleTap(float x,float y) {
            if(dir==0 && closeButton.handleTap(x,y)) {
                startMoving();
            }
        }
        public void update() {
            hMov+=(hFinal/10)*dir;
            if(closeButton!=null) {
                closeButton.update(dir);
            }
            if(hMov>=hFinal) {
                dir = 0;
                hMov = hFinal;
            }
            if(hMov<=0) {
                dir = 0;
                hMov = 0;
            }
        }
    }
    private class CloseButton {
        private float x,y,r,deg = 0;
        public CloseButton(float x,float y) {
            this.x = x;
            this.y = y;
            this.r = w/30;
        }
        public void draw(Canvas canvas,Paint paint) {
            paint.setColor(Color.BLACK);
            paint.setStrokeWidth(w/100);
            canvas.save();
            canvas.translate(x,y);
            canvas.rotate(deg);
            for(int i=0;i<2;i++) {
                canvas.save();
                canvas.rotate(i*90);
                canvas.drawLine(-r,0,r,0,paint);
                canvas.restore();
            }
            canvas.restore();
        }
        public void update(float dir) {
            deg+=4.5f*dir;
        }

        public boolean handleTap(float x,float y) {
            boolean condition = x>=this.x-r && x<=this.x+r && y>=this.y-r && y<=this.y+r;
            return condition;
        }
    }
}
