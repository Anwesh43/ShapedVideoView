package com.anwesome.ui.shapedvideo;

import android.content.Context;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;

/**
 * Created by anweshmishra on 19/02/17.
 */
public class SemiCircRectShapedVideoView extends ShapedVideoView {
    public SemiCircRectShapedVideoView(Context context, AttributeSet attrs) {
        super(context,attrs);
    }
    public Path definePath(Path path,int w,int h) {
        path.moveTo(w/2+w/4,h/3) ;
        path.lineTo(w,h/3);
        path.lineTo(w,h);
        path.lineTo(0,h);
        path.lineTo(0,h/3);
        path.lineTo(w/2-w/4,h/3);
        path.addArc(new RectF(w/2-w/4,h/3-w/4,w/2+w/4,h/3+w/4),180,180);
        return path;
    }
}
