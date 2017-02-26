package com.anwesome.ui.shapedvideo;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;

import java.util.*;

/**
 * Created by anweshmishra on 26/02/17.
 */
public class DrawOrbitShapedVideoView extends ShapedVideoView {
    private List<Orbit> orbits = new ArrayList<>();
    private boolean isAnimated = false;
    private OrbitFactory orbitFactory = new OrbitFactory();
    public DrawOrbitShapedVideoView(Context context, AttributeSet attrs) {
        super(context,attrs);
    }
    protected boolean shouldDraw() {
        return true;
    }
    public DrawOrbitShapedVideoView(Context context) {
        super(context);
    }
    public void drawElements(Canvas canvas, Paint paint) {
        for(Orbit orbit:orbits) {
            orbit.draw(canvas,paint);
            if(orbit.isStopped()) {
                orbits.remove(orbit);
            }
        }
        if(isAnimated) {
            try {
                Thread.sleep(50);
                invalidate();
            }
            catch (Exception ex) {

            }
        }
    }
    public void handleTap(float x,float y) {
        Orbit orbit = orbitFactory.newInstance(x,y);
        orbits.add(orbit);
        if(!isAnimated) {
            isAnimated = true;
            postInvalidate();
        }
    }
    private class Orbit {
        private float x,y,deg = -90,speed = 30,intialDeg = -90;
        private Orbit(float x,float y) {
            this.x = x;
            this.y = y;
        }
        public void draw(Canvas canvas,Paint paint) {
            int r = canvas.getWidth()/12;
            paint.setColor(Color.parseColor("#00BCD4"));
            paint.setStyle(Paint.Style.STROKE);
            paint.setStrokeWidth(r/6);
            canvas.save();
            canvas.translate(x,y);
            canvas.rotate(deg);
            canvas.drawCircle(r,0,r/4,paint);
            canvas.drawArc(new RectF(-r/2,-r/2,r/2,r/2),intialDeg,deg-intialDeg,false,paint);
            canvas.restore();
            deg+=speed;
            if(deg >= 270) {
                deg = 270;
                speed = 0;
            }
        }
        public boolean isStopped() {
            return speed == 0;
        }
        public int hashCode() {
            return (int)(Math.sqrt(x*x+y*y))+(int)deg;
        }
    }
    private class OrbitFactory {
        public Orbit newInstance(float x,float y) {
            return new Orbit(x,y);
        }
    }
}
