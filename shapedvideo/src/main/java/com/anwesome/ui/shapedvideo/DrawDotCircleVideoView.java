package com.anwesome.ui.shapedvideo;

import android.content.Context;
import android.util.AttributeSet;

/**
 * Created by anweshmishra on 14/03/17.
 */
public class DrawDotCircleVideoView extends ShapedVideoView{
    public DrawDotCircleVideoView(Context context, AttributeSet attrs) {
        super(context,attrs);
    }
    public DrawDotCircleVideoView(Context context) {
        super(context);
    }
    protected boolean shouldDraw() {
        return true;
    }
}
