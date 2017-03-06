package com.anwesome.ui.shapedvideo;

import android.content.Context;
import android.graphics.*;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Created by anweshmishra on 06/03/17.
 */
public class TriangleColorFilterShapedVideoView extends ShapedVideoView {
    private int w,h,time = 0;
    private boolean isAnimated = false;
    private TriangleColorFilterShapedVideoView prev,current;
    private List<TriangleColorFilter> filters = new LinkedList<>();
    public TriangleColorFilterShapedVideoView(Context context) {
        super(context);
    }
    protected boolean shouldDraw() {
        return true;
    }
    public void drawElements(Canvas canvas,Paint paint) {
        if(time == 0) {
            w = canvas.getWidth();
            h = canvas.getHeight();
            String colorHexes[] = {"3F51B5","e53935","00796B","F57F17"};
            for(String colorHex:colorHexes) {
                TriangleColorFilter triangleColorFilter = new TriangleColorFilter(colorHex);
                filters.add(triangleColorFilter);
            }
        }
        paint.setStyle(Paint.Style.FILL);
        time++;
        if(!isAnimated) {
            try {
                Thread.sleep(50);
                invalidate();
            }
            catch (Exception ex) {

            }
        }
    }
    public void handleTap(float x,float y) {

    }
    private class TriangleColorFilter {
        private float x,y,size,scale=0,rot=0,dir =0;
        private String colorHex = "#99";
        public TriangleColorFilter(String colorHex) {
            this.x = w/2;
            this.y = h/2;
            this.size = w/4;
            this.colorHex+=colorHex.replace("#","");
        }
        public void draw(Canvas canvas,Paint paint) {
            paint.setColor(Color.parseColor(colorHex));
            canvas.save();
            canvas.translate(x,y);
            canvas.scale(scale,scale);
            canvas.rotate(rot);
            Path path = new Path();
            path.moveTo(size/2,size/2);
            path.lineTo(0,-size/2);
            path.lineTo(-size/2,size/2);
            path.lineTo(size/2,size/2);
            canvas.drawPath(path,paint);
            canvas.restore();
        }
        public void update() {
            rot+=10*dir;
        }
        public void show() {
            dir  = 1;
        }
        public void clear() {
            dir = -1;
        }
        public boolean stopped() {
            return dir == 0;
        }
        public int hashCode() {
            return (int)(scale+rot)+colorHex.hashCode();
        }
    }
}
