package com.anwesome.ui.shapedvideo;

import android.content.Context;
import android.graphics.Path;
import android.util.AttributeSet;

/**
 * Created by anweshmishra on 19/02/17.
 */
public class SemiCircRectShapedVideoView extends ShapedVideoView {
    public SemiCircRectShapedVideoView(Context context, AttributeSet attrs) {
        super(context,attrs);
    }
    public Path definePath(Path path,int w,int h) {
        path.moveTo(w/2+w/4,h/2) ;
        path.lineTo(w,h/2);
        path.lineTo(w,h);
        path.lineTo(0,h);
        path.lineTo(0,h/2);
        path.lineTo(w/2-w/4,h/2);
        path.addCircle(w/2,h/2,w/4, Path.Direction.CCW);
        return path;
    }
}
