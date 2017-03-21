package com.anwesome.ui.shapedvideo;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by anweshmishra on 21/03/17.
 */
public class GridTraverseVideoView extends ShapedVideoView {
    public GridTraverseVideoView(Context context, AttributeSet attrs) {
        super(context,attrs);
    }
    public void drawElements(Canvas canvas, Paint paint) {

    }
    public boolean onTouchEvent(MotionEvent event) {
        return true;
    }
}
