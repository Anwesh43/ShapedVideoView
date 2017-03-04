package com.anwesome.ui.shapedvideo;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;

/**
 * Created by anweshmishra on 04/03/17.
 */
public class LazyLoadShapedVideoView extends ShapedVideoView {
    private int time = 0;
    public LazyLoadShapedVideoView(Context context, AttributeSet attrs) {
        super(context,attrs);
    }
    public void drawElements(Canvas canvas,Paint paint) {
        time++;
    }
    public void start() {

    }
}
