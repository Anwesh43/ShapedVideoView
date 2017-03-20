package com.anwesome.ui.shapedvideo;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;

/**
 * Created by anweshmishra on 20/03/17.
 */
public class FlagVideoView extends ShapedVideoView {
    public FlagVideoView(Context context){
        super(context);
    }
    public FlagVideoView(Context context, AttributeSet attrs) {
        super(context,attrs);
    }
    public void onDraw(Canvas canvas) {

    }
    protected boolean shouldDraw() {
        return true;
    }
}
