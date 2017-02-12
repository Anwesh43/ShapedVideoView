package com.anwesome.ui.shapedvideo;

import android.content.Context;
import android.util.AttributeSet;
import android.graphics.Path;
/**
 * Created by anweshmishra on 12/02/17.
 */
public class TrapezoidalShapedVideoView extends ShapedVideoView{
    public TrapezoidalShapedVideoView(Context context, AttributeSet attrs) {
        super(context,attrs);
    }
    public TrapezoidalShapedVideoView(Context context) {
        super(context);
    }
    public Path definePath(Path path,int w,int h) {
        path.moveTo(w/4,h);
        path.lineTo(3*w/4,h);
        path.lineTo(w,0);
        path.lineTo(0,0);
        path.lineTo(w/4,h);
        return path;
    }
}
