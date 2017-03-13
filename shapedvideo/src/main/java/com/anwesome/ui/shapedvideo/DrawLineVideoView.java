package com.anwesome.ui.shapedvideo;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PointF;
import android.util.AttributeSet;

/**
 * Created by anweshmishra on 13/03/17.
 */
public class DrawLineVideoView extends ShapedVideoView {
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

    }
    public void handleTap(float x,float y) {

    }
    private class LinePoint {
        private PointF pointF;
        private float yDir = 0,initY;
        public LinePoint(PointF pointF) {
            this.pointF = pointF;
            this.initY = pointF.y;
        }
        public void draw(Canvas canvas,Paint paint) {
            canvas.drawCircle(pointF.x,pointF.y,10,paint);
        }
        public int hashCode() {
            return pointF.hashCode()+(int)yDir;
        }
        public void jump() {
            yDir  = yDir == 0?1:yDir;
        }
        public void update() {
            pointF.y+=yDir*20;
            if(pointF.y<=initY-100) {
                pointF.y = initY-100;
            }
            else if(pointF.y>=initY){
                pointF.y = initY;
            }
        }
    }
}
