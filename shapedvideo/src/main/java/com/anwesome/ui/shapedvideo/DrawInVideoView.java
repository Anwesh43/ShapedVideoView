package com.anwesome.ui.shapedvideo;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.view.MotionEvent;

import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Created by anweshmishra on 11/03/17.
 */
public class DrawInVideoView extends ShapedVideoView{
    private int time = 0;
    private int currColor = Color.RED;
    private float minScale = 0.5f;
    private boolean isAnimated = false;
    private CircularColor prev=null,curr=null;
    private float colorY;
    private ConcurrentLinkedQueue<CircularColor> circularColors = new ConcurrentLinkedQueue<>();
    public DrawInVideoView(Context context) {
        super(context);
    }
    public DrawInVideoView(Context context, AttributeSet attrs) {
        super(context,attrs);
    }
    public boolean shouldDraw() {
        return true;
    }
    public void stopAnimation() {
        isAnimated = false;
        prev = curr;
        curr = null;
    }
    public void drawElements(Canvas canvas,Paint paint) {
        if(time == 0) {
            String colorsHexes[]={"00BCD4","ef5350","F57F17","43A047","5E35B1"};
            float gap = (2*canvas.getWidth())/(3*colorsHexes.length+1),x = gap;
            colorY = (canvas.getHeight()*8)/10;
            int index = 0;
            for(String colorHex:colorsHexes) {
                CircularColor circularColor = new CircularColor(colorHex,x,colorY+gap/2,gap);
                circularColors.add(circularColor);
                if(index == 0) {
                    prev = circularColor;
                    prev.scale = 1.0f;
                    currColor = circularColor.getColor();
                }
                x+=(3*gap)/2;
                index++;
            }
        }
        for(CircularColor circularColor:circularColors) {
            circularColor.draw(canvas,paint);
        }
        time++;
        if(isAnimated) {
            if(curr!=null) {
                curr.update();
                if(prev!=null) {
                    prev.update();
                    if(prev.stopped() && curr.stopped()) {
                        stopAnimation();
                    }
                }
                else {
                    if(curr.stopped()) {
                        stopAnimation();
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
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX(),y = event.getY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if(y>=colorY && !isAnimated && curr==null) {
                    for(CircularColor circularColor:circularColors) {
                        if(circularColor.scale==minScale && circularColor.handleTap(x,y)) {
                            curr = circularColor;
                            break;
                        }
                    }
                    if(curr!=null) {
                        curr.startUpdating(1);
                        currColor = curr.getColor();
                        if(prev!=null) {
                            prev.startUpdating(-1);
                        }
                        isAnimated = true;
                        postInvalidate();
                    }
                }
                break;
            case MotionEvent.ACTION_MOVE:
                break;
            case MotionEvent.ACTION_UP:
                break;
            default:
                break;
        }
        return true;
    }
    private class ColorShape {
        private ConcurrentLinkedQueue<PointF> points = new ConcurrentLinkedQueue<>();
        public ColorShape() {

        }
        public void draw(Canvas canvas,Paint paint) {
            int index = 0;
            paint.setColor(currColor);
            paint.setStyle(Paint.Style.STROKE);
            paint.setStrokeWidth(30);
            paint.setStrokeCap(Paint.Cap.BUTT);
            paint.setStrokeJoin(Paint.Join.ROUND);
            Path path = new Path();
            for(PointF point:points) {
                if(index == 0) {
                    path.moveTo(point.x,point.y);
                }
                else {
                    path.lineTo(point.x,point.y);
                }
                index++;
            }
            canvas.drawPath(path,paint);
        }
        public void addPoint(PointF point) {
            points.add(point);
        }
        public int hashCode() {
            return points.hashCode();
        }
    }
    private class CircularColor {
        private float x,y,r,scale = minScale,dir=0;
        private String colorHex="#99";
        public CircularColor(String colorHex,float x,float y,float r) {
            this.colorHex += colorHex.replace("#","");
            this.x = x;
            this.y = y;
            this.r = r;
        }
        public int getColor() {
            return Color.parseColor(colorHex);
        }
        public boolean handleTap(float x,float y) {
            boolean condition = x>=this.x-r && x<=this.x+r && y>=this.y-r && y<=this.y+r;
            return condition;
        }
        public boolean stopped() {
            return dir == 0;
        }
        public void startUpdating(float dir) {
            this.dir = dir;
        }
        public void draw(Canvas canvas, Paint paint) {
            paint.setStyle(Paint.Style.FILL);
            paint.setColor(Color.parseColor(colorHex));
            canvas.save();
            canvas.translate(x,y);
            canvas.scale(scale,scale);
            canvas.drawCircle(0,0,r,paint);
            canvas.restore();
        }
        public void update() {
            this.scale+=0.1f*dir;
            if(this.scale>=1) {
                this.scale = 1;
                this.dir = 0;
            }
            if(this.scale<=minScale) {
                this.scale = minScale;
                this.dir = 0;
            }
        }
        public int hashCode() {
            return (int)(x+scale)+colorHex.hashCode();
        }
    }
}
