package com.anwesome.ui.shapedvideo;

import android.content.Context;
import android.graphics.*;
import android.util.AttributeSet;

/**
 * Created by anweshmishra on 05/03/17.
 */
public class WindowColorFilterVideoView extends ShapedVideoView {
    private int w,h,time = 0;
    protected boolean shouldDraw() {
        return true;
    }
    public WindowColorFilterVideoView(Context context){
        super(context);
    }
    public WindowColorFilterVideoView(Context context, AttributeSet attrs) {
        super(context,attrs);
    }
    public void drawElements(Canvas canvas,Paint paint) {
        if(time == 0) {

        }
        time++;
    }
    private class WindowColorFilter {

    }
    private class IndividualFilterRect {
        private float x,y,w_rect,h_rect,h_init=0,dir = 0;
        private String colorHex="#AA";
        public IndividualFilterRect(String colorHex) {
            this.w_rect = w;
            this.h_rect = h/2;
            this.x = 0;
            this.y = 0;
            this.colorHex += colorHex.replace("#","");
        }
        public void start() {
            dir = 1;
        }
        public void draw(Canvas canvas,Paint paint) {
            paint.setColor(Color.parseColor(colorHex));
            canvas.drawRect(new RectF(x,y,w_rect,h_init),paint);
        }
        public void update() {
            this.h_init+=dir*(this.h_rect/8);
            if(this.h_init>=h_rect || this.h_init<=0) {
                dir = 0;
                if(this.h_init>=this.h_rect) {
                    this.h_init = this.h_rect;
                }
                if(this.h_init<=0) {
                    this.h_init = 0;
                }
            }
        }
        public int hashCode() {
            return (int)(dir+h_init);
        }
    }
}
