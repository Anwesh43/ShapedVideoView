package com.anwesome.ui.shapedvideo;

import android.content.Context;
import android.graphics.*;
import android.util.AttributeSet;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Created by anweshmishra on 02/03/17.
 */
public class PacCreateShapedVideoView extends ShapedVideoView {
    private int w,h,time = 0;
    private ConcurrentLinkedQueue<Pac> pacs = new ConcurrentLinkedQueue<>();
    public PacCreateShapedVideoView(Context context){
        super(context);
    }
    public PacCreateShapedVideoView(Context context, AttributeSet attrs){
        super(context,attrs);
    }
    public boolean shouldDraw() {
        return true;
    }
    public void drawElements(Canvas canvas,Paint paint) {
        if(time == 0) {
            w = canvas.getWidth();
            h = canvas.getHeight();
        }
        for(Pac pac:pacs) {
            pac.draw(canvas,paint);
            pac.update();
            if(pac.stop) {
                pacs.remove(pac);
            }

        }
        time++;
        try {
            Thread.sleep(50);
            invalidate();
        }
        catch (Exception ex) {

        }
    }
    public void handleTap(float x,float y) {
        switch(pacs.size()%4) {
            case 0:
                pacs.add(new Pac(0,y,1,0));
                break;
            case 1:
                pacs.add(new Pac(w,y,-1,0));
                break;
            case 2:
                pacs.add(new Pac(x,0,0,1));
                break;
            case 3:
                pacs.add(new Pac(x,h,0,-1));
                break;
            default:
                break;
        }

    }
    private class Pac {
        private boolean stop = false;
        private float x,y,a=0,dir = 1,xDir = 0,yDir = 0;
        public  Pac(float x,float y,float xDir,float yDir) {
            this.x = x;
            this.y = y;
            this.xDir = xDir;
            this.yDir = yDir;
        }
        public void draw(Canvas canvas,Paint paint) {
            float deg = 90-90*xDir+90*Math.abs(yDir)-90*yDir;
            paint.setStyle(Paint.Style.FILL);
            paint.setColor(Color.parseColor("#99FF6F00"));
            canvas.save();
            canvas.translate(x,y);
            canvas.rotate(deg);
            canvas.drawArc(new RectF(-canvas.getWidth()/10,-canvas.getWidth()/10,canvas.getWidth()/10,canvas.getWidth()/10),a,360-2*a,true,paint);
            canvas.restore();
        }
        public void update() {
            a+=5*dir;
            if(a>=30 || a<=0) {
                dir*=-1;
            }
            x+=xDir*20;
            y+=yDir*20;
            stop = x<0 || x>w || y<0 || y>h;
        }
        public int hashCode() {
            return (int)x+(int)y+(int)a+(int)xDir+(int)yDir;
        }
    }

}
