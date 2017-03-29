package com.anwesome.ui.shapedvideo;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;

/**
 * Created by anweshmishra on 29/03/17.
 */
public class DrawAvatarVideoView extends ShapedVideoView {
    public DrawAvatarVideoView(Context context) {
        super(context);
    }
    public DrawAvatarVideoView(Context context, AttributeSet attrs) {
        super(context,attrs);
    }
    public boolean shouldDraw() {
        return true;
    }
    public void drawElements(Canvas canvas, Paint paint) {
        paint.setStyle(Paint.Style.FILL);
    }
    public void handleTap(float x,float y) {

    }
    private class Avatar {
        private float x,y,size,deg = 0;
        public Avatar(float x,float y,float size) {
            this.x = x;
            this.y = y;
            this.size = size;
        }
        public void drawAvatar(Canvas canvas,Paint paint) {
            paint.setColor(Color.parseColor("#99FFFFFF"));
            canvas.save();
            canvas.translate(x,y);
            canvas.rotate(deg);
            drawFace(canvas,paint);
            drawShoulderAndNeck(canvas,paint);
            canvas.restore();
        }
        public void update() {
            deg+=20;
        }
        private void drawFace(Canvas canvas,Paint paint) {
            canvas.drawCircle(0,-size/4,size/8,paint);
        }
        private void drawShoulderAndNeck(Canvas canvas,Paint paint) {
            Path path = new Path();
            path.moveTo(size/2,size/2);
            DrawingCurveUtil.drawCircularPath(path,0,size/2,size/2,360,300);
            float newY = (float)((size/2)*(1-Math.sqrt(3)));
            DrawingCurveUtil.drawCircularPath(path,0,newY,size/2,60,120);
            DrawingCurveUtil.drawCircularPath(path,0,size/2,size/2,240,180);
            path.lineTo(size/2,size/2);
            canvas.drawPath(path,paint);


        }

        public boolean handleTap(float x,float y) {
            return x>=this.x-size/2 && x<=this.x+size/2 && y>=this.y-size/2 && y<=this.y+size/2;
        }
    }
}
