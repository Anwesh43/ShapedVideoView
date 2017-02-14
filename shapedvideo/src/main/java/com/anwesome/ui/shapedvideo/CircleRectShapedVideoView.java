package com.anwesome.ui.shapedvideo;

import android.content.Context;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;

/**
 * Created by anweshmishra on 14/02/17.
 */
public class CircleRectShapedVideoView extends ShapedVideoView{
    public CircleRectShapedVideoView(Context context, AttributeSet attrs) {
        super(context,attrs);
    }
    public Path definePath(Path path,int w,int h) {
        path.moveTo(w,h/2);
        path.lineTo(w,h);
        path.lineTo(0,h);
        path.lineTo(0,h/2);
        path.addArc(new RectF(0,0,w,h),180,180);
        return path;
    }
}
