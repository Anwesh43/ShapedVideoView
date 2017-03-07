package com.anwesome.ui.shapedvideo;

import android.content.Context;
import android.graphics.*;
import android.util.AttributeSet;
import java.util.*;


/**
 * Created by anweshmishra on 06/03/17.
 */
public class TriangleColorFilterShapedVideoView extends ShapedVideoView {
    private int w,h,time = 0,index = 0;
    private boolean isAnimated = false;
    private TriangleColorFilter prev,current;
    private List<TriangleColorFilter> filters = new LinkedList<>();
    public TriangleColorFilterShapedVideoView(Context context) {
        super(context);
    }
    public TriangleColorFilterShapedVideoView(Context context, AttributeSet attrs) {
        super(context,attrs);
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
        for(TriangleColorFilter filter:filters) {
            filter.draw(canvas,paint);
        }
        time++;
        if(isAnimated) {
            if(prev!=null) {
                prev.update();
                if(prev.stopped()) {
                    current.show();
                    prev = null;
                }
            }
            else {
                current.update();
                if(current.stopped()) {
                    prev = current;
                    current = null;
                    isAnimated = false;
                    index++;
                    index%=filters.size();
                }
            }
            try {
                Thread.sleep(50);
                invalidate();
            }
            catch (Exception ex) {

            }
        }
    }
    public void handleTap(float x,float y) {
        if(!isAnimated && current == null) {
            current = filters.get(index);
            if(prev == null) {
                current.show();
            }
            else {
                prev.clear();
            }
            isAnimated = true;
            postInvalidate();
        }
    }
    private class TriangleColorFilter {
        private float x,y,size,scale=0,rot=0,dir =0;
        private String colorHex = "#99";
        public TriangleColorFilter(String colorHex) {
            this.x = w/2;
            this.y = h/2;
            this.size = 2*w/3;
            this.colorHex+=colorHex.replace("#","");
        }
        public void draw(Canvas canvas,Paint paint) {
            paint.setStyle(Paint.Style.FILL);
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
            rot+=36*dir;
            scale+=0.1f*dir;
            if(scale>=1) {
                dir = 0;
                scale = 1;
                rot = 360;
            }
            if(scale<=0) {
                dir =0;
                rot = 0;
                scale = 0;
            }
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
