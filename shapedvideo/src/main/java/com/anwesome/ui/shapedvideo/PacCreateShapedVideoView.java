package com.anwesome.ui.shapedvideo;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;

/**
 * Created by anweshmishra on 02/03/17.
 */
public class PacCreateShapedVideoView extends ShapedVideoView {
    public PacCreateShapedVideoView(Context context){
        super(context);
    }
    public PacCreateShapedVideoView(Context context, AttributeSet attrs){
        super(context,attrs);
    }
    public boolean shouldDraw() {
        return true;
    }
    public void onDraw(Canvas canvas) {
        try {
            Thread.sleep(100);
            invalidate();
        }
        catch (Exception ex) {

        }
    }
    private class PacCreator {
        public PacCreator() {

        }
    }
    
}
