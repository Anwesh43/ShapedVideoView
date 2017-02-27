package com.anwesome.ui.shapedvideo;

import android.content.Context;
import android.util.AttributeSet;

/**
 * Created by anweshmishra on 27/02/17.
 */
public class ColorFilterShapedVideoView extends ShapedVideoView{
    public ColorFilterShapedVideoView(Context context) {
        super(context);
    }
    public ColorFilterShapedVideoView(Context context, AttributeSet attrs) {
        super(context,attrs);
    }
    protected boolean shouldDraw() {
        return  true;
    }
}
