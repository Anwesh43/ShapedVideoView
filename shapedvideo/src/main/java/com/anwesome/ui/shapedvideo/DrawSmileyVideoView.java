package com.anwesome.ui.shapedvideo;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;

/**
 * Created by anweshmishra on 15/03/17.
 */
public class DrawSmileyVideoView extends ShapedVideoView{
    public DrawSmileyVideoView(Context context) {
        super(context);
    }
    public boolean shouldDraw() {
        return true;
    }
    public void drawElements(Canvas canvas, Paint paint) {

    }
    public void handleTap(float x,float y) {

    }
}
