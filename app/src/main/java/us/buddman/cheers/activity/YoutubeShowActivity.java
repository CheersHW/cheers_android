package us.buddman.cheers.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

import us.buddman.cheers.utils.DeveloperService;
import us.buddman.cheers.R;
import us.buddman.cheers.utils.YouTubeFailureRecoveryActivity;


public class YoutubeShowActivity extends YouTubeFailureRecoveryActivity {

    Intent intent;
    YouTubePlayer player;
    String videoId;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_excercise_video_show);

        intent = getIntent();
        videoId = intent.getStringExtra("videoId");
        YouTubePlayerView youTubeView = (YouTubePlayerView) findViewById(R.id.youtube_view);
        youTubeView.initialize(DeveloperService.ANDROID_DEVELOPER_KEY, this);
        findViewById(R.id.comment).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), CommentActivity.class));
            }
        });
    }

    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer player, boolean wasRestored) {
        this.player = player;
        player.setFullscreen(true);
        if (!wasRestored) {
            player.cueVideo(videoId);
        }
    }

    @Override
    protected YouTubePlayer.Provider getYouTubePlayerProvider() {
        return (YouTubePlayerView) findViewById(R.id.youtube_view);
    }

    public void onResume() {
        super.onResume();
        if (player != null)
            player.setFullscreen(true);
    }
}
