package com.anwesome.ui.shapedvideo;

import android.content.Context;
import android.util.AttributeSet;
import android.graphics.*;
import android.view.MotionEvent;

/**
 * Created by anweshmishra on 01/03/17.
 */
public class CircularColorFilterShapeVideoView extends ShapedVideoView{
    private boolean isAnimated = false;
    public CircularColorFilterShapeVideoView(Context context) {
        super(context);
    }
    public CircularColorFilterShapeVideoView(Context context, AttributeSet attrs) {
        super(context,attrs);
    }
    public boolean shouldDraw() {
        return true;
    }
    public void onDraw(Canvas canvas) {
        if(isAnimated) {
            try {
                Thread.sleep(50);
                invalidate();
            } catch (Exception ex) {

            }
        }
    }
    public void handleTap(float x,float y) {

    }
}
