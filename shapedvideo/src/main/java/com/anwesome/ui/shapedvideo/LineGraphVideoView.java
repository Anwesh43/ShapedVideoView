package com.anwesome.ui.shapedvideo;

import android.content.Context;
import android.graphics.*;
import android.util.AttributeSet;

/**
 * Created by anweshmishra on 31/03/17.
 */
public class LineGraphVideoView extends ShapedVideoView {
    public LineGraphVideoView(Context context) {
        super(context);
    }
    public LineGraphVideoView(Context context, AttributeSet attrs) {
        super(context,attrs);
    }
    public boolean shouldDraw() {
        return true;
    }
    public void drawElements(Canvas canvas, Paint paint) {

    }
    public void handleTap(float x,float y) {

    }
}
