package com.anwesome.ui.shapedvideo;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.GestureDetector;
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
    private TraversingSquare traversingSquare;
    private GestureDetector gestureDetector;
    private boolean isAnimated = false;
    public GridTraverseVideoView(Context context, AttributeSet attrs) {
        super(context,attrs);
        gestureDetector = new GestureDetector(context,new GridGestureListener());
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
            while(y<h-sizeOfGrid) {
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
            traversingSquare = new TraversingSquare(grids.get(0));
        }
        paint.setStyle(Paint.Style.FILL);
        for(SquareGrid grid:grids) {
            grid.draw(canvas,paint);
        }
        traversingSquare.draw(canvas,paint);
        time++;
        if(isAnimated) {
            try {
                traversingSquare.move();
                if(traversingSquare.stopped()) {
                    isAnimated = false;
                }
                Thread.sleep(30);
                invalidate();
            } catch (Exception ex) {

            }
        }
    }
    public boolean onTouchEvent(MotionEvent event) {
        return gestureDetector.onTouchEvent(event);
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
                paint.setColor(Color.parseColor("#660097A7"));
            }
            else {
                paint.setColor(Color.parseColor("#669E9E9E"));
            }
            canvas.drawRoundRect(new RectF(x-w/2,y-w/2,x+w/2,y+w/2),w/6,w/6,paint);
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(Color.parseColor("#66424242"));
            canvas.drawRoundRect(new RectF(x-w/2,y-w/2,x+w/2,y+w/2),w/6,w/6,paint);
        }
        public int hashCode() {
            return (int)(2*x*x+y*y);
        }
    }
    private class TraversingSquare {
        private SquareGrid curr,target;
        private float xDir = 0,yDir = 0,x,y,w;
        public TraversingSquare(SquareGrid curr) {
            this.curr = curr;
            this.x = this.curr.x;
            this.y = this.curr.y;
            this.w = this.curr.w;
        }
        public void draw(Canvas canvas,Paint paint) {
            paint.setStyle(Paint.Style.FILL);
            paint.setColor(Color.parseColor("#AA3F51B5"));
            canvas.drawRoundRect(new RectF(x-w/2,y-w/2,x+w/2,y+w/2),w/6,w/6,paint);
        }
        public void setDirection(float xDir,float yDir) {
            this.xDir = xDir;
            this.yDir = yDir;
            obtainTarget();
        }
        public void obtainTarget() {
            if(xDir+yDir == 1) {
                if(xDir == 1) {
                    target = curr.right;
                }
                else {
                    target = curr.down;
                }
            }
            else {
                if(xDir == -1) {
                    target = curr.left;
                }
                else {
                    target = curr.up;
                }
            }
            if(target== null ||(target!=null && !target.allowed)) {
                this.xDir = 0;
                this.yDir = 0;
                if(curr!=null) {
                    x = curr.x;
                    y = curr.y;
                }
            }
        }
        public boolean stopped() {
            return xDir == 0 && yDir == 0;
        }
        public void move() {
            if(target!=null) {
                x += w  * xDir;
                y += w  * yDir;
                if (target != null && x > target.x - w / 2 && x < target.x + w / 2 && y > target.y - w / 2 && y < target.y + w / 2) {
                    curr = target;
                    x = curr.x;
                    y = curr.y;
                    obtainTarget();

                }
            }
        }
    }
    private class GridGestureListener extends GestureDetector.SimpleOnGestureListener {
        public boolean onDown(MotionEvent event) {
            return true;
        }
        public boolean onSingleTapUp(MotionEvent e1) {
            return true;
        }
        public boolean onFling(MotionEvent e1,MotionEvent e2,float velx,float vely) {
            if(!isAnimated) {
                if (Math.abs(velx) > Math.abs(vely)) {
                    if( e1.getX()!=e2.getX()) {
                        traversingSquare.setDirection((e2.getX() - e1.getX()) / Math.abs(e2.getX() - e1.getX()), 0);
                    }
                } else {
                    if(e2.getY()!=e1.getY()) {
                        traversingSquare.setDirection(0, (e2.getY() - e1.getY()) / Math.abs(e2.getY() - e1.getY()));
                    }
                }
                isAnimated = true;
                postInvalidate();
            }
            return true;
        }
    }
}
