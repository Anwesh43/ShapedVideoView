package com.anwesome.ui.shapedvideo;

import android.content.Context;
import android.graphics.Path;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Created by anweshmishra on 11/02/17.
 */
public class UserDefinedShapedVideoView extends ShapedVideoView {
    private boolean isDown = false,isDone = false;
    private ConcurrentLinkedQueue<PointF> points = new ConcurrentLinkedQueue<>();
    public UserDefinedShapedVideoView(Context context) {
        super(context);
    }
    public UserDefinedShapedVideoView(Context context, AttributeSet attrs) {
        super(context,attrs);
    }
    public Path definePath(Path path,int w,int h) {
        int i =0;
        if(isDone) {
            for (PointF pointF : points) {
                if (i == 0) {
                    path.moveTo(pointF.x, pointF.y);
                } else {
                    path.lineTo(pointF.x, pointF.y);
                }
                i++;
            }
            return path;
        }
        return null;
    }
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX(),y = event.getY();
        switch(event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if(!isDone && !isDown) {
                    points.add(new PointF(x,y));
                    isDown = true;
                }
                break;
            case MotionEvent.ACTION_MOVE:
                if(!isDone && isDown) {
                    points.add(new PointF(x,y));
                }
                break;
            case MotionEvent.ACTION_UP:
                if(!isDone && isDown) {
                    isDown = false;
                    isDone = true;
                    postInvalidate();
                }
                break;
            default:
                break;
        }

        return true;
    }
}
