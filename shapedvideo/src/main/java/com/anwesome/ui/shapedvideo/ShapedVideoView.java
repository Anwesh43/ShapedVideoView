package com.anwesome.ui.shapedvideo;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Path;
import android.media.MediaPlayer;
import android.net.Uri;
import android.util.AttributeSet;
import android.view.*;

/**
 * Created by anweshmishra on 06/02/17.
 */
public class ShapedVideoView extends SurfaceView implements SurfaceHolder.Callback {
    private SurfaceHolder holder;
    private MediaPlayer mediaPlayer;
    private Context context;
    private boolean loaded = false;
    public ShapedVideoView(Context context, AttributeSet attributeSet) {
        super(context,attributeSet);
        this.context = context;
        this.mediaPlayer = new MediaPlayer();
        holder = getHolder();
        holder.addCallback(this);
        //setWillNotDraw(false);
    }
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        if(this.mediaPlayer!=null) {
            mediaPlayer.setDisplay(holder);
        }
    }
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {

    }
    public void surfaceChanged(SurfaceHolder surfaceHolder,int a,int b,int c) {

    }
    public void setVideoURI(Uri uri) {
        try {
            mediaPlayer.setDataSource(context, uri);
            mediaPlayer.prepare();
            loaded = true;
        }
        catch (Exception ex) {

        }
    }
    public void start() {
        if(loaded) {
            mediaPlayer.setLooping(true);
            mediaPlayer.start();

        }
    }
    public ShapedVideoView(Context context) {
        super(context);
        this.context = context;
        getHolder().addCallback(this);
        mediaPlayer = new MediaPlayer();
    }
    protected Path definePath(Path path,int w,int h){
        return null;
    }
    public void dispatchDraw(Canvas canvas) {
        int w = canvas.getWidth(),h = canvas.getHeight();
        Path path = new Path();
        path = definePath(new Path(),w,h);
        if(path!=null) {
            canvas.clipPath(path);
        }
        super.dispatchDraw(canvas);
    }
}
