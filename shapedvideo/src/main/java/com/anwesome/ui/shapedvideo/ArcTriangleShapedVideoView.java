package com.anwesome.ui.shapedvideo;

import android.content.Context;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;

/**
 * Created by anweshmishra on 16/02/17.
 */
public class ArcTriangleShapedVideoView extends ShapedVideoView{
    public ArcTriangleShapedVideoView(Context context, AttributeSet attrs) {
        super(context,attrs);
    }
    public Path definePath(Path path,int w,int h) {
        path.moveTo(w,h/2);
        path.addArc(new RectF(0,0,w,h),0,180);
        path.lineTo(w/2,0);
        path.lineTo(w,h/2);
        return path;
    }
}
