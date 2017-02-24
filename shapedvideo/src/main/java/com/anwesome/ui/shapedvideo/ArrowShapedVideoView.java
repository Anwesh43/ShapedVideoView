package com.anwesome.ui.shapedvideo;

import android.content.Context;
import android.graphics.Path;
import android.util.AttributeSet;

/**
 * Created by anweshmishra on 24/02/17.
 */
public class ArrowShapedVideoView extends ShapedVideoView {
    public ArrowShapedVideoView(Context context, AttributeSet attrs) {
        super(context,attrs);
    }
    public ArrowShapedVideoView(Context context) {
        super(context);
    }
    public Path definePath(Path path,int w,int h) {
        path.moveTo(w/2,3*h/4);
        path.lineTo(0,h);
        path.lineTo(w/2,0);
        path.lineTo(w,h);
        path.lineTo(w/2,3*h/4);
        return path;
    }
}
