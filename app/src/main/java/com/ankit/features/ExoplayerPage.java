package com.ankit.features;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.extractor.ExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;

public class ExoplayerPage extends AppCompatActivity {

    SimpleExoPlayerView simpleExoPlayerView;
    SimpleExoPlayer exoPlayer;
    AppCompatButton optionButton;
    String videoUrl = "https://www.rmp-streaming.com/media/big-buck-bunny-360p.mp4";
    String audioUrl = "https://file-examples-com.github.io/uploads/2017/11/file_example_MP3_5MG.mp3";
    boolean playVideo = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme();
        setContentView(R.layout.activity_exoplayer_page);
        simpleExoPlayerView = findViewById(R.id.exoPlayerVIew);
        optionButton = findViewById(R.id.btn_playInstead);
        optionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                playVideo = !playVideo;
                exoPlayer.release();
                if(playVideo){
                    initExoPlayer(videoUrl);
                    optionButton.setText("Play Audio Instead");
                    optionButton.setBackgroundResource(R.color.navy_blue);
                    //simpleExoPlayerView.getLayoutParams().height = ViewGroup.LayoutParams.WRAP_CONTENT;
                    Toast.makeText(ExoplayerPage.this,"Playing video now!",Toast.LENGTH_SHORT).show();
                }
                else {
                    initExoPlayer(audioUrl);
                    optionButton.setText("Play Video Instead");
                    optionButton.setBackgroundResource(R.color.brown);
                    //simpleExoPlayerView.getLayoutParams().height = 500;
                    Toast.makeText(ExoplayerPage.this,"Playing audio now!",Toast.LENGTH_SHORT).show();
                }
            }
        });
        initExoPlayer(videoUrl);
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(exoPlayer != null){
            exoPlayer.setPlayWhenReady(false);
        }
    }

    private void initExoPlayer(String mediaUrl){
        BandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();
        TrackSelector trackSelector = new DefaultTrackSelector(new AdaptiveTrackSelection.Factory(bandwidthMeter));
        exoPlayer = ExoPlayerFactory.newSimpleInstance(this,trackSelector);
        simpleExoPlayerView.setPlayer(exoPlayer);

        Uri uri = Uri.parse(mediaUrl);
        DefaultHttpDataSourceFactory dataSourceFactory = new DefaultHttpDataSourceFactory("exoplayer_video");
        ExtractorsFactory extractorsFactory = new DefaultExtractorsFactory();
        MediaSource mediaSource = new ExtractorMediaSource(uri,dataSourceFactory,extractorsFactory,null,null);
        exoPlayer.prepare(mediaSource);
        exoPlayer.setPlayWhenReady(true);
    }

    void setTheme(){
        SharedPreferences sharedPreferences = getSharedPreferences("Theme",MODE_PRIVATE);
        int themeNo = sharedPreferences.getInt("theme_no",4);
        switch (themeNo){
            case 1:
                getTheme().applyStyle(R.style.OverlayThemeLime,true);
                break;
            case 2:
                getTheme().applyStyle(R.style.OverlayThemeRed,true);
                break;
            case 3:
                getTheme().applyStyle(R.style.OverlayThemeGreen,true);
                break;
            case 4:
                getTheme().applyStyle(R.style.OverlayThemeBlue,true);
                break;
        }
    }
}