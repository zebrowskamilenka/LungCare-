package com.example.myapplication;

import android.net.Uri;
import android.os.Bundle;
import android.widget.MediaController;
import android.widget.VideoView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class VideoPlayerActivity extends AppCompatActivity {

    public static final String EXTRA_RAW_RES_ID = "raw_res_id";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_player);

        int rawResId = getIntent().getIntExtra(EXTRA_RAW_RES_ID, 0);
        if (rawResId == 0) {
            finish();
            return;
        }

        VideoView videoView = findViewById(R.id.videoView);

        // controls (play/pause/seek)
        MediaController controller = new MediaController(this);
        controller.setAnchorView(videoView);
        videoView.setMediaController(controller);

        Uri uri = Uri.parse("android.resource://" + getPackageName() + "/" + rawResId);
        videoView.setVideoURI(uri);
        videoView.requestFocus();
        videoView.start();
    }
}