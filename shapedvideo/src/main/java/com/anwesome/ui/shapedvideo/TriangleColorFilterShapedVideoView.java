package com.anwesome.ui.shapedvideo;

import android.content.Context;
import android.graphics.*;
/**
 * Created by anweshmishra on 06/03/17.
 */
public class TriangleColorFilterShapedVideoView extends ShapedVideoView {
    public TriangleColorFilterShapedVideoView(Context context) {
        super(context);
    }
    public void drawElements(Canvas canvas,Paint paint) {

    }
    public void handleTap(float x,float y) {

    }
    private class TriangleColorFilter {
        private float x,y,size,scale=0,rot=0,dir =0;
        public TriangleColorFilter(float x,float y,float size) {
            this.x = x;
            this.y = y;
            this.size = size;
        }
        public void draw(Canvas canvas,Paint paint) {
            canvas.save();
            canvas.translate(x,y);
            canvas.scale(scale,scale);
            canvas.rotate(rot);
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
            return (int)(scale+rot);
        }
    }
}
