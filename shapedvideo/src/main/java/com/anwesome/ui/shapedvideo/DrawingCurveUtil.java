package com.anwesome.ui.shapedvideo;

import android.graphics.Path;

/**
 * Created by anweshmishra on 29/03/17.
 */
public class DrawingCurveUtil {
    public static void drawCircularPath(Path path,float centerX, float centerY, float r, float startDeg, float endDeg) {
        if(startDeg!=endDeg) {
            float dir = (endDeg-startDeg)/(Math.abs(endDeg-startDeg));
            for(float deg=startDeg;startDeg!=endDeg;deg+=dir) {
                float x = centerX+r*(float)(Math.cos(deg*Math.cos(deg*Math.PI/180)));
                float y = centerY+r*(float)(Math.sin(deg*Math.PI/180));
                path.lineTo(x,y);
            }
        }
    }
}
