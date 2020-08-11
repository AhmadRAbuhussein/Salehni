package com.salehni.salehni.view.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.MediaController;
import android.widget.VideoView;

import androidx.appcompat.app.AppCompatActivity;

import com.salehni.salehni.R;
import com.salehni.salehni.util.Constants;

public class VideoActivity extends AppCompatActivity {

    String selectedVideoPath;

    VideoView vid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.play_video_popup);

        vid = findViewById(R.id.videoView);

        getExtra();

        playVideo();


    }

    private void getExtra() {
        Intent intent = getIntent();
        selectedVideoPath = intent.getStringExtra(Constants.selectedVideoPath);
    }

    public void playVideo() {
        MediaController m = new MediaController(this);
        vid.setMediaController(m);

        Uri u = Uri.parse(selectedVideoPath);

        vid.setVideoURI(u);

        vid.start();

    }


}
