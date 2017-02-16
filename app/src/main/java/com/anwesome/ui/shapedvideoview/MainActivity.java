package com.anwesome.ui.shapedvideoview;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Movie;
import android.graphics.drawable.shapes.Shape;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.WindowManager;
import android.widget.VideoView;

import com.anwesome.ui.shapedvideo.CircularShapedVideoView;
import com.anwesome.ui.shapedvideo.ShapedVideoView;
import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {
    private int request_code = 1;
    private Socket socket;
    private Thread imageThread;
    private boolean isRunning = true,started = false;
    private String filePath = Environment.getExternalStorageDirectory()+"/"+Environment.DIRECTORY_MOVIES+"/abp.mp4";
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
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(filePath)));
        startActivityForResult(intent,request_code);

    }
    public void getImageFromVideo() {

        imageThread = new Thread(new Runnable(){
            public void run() {
                try {
                    socket = IO.socket(AppConstants.MAIN_URL);
                    socket = socket.connect();
                }
                catch (Exception ex) {

                }
                while(isRunning) {
                    newVideoView.setDrawingCacheEnabled(true);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Bitmap bitmap = newVideoView.getDrawingCache();
                            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                            if(bitmap!=null) {
                                bitmap.compress(Bitmap.CompressFormat.PNG, 1, byteArrayOutputStream);
                                byte[] data = byteArrayOutputStream.toByteArray();
                                String base64String = Base64.encodeToString(data, Base64.DEFAULT);
                                if(socket!=null && socket.connected()) {
                                    socket.emit("newImage",base64String);
                                }
                            }
                        }
                    });

                    try {
                        Thread.sleep(1000);
                    } catch (Exception ex) {

                    }
                }
            }
        });
        imageThread.start();
    }
    public void onPause() {
        super.onPause();
        if(started) {
            stopThread();
            newVideoView.pause();
        }
    }
    public void stopThread() {
        if(isRunning) {
            isRunning = false;
            while(true) {
                try {
                    imageThread.join();
                    break;
                }
                catch (Exception ex) {

                }
            }
        }
    }
    public void onResume() {
        super.onResume();
        if(started) {
            if(!isRunning) {
                isRunning = true;
                imageThread.start();
            }
            newVideoView.start();
        }
    }
    public void onActivityResult(int request_code,int result_code,Intent data) {
        if(request_code == this.request_code && result_code == Activity.RESULT_OK) {
            Uri uri = data.getData();
            newVideoView.setVideoURI(uri);
            newVideoView.start();
            getImageFromVideo();
            started = true;
            newVideoView.setOnCompletetionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mediaPlayer) {
                    stopThread();
                    Thread thread = new Thread(new Runnable() {
                        @Override
                        public void run() {
                            if(socket!=null && socket.connected()) {
                                socket.emit("stopSavingImage");
                            }
                        }
                    });
                    thread.start();
                }
            });
        }
    }
}
