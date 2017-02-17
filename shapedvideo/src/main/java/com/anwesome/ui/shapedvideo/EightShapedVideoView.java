package com.anwesome.ui.shapedvideo;

import android.content.Context;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;

/**
 * Created by anweshmishra on 17/02/17.
 */
public class EightShapedVideoView extends ShapedVideoView{
    public EightShapedVideoView(Context context, AttributeSet attrs) {
        super(context,attrs);
    }
    public Path definePath(Path path,int w,int h) {
        float r = h/4;
        float y_center1 = h/2+h/4;
        float x = (float)(w/2+r*Math.cos(-60*Math.PI/180)),y = (float)(y_center1+r*Math.sin(-60*Math.PI/180));
        float y_center2 = y-r*(float)Math.cos(Math.PI/3);
        path.moveTo(x,y);
        path.arcTo(new RectF(0,h/2,w,h),-60,300);
        path.arcTo(new RectF(0,y_center2-r,w,y_center2+r),120,300);
        return path;
    }
}
