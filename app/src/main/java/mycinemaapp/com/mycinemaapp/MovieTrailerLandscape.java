package mycinemaapp.com.mycinemaapp;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Bundle;
import android.widget.MediaController;
import android.widget.VideoView;

/**
 * Created by kristian on 15-3-18.
 */
public class MovieTrailerLandscape extends Activity {

    private VideoView mVideoView;
    private int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.movie_trailer_landscape);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        String url = getIntent().getStringExtra("url");
        position = getIntent().getIntExtra("POSITION", 0);
        mVideoView = (VideoView) findViewById(R.id.movie_trailer);
        if (url != "" && url != null) {
            mVideoView.setVideoURI(Uri.parse(url));
        }
        mVideoView.start();
        mVideoView.setMediaController(new MediaController(this));
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent();
        intent.putExtra("POSITION", position);
        setResult(1, intent);
        super.onBackPressed();
    }
}
