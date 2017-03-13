package com.anwesome.ui.shapedvideo;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;

/**
 * Created by anweshmishra on 13/03/17.
 */
public class DrawLineVideoView extends ShapedVideoView {
    public DrawLineVideoView(Context context) {
        super(context);
    }
    public DrawLineVideoView(Context context, AttributeSet attrs) {
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
