package com.anwesome.ui.shapedvideo;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;

import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Created by anweshmishra on 15/03/17.
 */
public class DrawSmileyVideoView extends ShapedVideoView{
    private int w,h,time = 0;
    private int res[] = {R.drawable.sml1,R.drawable.sml2,R.drawable.sml3};
    private ConcurrentLinkedQueue<Smiley> smileys = new ConcurrentLinkedQueue<>();
    public DrawSmileyVideoView(Context context) {
        super(context);
    }
    public DrawSmileyVideoView(Context context, AttributeSet attrs) {
        super(context,attrs);
    }
    public boolean shouldDraw() {
        return true;
    }
    public void drawElements(Canvas canvas, Paint paint) {
        if(time == 0) {
            w = canvas.getWidth();
            h = canvas.getHeight();
        }
        for(Smiley smiley:smileys) {
            smiley.drawSmiley(canvas,paint);
            smiley.update();
        }
        time++;
        try {
            Thread.sleep(100);
            invalidate();
        }
        catch (Exception ex) {

        }
    }
    public void handleTap(float x,float y) {
        smileys.add(new Smiley(BitmapFactory.decodeResource(getResources(),res[smileys.size()%3]),x,y));
    }
    private class Smiley {
        private Bitmap bitmap;
        private int mode = 0;
        private float x,y,deg=0,scale=0f,dir = 1;
        private Smiley(Bitmap bitmap,float x,float y) {
            this.bitmap = bitmap;
            this.x = x;
            this.y = y;
            bitmap = Bitmap.createScaledBitmap(bitmap,w/10,w/10,true);
        }
        public void drawSmiley(Canvas canvas,Paint paint) {
            canvas.save();
            canvas.translate(x,y);
            canvas.rotate(deg);
            canvas.drawBitmap(bitmap,-w/20,-w/20,paint);
            canvas.restore();
        }
        public void update() {
            switch (mode) {
                case 0:
                    deg+=72;
                    scale+=0.2f;
                    if(scale>=1) {
                        scale = 1;
                        deg = 360;
                        mode = 1;
                    }
                    break;
                case 1:
                    scale+=0.1f*dir;
                    if(scale>=1.3f) {
                        dir = -1;
                    }
                    if(scale<=1) {
                        dir = 1;
                    }
                    break;
                default:
                    break;
            }
        }
        public int hashCode() {
            return bitmap.hashCode()+(int)(x+y+deg);
        }
    }
}
