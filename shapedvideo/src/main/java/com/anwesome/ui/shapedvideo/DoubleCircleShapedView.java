package com.anwesome.ui.shapedvideo;

import android.app.Activity;
import android.content.Context;
import android.graphics.Path;
import android.graphics.Point;
import android.hardware.display.DisplayManager;
import android.net.Uri;
import android.util.AttributeSet;
import android.view.Display;
import android.view.ViewGroup;

/**
 * Created by anweshmishra on 23/02/17.
 */
public class DoubleCircleShapedView extends ShapedVideoView {
    private Activity activity;
    private int time = 0;
    private CircularShapedVideoView miniCircularShapedVideoView;
    public DoubleCircleShapedView(Context context, AttributeSet attrs) {
        super(context,attrs);
        miniCircularShapedVideoView = new CircularShapedVideoView(context);
        activity =(Activity)context;
    }
    private void addMiniCirularVideo(int w,int h) {
        int r = Math.min(w,h)/2;
        miniCircularShapedVideoView.setX(w/2-w/4);
        miniCircularShapedVideoView.setY(h/2-w/4);
        this.setAlpha(0.5f);
        miniCircularShapedVideoView.bringToFront();
        miniCircularShapedVideoView.setVolume(0);
        activity.addContentView(miniCircularShapedVideoView, new ViewGroup.LayoutParams(r, r));
    }
    public void start() {
        super.start();
        miniCircularShapedVideoView.start();
    }
    public void setVideoURI(Uri uri) {
        super.setVideoURI(uri);
        miniCircularShapedVideoView.setVideoURI(uri);
    }
    public DoubleCircleShapedView(Context context) {
        super(context);
        miniCircularShapedVideoView = new CircularShapedVideoView(context);

        activity =(Activity)context;
    }
    public Path definePath(Path path,int w,int h) {
        if(time == 0) {
            addMiniCirularVideo(w,h);
        }
        time++;
        return null;
    }
}
