package com.anwesome.ui.shapedvideo;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import java.util.*;

/**
 * Created by anweshmishra on 20/03/17.
 */
public class FlagVideoView extends ShapedVideoView {
    private int w,h,time = 0;
    private boolean isAnimated = false;
    private List<TransparentFlag> transparentFlags = new ArrayList<>();
    private TransparentFlag prevFlag,currFlag;
    private int currIndex = 0;
    private String flagsColorHexes[][] = {{"#FF9100","#FFFFFF","#4CAF50"},{"#f44336","#ffffff","#00BCD4"},{"#000000","#f44336","#FFC107"},{"#FFEB3B","#4CAF50","#f44336"}};
    private GestureDetector gestureDetector;
    public FlagVideoView(Context context){
        super(context);
        gestureDetector = new GestureDetector(context,new FlagGestureListener());
    }
    public FlagVideoView(Context context, AttributeSet attrs) {
        super(context,attrs);
        gestureDetector = new GestureDetector(context,new FlagGestureListener());
    }
    public void drawElements(Canvas canvas,Paint paint) {
        if(time == 0) {
            w = canvas.getWidth();
            h = canvas.getHeight();
            int colors[][] = new int[flagsColorHexes.length][3];
            for(int i=0;i<flagsColorHexes.length;i++) {
                for(int j=0;j<flagsColorHexes[i].length;j++) {
                    colors[i][j] = Color.parseColor("#99"+flagsColorHexes[i][j].replace("#",""));
                }
                transparentFlags.add(new TransparentFlag(w*i,0,colors[i]));
            }
            if(transparentFlags.size()>0) {
                currFlag = transparentFlags.get(0);
            }
        }
        paint.setStyle(Paint.Style.FILL);
        for(TransparentFlag transparentFlag:transparentFlags) {
            transparentFlag.draw(canvas,paint);
        }
        time++;
        if(isAnimated) {
            if(currFlag!=null && prevFlag!=null) {
                currFlag.update();
                prevFlag.update();
                if(currFlag.isStopped()) {
                    isAnimated = false;
                    prevFlag.x = prevFlag.dir == 1?w:-w;
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
    public boolean onTouchEvent(MotionEvent event) {
        return gestureDetector.onTouchEvent(event);
    }
    protected boolean shouldDraw() {
        return true;
    }
    public class TransparentFlag {
        private float x,y,dir =0;
        private boolean curr = false,stopped = false;
        private int flagColors[] = new int[3];
        public TransparentFlag(float x,float y,int flagColors[]) {
            this.x = x;
            this.y = y;
            this.flagColors = flagColors;
        }
        public boolean isStopped() {
            return stopped;
        }
        public void setDir(float dir) {
            this.dir = dir;
            stopped = false;
            curr = false;
        }
        public void draw(Canvas canvas, Paint paint) {
            canvas.save();
            canvas.translate(x+w/2,y+h/2);
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
                stopped = true;
            }
        }
        public int hashCode() {
            return (int)(x+y+dir)+flagColors.hashCode();
        }
    }
    private class FlagGestureListener extends GestureDetector.SimpleOnGestureListener{
        public boolean onDown(MotionEvent event) {
            return true;
        }
        public boolean onSingleTapUp(MotionEvent event) {
            return true;
        }
        public boolean onFling(MotionEvent e1,MotionEvent e2,float velx,float vely) {
            int dir = 0;
            if(e1.getX()<e2.getX()) {
                dir = 1;
            }
            else if(e1.getX()>e2.getX()) {
                dir = -1;
            }
            if(dir !=0 && transparentFlags.size()>0 && !isAnimated) {
                currIndex += dir;
                if (currIndex > transparentFlags.size()-1) {
                    currIndex = 0;
                }
                if (currIndex < 0) {
                    currIndex = transparentFlags.size() - 1;
                }
                prevFlag = currFlag;
                currFlag = transparentFlags.get(currIndex);
                currFlag.x = -dir*w;
                currFlag.setDir(dir);
                prevFlag.setDir(dir);
                currFlag.setCurrent();
                isAnimated = true;
                postInvalidate();
            }
            return true;
        }
    }
}
