package com.anwesome.ui.shapedvideo;

import android.content.Context;
import android.graphics.*;
import android.util.AttributeSet;

/**
 * Created by anweshmishra on 25/03/17.
 */
public class StarEffectVideoView extends ShapedVideoView {
    public StarEffectVideoView(Context context) {
        super(context);
    }
    public StarEffectVideoView(Context context, AttributeSet attrs) {
        super(context,attrs);
    }
    public void drawElements(Canvas canvas,Paint paint) {

    }
    public boolean shouldDraw() {
        return true;
    }
}
