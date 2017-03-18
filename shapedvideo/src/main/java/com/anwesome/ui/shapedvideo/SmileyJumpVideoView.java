package com.anwesome.ui.shapedvideo;

import android.content.Context;
import android.graphics.*;
import android.util.AttributeSet;

/**
 * Created by anweshmishra on 18/03/17.
 */
public class SmileyJumpVideoView extends ShapedVideoView {
    public SmileyJumpVideoView(Context context) {
        super(context);
    }
    public SmileyJumpVideoView(Context context, AttributeSet attrs) {
        super(context,attrs);
    }
    public void drawElements(Canvas canvas,Paint paint) {

    }
    public boolean shouldDraw() {
        return true;
    }
    public void handleTap(float x,float y) {

    }
}
