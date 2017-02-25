package com.anwesome.ui.shapedvideo;
import android.content.Context;
import android.graphics.Path;
import android.util.AttributeSet;

/**
 * Created by anweshmishra on 25/02/17.
 */
public class PacShapedVideoView extends ShapedVideoView{
    public PacShapedVideoView(Context context, AttributeSet attrs) {
        super(context,attrs);
    }
    public Path definePath(Path path,int w,int h) {
        path.moveTo(w/2,h/2);
        definePac(path,w,h);
        path.lineTo(w/2,h/2);
        return path;
    }
    private void definePac(Path path,int w,int h) {
        int startDeg = 30;
        for(int i=startDeg;i<=360-startDeg;i++) {
            float x = w/2+(Math.min(w,h)/2)*(float)Math.cos(i*Math.PI/180),y = h/2+(Math.min(w,h)/2)*(float)Math.sin(i*Math.PI/180);
            path.lineTo(x,y);
        }
    }
}
