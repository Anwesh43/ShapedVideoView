package com.anwesome.ui.shapedvideo;

import android.content.Context;
import android.graphics.Path;
import android.util.AttributeSet;

/**
 * Created by anweshmishra on 13/02/17.
 */
public class DiamondShapedVideoView extends ShapedVideoView{
    public DiamondShapedVideoView(Context context,AttributeSet attrs) {
        super(context,attrs);
    }
    public Path definePath(Path path,int w,int h) {
        path.moveTo(0,h/2);
        path.lineTo(w/2,0);
        path.lineTo(w,h/2);
        path.lineTo(w/2,h);
        path.lineTo(0,h/2);
        return path;
    }
}
