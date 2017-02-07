package com.anwesome.ui.shapedvideo;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.widget.VideoView;

/**
 * Created by anweshmishra on 07/02/17.
 */
public class EllipseShapedVideoView extends ShapedVideoView {
    public EllipseShapedVideoView(Context context) {
        super(context);
    }
    public EllipseShapedVideoView(Context context, AttributeSet attributeSet) {
        super(context,attributeSet);
    }
    public Path definePath(Path path,int w,int h) {
        path.addArc(new RectF(0,0,w,h),0,360);
        return path;
    }
}
