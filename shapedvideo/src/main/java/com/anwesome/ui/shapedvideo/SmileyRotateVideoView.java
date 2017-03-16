package com.anwesome.ui.shapedvideo;


import android.content.Context;
import android.graphics.*;
import android.util.AttributeSet;

/**
 * Created by anweshmishra on 16/03/17.
 */
public class SmileyRotateVideoView extends ShapedVideoView {
    public SmileyRotateVideoView(Context context) {
        super(context);
    }
    public SmileyRotateVideoView(Context context, AttributeSet attrs) {
        super(context,attrs);
    }
    public boolean shouldDraw() {
        return true;
    }
    public void drawElements(Canvas canvas,Paint paint) {

    }
    public void handleTap(float x,float y) {

    }
}
