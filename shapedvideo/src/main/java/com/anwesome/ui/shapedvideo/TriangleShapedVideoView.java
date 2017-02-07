package com.anwesome.ui.shapedvideo;

import android.content.Context;
import android.graphics.Path;
import android.util.AttributeSet;

/**
 * Created by anweshmishra on 08/02/17.
 */
public class TriangleShapedVideoView extends ShapedVideoView {
    public TriangleShapedVideoView(Context context, AttributeSet attrs) {
        super(context,attrs);
    }
    public TriangleShapedVideoView(Context context) {
        super(context);
    }
    public Path definePath(Path path,int w,int h) {
        path.moveTo(w,h);
        path.lineTo(w/2,0);
        path.lineTo(0,h);
        path.lineTo(w,h);
        return path;
    }
}
