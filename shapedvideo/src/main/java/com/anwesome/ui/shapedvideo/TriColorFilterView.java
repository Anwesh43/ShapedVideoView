package com.anwesome.ui.shapedvideo;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;

/**
 * Created by anweshmishra on 10/03/17.
 */
public class TriColorFilterView extends ShapedVideoView {
    public boolean shouldDraw() {
        return true;
    }
    public TriColorFilterView(Context context) {
        super(context);
    }
    public TriColorFilterView(Context context, AttributeSet attrs) {
        super(context);
    }
    public void drawElements(Canvas canvas, Paint paint) {

    }
    public void handleTap(float x,float y) {

    }
    private class TriColorFilter {
        
    }
}
