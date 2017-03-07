package com.anwesome.ui.shapedvideo;

import android.content.Context;
import android.graphics.*;
import android.util.AttributeSet;

/**
 * Created by anweshmishra on 07/03/17.
 */
public class RollingBallVideoView extends ShapedVideoView {
    public RollingBallVideoView(Context context) {
        super(context);
    }
    public RollingBallVideoView(Context context, AttributeSet attrs) {
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
