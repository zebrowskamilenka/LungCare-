package com.example.myapplication;

import android.os.Bundle;
import androidx.annotation.OptIn;
import androidx.appcompat.app.AppCompatActivity;
import androidx.media3.common.MediaItem;
import androidx.media3.common.util.UnstableApi;
import androidx.media3.exoplayer.ExoPlayer;
import androidx.media3.ui.PlayerView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

@OptIn(markerClass = UnstableApi.class)
public class VideoPlayerActivity extends AppCompatActivity {

    public static final String EXTRA_RAW_RES_ID = "raw_res_id";
    private ExoPlayer player;
    private PlayerView playerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_player);

        playerView = findViewById(R.id.playerView);
        FloatingActionButton btnBack = findViewById(R.id.btnBack);

        if (btnBack != null) {
            btnBack.setOnClickListener(v -> finish());
        }

        int rawResId = getIntent().getIntExtra(EXTRA_RAW_RES_ID, 0);

        if (rawResId != 0) {
            initializePlayer(rawResId);
        } else {
            finish();
        }
    }

    private void initializePlayer(int resId) {
        player = new ExoPlayer.Builder(this).build();
        playerView.setPlayer(player);

        // Tworzenie ścieżki do pliku w res/raw
        String uriPath = "android.resource://" + getPackageName() + "/" + resId;
        MediaItem mediaItem = MediaItem.fromUri(uriPath);

        player.setMediaItem(mediaItem);
        player.prepare();
        player.play(); // Automatyczne odtwarzanie po wejściu
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (player != null) {
            player.release();
            player = null;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (player != null) {
            player.release();
        }
    }
}
