package com.anwesome.ui.shapedvideo;

import android.content.Context;
import android.graphics.Path;
import android.util.AttributeSet;

/**
 * Created by anweshmishra on 09/02/17.
 */
public class DumbleShapedVideoView extends ShapedVideoView {
    public DumbleShapedVideoView(Context context, AttributeSet attrs) {
        super(context,attrs);
    }
    public DumbleShapedVideoView(Context context) {
        super(context);
    }
    public Path definePath(Path path,int w,int h) {
        path.lineTo(w/4,h/2);
        path.lineTo(0,0);
        path.lineTo(w,0);
        path.lineTo(3*w/4,h/2);
        path.lineTo(w,h);
        path.lineTo(0,h);
        path.lineTo(w/4,h/2);
        return path;
    }
}
