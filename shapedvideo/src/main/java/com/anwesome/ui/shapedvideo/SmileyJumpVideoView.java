package com.anwesome.ui.shapedvideo;

import android.content.Context;
import android.graphics.*;
import android.util.AttributeSet;

/**
 * Created by anweshmishra on 18/03/17.
 */
public class SmileyJumpVideoView extends ShapedVideoView {
    public SmileyJumpVideoView(Context context) {
        super(context);
    }
    public SmileyJumpVideoView(Context context, AttributeSet attrs) {
        super(context,attrs);
    }
    public void drawElements(Canvas canvas,Paint paint) {

    }
    public boolean shouldDraw() {
        return true;
    }
    public void handleTap(float x,float y) {

    }
    private class JumpingSmiley {

    }
    private class Smiley {
        private float x,y,initY,dir=-1;
        private Bitmap bitmap;
        public Smiley(float x,float y,float size,Bitmap bitmap) {
            this.x = x;
            this.y = y;
            this.initY = y;
            this.bitmap = Bitmap.createScaledBitmap(bitmap,size,size,true);
        }
        public void draw(Canvas canvas,Paint paint) {
            canvas.save();
        }
    }
}
