package com.anwesome.ui.shapedvideo;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
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
        private float deg = 0,x,y,size;
        public CamShape(float x,float y) {
            this.x = x;
            this.y = y;
            this.size = w/10;
        }
        public void draw(Canvas canvas,Paint paint) {
            
        }
        public void update() {

        }

    }
}
