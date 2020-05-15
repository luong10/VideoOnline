package com.example.mywatch;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

import java.util.ArrayList;

public class PlayVideoActivity extends YouTubeBaseActivity implements YouTubePlayer.OnInitializedListener {
    YouTubePlayerView youTubePlayerView;
    TextView teN,ngaY,kenH;
    String id = "";
    int RE =123;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_video);
        teN = (TextView)findViewById(R.id.nameVideo);
        ngaY = (TextView)findViewById(R.id.ngay);
        kenH = (TextView)findViewById(R.id.tenKenh);

        youTubePlayerView = (YouTubePlayerView) findViewById(R.id.myYouTube);
        Intent intent = getIntent();
        id = intent.getStringExtra("idVideoYouTube");
        teN.setText(intent.getStringExtra("nameVideoYouTube"));
        ngaY.setText(intent.getStringExtra("ngayVideoYouTube"));
        kenH.setText(intent.getStringExtra("kenhVideoYouTube"));

        youTubePlayerView.initialize(MainActivity.API_KEY,this);
    }


    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
        youTubePlayer.loadVideo(id);
    }

    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
        if (youTubeInitializationResult.isUserRecoverableError()) {
            youTubeInitializationResult.getErrorDialog(PlayVideoActivity.this,RE);
        }else {
            Toast.makeText(this, "Error!!", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == RE){
            youTubePlayerView.initialize(MainActivity.API_KEY,PlayVideoActivity.this);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
