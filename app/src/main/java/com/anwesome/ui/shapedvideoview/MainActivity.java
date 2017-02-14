package com.anwesome.ui.shapedvideoview;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Movie;
import android.graphics.drawable.shapes.Shape;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.VideoView;

import com.anwesome.ui.shapedvideo.CircularShapedVideoView;
import com.anwesome.ui.shapedvideo.ShapedVideoView;

public class MainActivity extends AppCompatActivity {
    private int request_code = 1;
    private String filePath = Environment.getExternalStorageDirectory()+"/abp.mp4";
    private ShapedVideoView newVideoView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        newVideoView  = (ShapedVideoView) findViewById(R.id.video_view);
        Intent intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
        intent.putExtra("android.intent.extras.CAMERA_FACING",1);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, filePath);
        startActivityForResult(intent,request_code);
    }
    public void onPause() {
        super.onPause();
    }
    public void onResume() {
        super.onResume();
    }
    public void onActivityResult(int request_code,int result_code,Intent data) {
        if(request_code == this.request_code && result_code == Activity.RESULT_OK) {
            Uri uri = data.getData();
            newVideoView.setVideoURI(uri);
            newVideoView.start();

        }
    }


}
