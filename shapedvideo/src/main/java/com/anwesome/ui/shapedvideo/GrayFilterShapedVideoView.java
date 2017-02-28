package com.anwesome.ui.shapedvideo;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;

/**
 * Created by anweshmishra on 28/02/17.
 */
public class GrayFilterShapedVideoView extends ShapedVideoView {
    public GrayFilterShapedVideoView(Context context) {
        super(context);
    }
    public GrayFilterShapedVideoView(Context context, AttributeSet attrs) {
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
