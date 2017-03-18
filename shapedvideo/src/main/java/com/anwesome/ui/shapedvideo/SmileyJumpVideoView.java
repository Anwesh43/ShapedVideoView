package com.anwesome.ui.shapedvideo;

import android.content.Context;
import android.graphics.*;
import android.util.AttributeSet;

import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Created by anweshmishra on 18/03/17.
 */
public class SmileyJumpVideoView extends ShapedVideoView {
    private int w,h,time;
    private Bitmap smileyBitmap;
    private ConcurrentLinkedQueue<JumpingSmiley> jumpingSmileys = new ConcurrentLinkedQueue<>();
    public SmileyJumpVideoView(Context context) {
        super(context);
    }
    public SmileyJumpVideoView(Context context, AttributeSet attrs) {
        super(context,attrs);
    }
    public void drawElements(Canvas canvas,Paint paint) {
        if(time == 0) {
            w = canvas.getWidth();
            h = canvas.getHeight();
            smileyBitmap = BitmapFactory.decodeResource(getResources(),R.drawable.sml1);
        }
        for(JumpingSmiley jumpingSmiley:jumpingSmileys) {
            jumpingSmiley.draw(canvas,paint);
            jumpingSmiley.update();
        }
        time++;
        try {
            Thread.sleep(50);
            invalidate();
        }
        catch (Exception ex)  {

        }
    }
    public boolean shouldDraw() {
        return true;
    }
    public void handleTap(float x,float y) {
        if(time>0) {
            jumpingSmileys.add(new JumpingSmiley(x, y, w / 6,smileyBitmap ));
        }
    }

    private class JumpingSmiley {
        private float x,y,initY,dir=-1;
        private Bitmap bitmap;
        public JumpingSmiley(float x,float y,float size,Bitmap bitmap) {
            this.x = x;
            this.y = y;
            this.initY = y;
            this.bitmap = Bitmap.createScaledBitmap(bitmap,(int)size,(int)size,true);
        }
        public void draw(Canvas canvas,Paint paint) {
            canvas.save();
            canvas.translate(x,y);
            canvas.drawBitmap(bitmap,-bitmap.getWidth()/2,-bitmap.getHeight()/2,paint);
            paint.setColor(Color.YELLOW);
            canvas.drawLine(0,initY-y,0,0,paint);
            canvas.restore();
        }
        public void update() {
            y+=10*dir;
            if(y<=initY-200) {
                dir = 1;
            }
            if(y>=initY) {
                dir = -1;
            }
        }
    }
}
