package com.anwesome.ui.shapedvideo;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;

/**
 * Created by anweshmishra on 02/04/17.
 */
public class CircFillVideoView extends ShapedVideoView{
    public CircFillVideoView(Context context) {
        super(context);
    }
    public CircFillVideoView(Context context, AttributeSet attrs) {
        super(context,attrs);
    }
    public void drawElements(Canvas canvas, Paint paint) {

    }
    protected boolean shouldDraw() {
        return true;
    }
    public void handleTap(float x,float y) {

    }
}
