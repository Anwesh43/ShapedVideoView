package com.anwesome.ui.shapedvideo;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;

/**
 * Created by anweshmishra on 04/04/17.
 */
public class CircMoveVideoView extends ShapedVideoView {
    public CircMoveVideoView(Context context) {
        super(context);
    }
    public CircMoveVideoView(Context context, AttributeSet attrs) {
        super(context,attrs);
    }
    public void drawElements(Canvas canvas, Paint paint) {

    }
    public boolean shouldDraw() {
        return true;
    }
    public void handleTap(float x,float y) {

    }
}
