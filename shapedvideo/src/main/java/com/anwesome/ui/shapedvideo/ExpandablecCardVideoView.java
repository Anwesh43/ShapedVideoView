package com.anwesome.ui.shapedvideo;

import android.content.Context;
import android.graphics.*;
import android.util.AttributeSet;

/**
 * Created by anweshmishra on 01/04/17.
 */
public class ExpandablecCardVideoView extends ShapedVideoView {
    public ExpandablecCardVideoView(Context context) {
        super(context);
    }
    public ExpandablecCardVideoView(Context context, AttributeSet attrs) {
        super(context,attrs);
    }
    public void handleTap(float x,float y) {

    }
    public void drawElements(Canvas canvas, Paint paint) {
    }
    public boolean shouldDraw() {
        return true;
    }
}
