package com.anwesome.ui.shapedvideo;

import android.content.Context;
import android.graphics.*;
import android.util.AttributeSet;

/**
 * Created by anweshmishra on 23/02/17.
 */
public class DoubleCircleShapedView extends ShapedVideoView {
    public DoubleCircleShapedView(Context context, AttributeSet attrs) {
        super(context,attrs);
    }
    public DoubleCircleShapedView(Context context) {
        super(context);
    }
    public Path definePath(Path path,int w,int h) {
        for(int i=0;i<4;i++) {
            int x = w/2*(i%2)+w/4,y = h/2*(i/2)+h/4,r1=w/4,r2 =h/4;
            path.addArc(new RectF(x-r1,y-r2,x+r1,y+r2),0,360);
        }
        return path;
    }
}
