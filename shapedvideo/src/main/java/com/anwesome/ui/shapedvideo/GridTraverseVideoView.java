package com.anwesome.ui.shapedvideo;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by anweshmishra on 21/03/17.
 */
public class GridTraverseVideoView extends ShapedVideoView {
    public GridTraverseVideoView(Context context, AttributeSet attrs) {
        super(context,attrs);
    }
    public void drawElements(Canvas canvas, Paint paint) {
        paint.setStyle(Paint.Style.FILL);
    }
    public boolean onTouchEvent(MotionEvent event) {
        return true;
    }
    private class SquareGrid {
        private float x,y,w;
        private boolean allowed = false;
        private SquareGrid left,right,up,down;
        public SquareGrid(float x,float y,float w,boolean allowed) {
            this.x = x;
            this.y = y;
            this.w = w;
            this.allowed = allowed;
        }
        public void setLeft(SquareGrid left) {
            this.left = left;
        }
        public void setRight(SquareGrid right) {
            this.right = right;
        }
        public void setUp(SquareGrid up) {
            this.up = up;
        }
        public void setDown(SquareGrid down) {
            this.down = down;
        }
        public void draw(Canvas canvas,Paint paint) {
            if(allowed) {
                paint.setColor(Color.parseColor("#990097A7"));
            }
            else {
                paint.setColor(Color.parseColor("#999E9E9E"));
            }
            canvas.drawRoundRect(new RectF(x-w/2,y-w/2,x+w/2,y+w/2),w/15,w/15,paint);
        }
        public int hashCode() {
            return (int)(2*x*x+y*y);
        }
    }
}
