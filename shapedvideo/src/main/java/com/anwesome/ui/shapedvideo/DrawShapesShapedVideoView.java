package com.anwesome.ui.shapedvideo;

import android.content.Context;
import android.graphics.*;
import android.util.AttributeSet;

import java.util.*;

/**
 * Created by anweshmishra on 25/02/17.
 */
public class DrawShapesShapedVideoView extends ShapedVideoView{
    private List<Shape> shapes = new ArrayList<>();

    public DrawShapesShapedVideoView(Context context) {
        super(context);
    }
    public DrawShapesShapedVideoView(Context context, AttributeSet attrs) {
        super(context,attrs);
    }
    protected boolean shouldDraw() {
        return true;
    }
    public void drawElements(Canvas canvas,Paint paint) {
        paint.setColor(Color.parseColor("#283593"));
        paint.setStrokeWidth(15);
        for(Shape shape:shapes) {
            shape.draw(canvas,paint);
            shape.update();
        }
        try {

            Thread.sleep(50);
            invalidate();
        }
        catch (Exception ex) {

        }
    }
    public void handleTap(float x,float y) {
        ShapeMode shapeMode = ShapeMode.CIRCLE;
        switch(shapes.size()%4) {
            case 1:
                shapeMode = ShapeMode.TRIANGLE;
                break;
            case 2:
                shapeMode = ShapeMode.SQUARE;
                break;
            case 3:
                shapeMode = ShapeMode.ROUNDRECT;
                break;
            default:
                break;
        }
        shapes.add(new Shape(x,y,shapeMode));
    }
    public enum ShapeMode {
        TRIANGLE,CIRCLE,SQUARE,ROUNDRECT;
    }
    private class ShapeFactory {
        public Shape newInstance(float x,float y,ShapeMode shapeMode) {
            return new Shape(x,y,shapeMode);
        }
    }
    private class Shape {
        private float x,y,deg=0,degSpeed = 20,size=10,time=0;
        private ShapeMode shapeMode;
        private  Shape(float x,float y,ShapeMode shapeMode) {
            this.x = x;
            this.y = y;
            this.shapeMode = shapeMode;
        }
        public int hashCode() {
            return (int)(Math.sqrt(Math.pow(x,2)+Math.pow(y,2)));
        }
        public void draw(Canvas canvas,Paint paint) {
            if(time == 0) {
                size = canvas.getWidth()/20;
            }
            canvas.save();
            canvas.translate(x,y);
            canvas.rotate(deg);
            switch(shapeMode) {
                case CIRCLE:
                    canvas.drawCircle(0,0,size,paint);
                    break;
                case TRIANGLE:
                    Path path = new Path();
                    path.moveTo(-size/2,size/2);
                    path.lineTo(size/2,size/2);
                    path.lineTo(0,-size/2);
                    path.lineTo(-size/2,size/2);
                    canvas.drawPath(path,paint);
                    break;
                case SQUARE:
                    canvas.drawRect(new RectF(-size/2,-size/2,size/2,size/2),paint);
                    break;
                case ROUNDRECT:
                    canvas.drawRoundRect(new RectF(-size/2,-size/2,size/2,size/2),size/10,size/10,paint);
                    break;
                default:
                    break;
            }
            canvas.restore();
            time++;
        }
        public void update() {
            deg+=degSpeed;
        }
    }
}
