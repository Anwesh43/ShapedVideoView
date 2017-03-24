package com.anwesome.ui.shapedvideo;

import android.content.Context;
import android.graphics.*;
import android.util.AttributeSet;


/**
 * Created by anweshmishra on 24/03/17.
 */
public class ChargingBarVideoView extends ShapedVideoView {
    public ChargingBarVideoView(Context context) {
        super(context);
    }
    public ChargingBarVideoView(Context context, AttributeSet attrs) {
        super(context,attrs);
    }
    public void handleTap(float x,float y) {

    }
    protected boolean shouldDraw() {
        return true;
    }
    public void drawElements(Canvas canvas, Paint paint) {

    }
}
