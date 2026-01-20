//package com.example.myapplication;
//import android.net.Uri;
//import android.os.Bundle;
//
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.media3.common.MediaItem;
//import androidx.media3.exoplayer.ExoPlayer;
//import androidx.media3.ui.PlayerView;
//
//public class VideoPlayerActivity extends AppCompatActivity {
//
//    private ExoPlayer player;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_video_player);
//
//        String url = getIntent().getStringExtra(VideosActivity.EXTRA_URL);
//        String title = getIntent().getStringExtra(VideosActivity.EXTRA_TITLE);
//
//        if (getSupportActionBar() != null) {
//            getSupportActionBar().setTitle(title != null ? title : "Odtwarzacz");
//            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        }
//
//        PlayerView playerView = findViewById(R.id.playerView);
//
//        player = new ExoPlayer.Builder(this).build();
//        playerView.setPlayer(player);
//
//        if (url != null) {
//            MediaItem mediaItem = MediaItem.fromUri(Uri.parse(url));
//            player.setMediaItem(mediaItem);
//            player.prepare();
//            player.play();
//        }
//    }
//
//    @Override
//    protected void onStop() {
//        super.onStop();
//        if (player != null) {
//            player.release();
//            player = null;
//        }
//    }
//
//    @Override
//    public boolean onSupportNavigateUp() {
//        finish();
//        return true;
//    }
//}