package com.anwesome.ui.shapedvideo;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;

/**
 * Created by anweshmishra on 27/03/17.
 */
public class CamShapeVideoView extends ShapedVideoView {
    private int time = 0,w,h;
    public CamShapeVideoView(Context context) {
        super(context);
    }
    public CamShapeVideoView(Context context, AttributeSet attrs) {
        super(context,attrs);
    }
    public void drawElements(Canvas canvas, Paint paint) {
        if(time == 0) {
            w = canvas.getWidth();
            h = canvas.getHeight();
        }
    }
    public void handleTap(float x,float y) {

    }
    private class CamShape {
        private boolean stopped = false;
        private float deg = 0,x,y,size;
        public CamShape(float x,float y) {
            this.x = x;
            this.y = y;
            this.size = w/10;
        }
        public void draw(Canvas canvas,Paint paint) {
            canvas.save();
            canvas.translate(x,y);
            canvas.rotate(deg);
            drawCamShape(canvas,paint);
            canvas.restore();
        }
        public void drawCamShape(Canvas canvas,Paint paint) {
            paint.setStyle(Paint.Style.FILL);
            paint.setColor(Color.parseColor("#99616161"));
            canvas.drawRoundRect(new RectF(-size/2,-size/3,size/2,size/3),size/4,size/6,paint);
            canvas.drawRoundRect(new RectF(-size/4,-size/2,size/4,size/2),size/16,size/16,paint);
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(Color.parseColor("#99263238"));
            canvas.drawCircle(0,0,size/8,paint);
        }
        public void update() {
            y+=h/12;
            deg+=20;
            if(y>=h) {
                stopped = true;
            }
        }

    }
}
