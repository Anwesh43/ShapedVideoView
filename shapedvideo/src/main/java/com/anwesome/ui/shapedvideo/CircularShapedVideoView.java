package com.anwesome.ui.shapedvideo;

import android.content.Context;
import android.graphics.*;
import android.util.AttributeSet;
import android.view.SurfaceHolder;


/**
 * Created by anweshmishra on 06/02/17.
 */
public class CircularShapedVideoView extends ShapedVideoView{
    public CircularShapedVideoView(Context context) {
        super(context);
    }
    public CircularShapedVideoView(Context context,AttributeSet attributeSet) {
        super(context,attributeSet);
    }
    public Path definePath(Path path,int w,int h) {
        path.addCircle(w/2,h/2,w/2, Path.Direction.CCW);
        return path;
    }
}
