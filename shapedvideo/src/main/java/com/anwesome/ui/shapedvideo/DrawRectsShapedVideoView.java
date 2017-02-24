package com.anwesome.ui.shapedvideo;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by anweshmishra on 24/02/17.
 */
public class DrawRectsShapedVideoView extends ShapedVideoView {
    private List<Rectangle> rectangles = new ArrayList<>();
    private RectangleFactory rectangleFactory = new RectangleFactory();
    public DrawRectsShapedVideoView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public DrawRectsShapedVideoView(Context context) {
        super(context);
    }
    public boolean shouldDraw() {
        return true;
    }
    public void drawElements(Canvas canvas, Paint paint) {
        paint.setColor(Color.parseColor("#009688"));
        for(Rectangle rectangle:rectangles) {
            rectangle.draw(canvas,paint);
        }
        try {
            Thread.sleep(50);
            invalidate();
        }
        catch (Exception ex) {

        }
    }
    public void handleTap(float x,float y) {
        rectangles.add(rectangleFactory.newRectangle(x,y));
    }
    private class RectangleFactory {
        private Rectangle newRectangle(float x,float y) {
            return new Rectangle(x,y);
        }
    }
    private class Rectangle {
        private float x,y,deg = 0;
        private Rectangle(float x,float y){
            this.x = x;
            this.y = y;
        }
        public void draw(Canvas canvas,Paint paint) {
            int w = canvas.getWidth()/10;
            paint.setStrokeWidth(15);
            canvas.save();
            canvas.translate(x,y);
            canvas.rotate(deg);
            canvas.drawRect(new RectF(-w/2,-w/2,w/2,w/2),paint);
            canvas.restore();
            deg+=10;
        }
        public int hashCode() {
            return (int)x+(int)y+(int)deg;
        }
    }
}
