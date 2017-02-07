#ShapedVideoView

##Render your videos in amazing shapes

##CircularShapedVideoView - Render your videos in a circular frame

###Usage

#### In layout.xml file. Add the view

```
<com.anwesome.ui.shapedvideo.CircularShapedVideoView
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:id="@+id/video_view"/>
```
##EllipseShapedVideoView - Render your videos in a elliptical frame

###Usage

#### In layout.xml file. Add the view

```
<com.anwesome.ui.shapedvideo.EllipseShapedVideoView
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:id="@+id/video_view"/>
```

##Using any ShapedVideoView in android activity.
###In Activity

```
    ShapedVideoView newVideoView  = (ShapedVideoView) findViewById(R.id.video_view);
    newVideoView.setVideoURI(uri);
    newVideoView.start();
```
