package com.anwesome.ui.shapedvideo;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by anweshmishra on 20/03/17.
 */
public class FlagVideoView extends ShapedVideoView {
    private int w,h,time = 0;
    private boolean isAnimated = false;
    public FlagVideoView(Context context){
        super(context);
    }
    public FlagVideoView(Context context, AttributeSet attrs) {
        super(context,attrs);
    }
    public void drawElements(Canvas canvas,Paint paint) {
        if(time == 0) {
            w = canvas.getWidth();
            h = canvas.getHeight();
        }
        paint.setStyle(Paint.Style.FILL);
        time++;
        if(isAnimated) {
            try {
                Thread.sleep(50);
                invalidate();
            }
            catch (Exception ex) {

            }
        }
    }
    protected boolean shouldDraw() {
        return true;
    }
    public class TransparentFlag {
        private float x,y,dir =0;
        private boolean curr = false;
        private int flagColors[] = new int[3];
        public TransparentFlag(float x,float y,int flagColors[]) {
            this.x = x;
            this.y = y;
            this.flagColors = flagColors;
        }
        public void setDir(float dir) {
            this.dir = dir;
        }
        public void draw(Canvas canvas, Paint paint) {
            canvas.save();
            canvas.translate(x,y);
            for(int i=0;i<flagColors.length;i++) {
                paint.setColor(flagColors[i]);
                canvas.save();
                canvas.translate(-w/2,i*h/3-(h/3+h/6));
                canvas.drawRect(new RectF(0,0,w,h/3),paint);
                canvas.restore();
            }
            canvas.restore();
        }
        public void setCurrent() {
            curr = true;
        }
        public void update() {
            x+=dir*w/10;
            if(((dir == -1 && x<=0) || (dir == 1 && x>=0)) && curr) {
                dir = 0;
            }
        }
        public int hashCode() {
            return (int)(x+y+dir)+flagColors.hashCode();
        }
    }
}
