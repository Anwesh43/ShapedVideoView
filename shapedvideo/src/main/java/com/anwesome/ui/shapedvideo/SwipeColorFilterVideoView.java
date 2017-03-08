package com.anwesome.ui.shapedvideo;

import android.content.Context;
import android.graphics.*;
import android.util.AttributeSet;

/**
 * Created by anweshmishra on 08/03/17.
 */
public class SwipeColorFilterVideoView extends ShapedVideoView {
    public SwipeColorFilterVideoView(Context context, AttributeSet attrs) {
        super(context,attrs);
    }
    public SwipeColorFilterVideoView(Context context) {
        super(context);
    }
    public boolean shouldDraw() {
        return true;
    }
    public void drawElements(Canvas canvas,Paint paint) {

    }
}
