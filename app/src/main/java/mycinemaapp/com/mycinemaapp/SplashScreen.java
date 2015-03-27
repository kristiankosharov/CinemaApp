package mycinemaapp.com.mycinemaapp;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.widget.VideoView;

/**
 * Created by kristian on 15-3-27.
 */
public class SplashScreen extends Activity {

    private VideoView video;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);

        video = (VideoView) findViewById(R.id.splash);
        video.setVideoPath("android.resource://" + getPackageName() + "/" + R.drawable.splash);
        video.start();
        video.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                Intent i = new Intent(SplashScreen.this, MainActivity.class);
                startActivity(i);
            }
        });
    }
}
