package com.anwesome.ui.shapedvideo;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.media.UnsupportedSchemeException;
import android.util.AttributeSet;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by anweshmishra on 03/03/17.
 */
public class SquaredColorFilterVideoView extends ShapedVideoView {
    private boolean isAnimated = true;
    private List<SquareColorFilter> filters = new LinkedList<>();
    private int time = 0,index = 0;
    public SquaredColorFilterVideoView(Context context, AttributeSet attrs) {
        super(context,attrs);
    }
    protected boolean shouldDraw() {
        return true;
    }
    private int getTransparentColor(String colorHex) {
        return Color.parseColor("#99"+colorHex.replace("#",""));
    }
    public void drawElements(Canvas canvas, Paint paint) {
        if(time == 0) {
            int w = canvas.getWidth(),h = canvas.getHeight();
            int colors[] = {getTransparentColor("#3F51B5"),getTransparentColor("#43A047"),getTransparentColor("#0097A7"),getTransparentColor("#D84315")};
            int i = 0;
            for(int color:colors) {
                filters.add(new SquareColorFilter(color,((w/2)*(i%2))+w/4,(h/2)*(i/2)+h/4,w/2,h/2));
                i++;
            }
        }
        for(SquareColorFilter filter:filters) {
            filter.draw(canvas,paint);
        }
        time++;
        if(isAnimated) {
            if(index>=0 && index<filters.size()) {
                filters.get(index).update();
                if(filters.get(index).stopped()) {
                    index++;
                    if(index == filters.size()) {
                        isAnimated = false;
                        index = index-1;
                    }
                }
            }
            try {
                Thread.sleep(50);
                invalidate();
            }
            catch (Exception ex) {

            }
        }
    }
    class SquareColorFilter {
        private int color;
        private float x,y,w,h,scale = 0,dir = 1;
        public SquareColorFilter(int color,float...dimen) {
            if(dimen.length == 4) {
                this.x = dimen[0];
                this.y = dimen[1];
                this.w = dimen[2];
                this.h = dimen[3];
            }
            else {
                throw  new UnsupportedOperationException();
            }
            this.color = color;
        }
        public void draw(Canvas canvas,Paint paint) {
            paint.setStyle(Paint.Style.FILL);
            paint.setColor(color);
            canvas.save();
            canvas.translate(x,y);
            canvas.scale(scale,scale);
            canvas.drawRect(new RectF(-w/2,-h/2,w/2,h/2),paint);
            canvas.restore();
        }
        public void update() {
            scale+=dir*0.1f;
            if(scale>=1) {
                dir = 0;
            }
        }
        public boolean stopped() {
            return dir == 0;
        }
        public int hashCode() {
            return (int)x+(int)y+color;
        }
    }
}
