package com.anwesome.ui.shapedvideo;

import android.content.Context;
import android.graphics.*;
import android.util.AttributeSet;

/**
 * Created by anweshmishra on 03/04/17.
 */
public class DownloadButtonVideoView extends ShapedVideoView {
    public DownloadButtonVideoView(Context context) {
        super(context);
    }
    public DownloadButtonVideoView(Context context, AttributeSet attrs) {
        super(context,attrs);
    }
    public boolean shouldDraw() {
        return true;
    }
    public void drawElements(Canvas canvas, Paint paint) {

    }
    public void handleTap(float x,float y) {
        
    }
}
