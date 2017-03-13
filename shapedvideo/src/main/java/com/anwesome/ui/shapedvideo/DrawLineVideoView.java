package com.anwesome.ui.shapedvideo;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.util.AttributeSet;

import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Created by anweshmishra on 13/03/17.
 */
public class DrawLineVideoView extends ShapedVideoView {
    private float linePointRadius = 10;
    private ConcurrentLinkedQueue<Line> lines = new ConcurrentLinkedQueue<>();
    private Line currentLine;
    public DrawLineVideoView(Context context) {
        super(context);
    }
    public DrawLineVideoView(Context context, AttributeSet attrs) {
        super(context,attrs);
    }
    public boolean shouldDraw() {
        return true;
    }
    public void drawElements(Canvas canvas, Paint paint) {
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.parseColor("#99f44336"));
        if(currentLine!=null) {
            currentLine.draw(canvas,paint);
        }
        for(Line line:lines) {
            line.draw(canvas,paint);
            line.update();
        }
        try {
            Thread.sleep(50);
            invalidate();
        }
        catch (Exception ex) {

        }
    }
    public void handleTap(float x,float y) {
        if(currentLine == null) {
            currentLine = new Line(x,y);
        }
        else if(x!=currentLine.startX && y!=currentLine.startY){
            currentLine.computeInnerPoints(x,y);
            lines.add(currentLine);
            currentLine = null;
        }
    }
    private class Line {
        private float startX,startY;
        private int index = 0,time = 0,maxtime = 5;
        private boolean stopped = false;
        private ConcurrentLinkedQueue<LinePoint> linePoints = new ConcurrentLinkedQueue<>();
        public Line(float startX,float startY) {
            this.startX = startX;
            this.startY = startY;
            linePoints.add(new LinePoint(new PointF(this.startX,this.startY)));
        }
        public void draw(Canvas canvas,Paint paint) {
            for(LinePoint linePoint:linePoints) {
                linePoint.draw(canvas,paint);
            }
        }
        private double getDistance(float x,float y) {
            return Math.sqrt(Math.pow(x-startX,2)+Math.pow(y-startY,2));
        }
        public void computeInnerPoints(float endX,float endY) {
            float theta = (endX == startX)?90:(float)Math.atan((Math.abs(endY-startY))/(Math.abs(endX-startX)));
            float xDir = endX-startX == 0?0:(endX-startX)/Math.abs(endX-startX);
            float yDir = endY-startY == 0?0:(endY-startY)/Math.abs(endY-startY);
            if(xDir == yDir) {
                theta += 90*(1-xDir);
            }
            else if(xDir!=yDir) {
                theta += 90*(-yDir+2);
            }
            float x = startX,y = startY;
            while(true) {
                x+=2*linePointRadius*Math.cos(theta*Math.PI/180);
                y+=2*linePointRadius*Math.sin(theta*Math.PI/180);
                if(getDistance(x,y)>getDistance(endX,endY)) {
                    break;
                }
                linePoints.add(new LinePoint(new PointF(x,y)));
            }
        }
        public LinePoint getLinePoint(int index) {
            int i = 0;
            for(LinePoint linePoint:linePoints) {
                if(i == index) {
                    return linePoint;
                }
                i++;
            }
            return null;
        }
        public void update() {
            if(time<maxtime) {
                time++;
            }
            else if(index<linePoints.size()){
                for(LinePoint linePoint:linePoints) {
                    linePoint.update();
                }
                if(index<linePoints.size()) {
                    if(getLinePoint(index).shouldNeighborJump) {
                        index++;
                    }
                    if(index<linePoints.size()) {
                        getLinePoint(index).jump();
                    }
                }
            }
            if(linePoints.size()>0 && getLinePoint(linePoints.size()-1).stopped) {
                stopped = false;
            }
        }
        public int hashCode() {
            return linePoints.size();
        }
    }
    private class LinePoint {
        private PointF pointF;
        private float yDir = 0,initY;
        private boolean shouldNeighborJump = false,stopped = false;
        public LinePoint(PointF pointF) {
            this.pointF = pointF;
            this.initY = pointF.y;
        }
        public void draw(Canvas canvas,Paint paint) {
            canvas.drawCircle(pointF.x,pointF.y,linePointRadius,paint);
        }
        public int hashCode() {
            return pointF.hashCode()+(int)yDir;
        }
        public void jump() {
            yDir  = yDir == 0?1:yDir;
        }
        public void update() {
            pointF.y-=yDir*20;
            if(pointF.y<=initY-100) {
                pointF.y = initY-100;
                shouldNeighborJump = true;
                yDir = -1;
            }
            else if(pointF.y>=initY){
                pointF.y = initY;
                yDir = 0;
                stopped = true;

            }
        }
    }
}
