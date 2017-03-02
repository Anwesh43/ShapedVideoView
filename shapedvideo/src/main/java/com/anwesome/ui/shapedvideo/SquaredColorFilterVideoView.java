package com.anwesome.ui.shapedvideo;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.media.UnsupportedSchemeException;
import android.util.AttributeSet;

/**
 * Created by anweshmishra on 03/03/17.
 */
public class SquaredColorFilterVideoView extends ShapedVideoView {
    public SquaredColorFilterVideoView(Context context, AttributeSet attrs) {
        super(context,attrs);
    }
    protected boolean shouldDraw() {
        return true;
    }
    public void drawElements(Canvas canvas, Paint paint) {

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
        }
        public void draw(Canvas canvas,Paint paint) {
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
