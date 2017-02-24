package com.anwesome.ui.shapedvideo;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
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
        setDrawingCacheEnabled(true);
        this.mediaPlayer = new MediaPlayer();
        holder = getHolder();
        holder.addCallback(this);
        if(shouldDraw()) {
            setWillNotDraw(false);
        }
    }
    protected boolean shouldDraw() {
        return false;
    }
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        if(this.mediaPlayer!=null) {
            mediaPlayer.setDisplay(holder);
            setDrawingCacheEnabled(true);
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
        path = definePath(path,w,h);
        if(path!=null) {
            canvas.clipPath(path);
        }
        super.dispatchDraw(canvas);
    }
    public void pause() {
        mediaPlayer.stop();
    }
    public void setOnCompletetionListener(MediaPlayer.OnCompletionListener onCompleteListener) {
        mediaPlayer.setOnCompletionListener(onCompleteListener);
    }
    public void setVolume(float volume) {
        mediaPlayer.setVolume(0,volume);
    }
    public void onDraw(Canvas canvas){
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setStyle(Paint.Style.STROKE);
        drawElements(canvas,paint);
    }
    protected void drawElements(Canvas canvas, Paint paint) {

    }
    public boolean onTouchEvent(MotionEvent event) {
        if(shouldDraw() && event.getAction() == MotionEvent.ACTION_DOWN) {
            handleTap(event.getX(),event.getY());
        }
        return true;
    }
    protected void handleTap(float x,float y) {

    }
}
