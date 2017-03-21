package com.anwesome.ui.shapedvideo;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.MotionEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by anweshmishra on 21/03/17.
 */
public class GridTraverseVideoView extends ShapedVideoView {
    private List<SquareGrid> grids = new ArrayList<>();
    private int w,h,time = 0;
    private boolean isAnimated = false;
    public GridTraverseVideoView(Context context, AttributeSet attrs) {
        super(context,attrs);
    }
    public boolean shouldDraw() {
        return true;
    }
    public void drawElements(Canvas canvas, Paint paint) {
        if(time == 0) {
            w = canvas.getWidth();
            h = canvas.getHeight();
            int na = 0;
            int k = 20;
            float sizeOfGrid = w/k,x = w/(2*k),y=w/(2*k);
            while(y<h-sizeOfGrid/2) {
                boolean allowed = true;
                if(grids.size()%30 == 11 && na<7) {
                    Random random = new Random();
                    allowed = random.nextInt(1000)%2 != 0;
                    if(!allowed) {
                        na++;
                    }
                }
                grids.add(new SquareGrid(x,y,sizeOfGrid,allowed));
                x+=sizeOfGrid;
                if(x>w) {
                    x = w/(k*2);
                    y+=sizeOfGrid;
                }
            }
            int index = 0;
            for(SquareGrid grid:grids) {
                if(index-k>=0) {
                    grid.setUp(grids.get(index-k));
                }
                if(index%k>=1) {
                    grid.setLeft(grids.get(index-1));
                }
                if(index%k<=k-1 && index+1<grids.size()) {
                    grid.setRight(grids.get(index+1));
                }
                if(index+k<=grids.size()-1) {
                    grid.setDown(grids.get(index+k));
                }
                index++;
            }
        }
        paint.setStyle(Paint.Style.FILL);
        for(SquareGrid grid:grids) {
            grid.draw(canvas,paint);
        }
        time++;
        if(isAnimated) {
            try {
                Thread.sleep(50);
                invalidate();
            } catch (Exception ex) {

            }
        }
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
            paint.setStyle(Paint.Style.FILL);
            if(allowed) {
                paint.setColor(Color.parseColor("#990097A7"));
            }
            else {
                paint.setColor(Color.parseColor("#999E9E9E"));
            }
            canvas.drawRoundRect(new RectF(x-w/2,y-w/2,x+w/2,y+w/2),w/6,w/6,paint);
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(Color.parseColor("#99424242"));
            canvas.drawRoundRect(new RectF(x-w/2,y-w/2,x+w/2,y+w/2),w/6,w/6,paint);
        }
        public int hashCode() {
            return (int)(2*x*x+y*y);
        }
    }
}
