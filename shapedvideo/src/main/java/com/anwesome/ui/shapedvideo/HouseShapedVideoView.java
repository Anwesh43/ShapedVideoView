package com.anwesome.ui.shapedvideo;

import android.content.Context;
import android.graphics.Path;
import android.util.AttributeSet;

/**
 * Created by anweshmishra on 10/02/17.
 */
public class HouseShapedVideoView extends ShapedVideoView {
    public HouseShapedVideoView(Context context, AttributeSet attributeSet) {
        super(context,attributeSet);
    }
    public HouseShapedVideoView(Context context) {
        super(context);
    }
    public Path definePath(Path path,int w,int h) {
        path.moveTo(0,h/2);
        path.lineTo(w/2,0);
        path.lineTo(w,h/2);
        path.lineTo(w,h);
        path.lineTo(0,h);
        path.lineTo(0,h/2);
        return path;
    }
}
