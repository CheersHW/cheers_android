package us.buddman.cheers.utils;

import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;

/**
 * Created by Chad Park on 2018-02-11.
 */


public class PlayAudioManager {
    private static MediaPlayer mediaPlayer;
    public static boolean isPlaying = false;

    public static void playAudio(final Context context, final String url) throws Exception {
        if (mediaPlayer == null) {
            mediaPlayer = MediaPlayer.create(context, Uri.parse(url));
        }
        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                killMediaPlayer();
            }
        });
        mediaPlayer.start();
        isPlaying = true;
    }

    public static void killMediaPlayer() {
        if (mediaPlayer != null) {
            try {
                mediaPlayer.reset();
                mediaPlayer.release();
                mediaPlayer = null;
                isPlaying = false;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
