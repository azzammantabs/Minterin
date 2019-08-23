package com.example.minterin;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.MediaController;
import android.widget.VideoView;

public class VideoActivity extends AppCompatActivity {

    VideoView vv_videomateri;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ui_video);

        vv_videomateri = findViewById(R.id.video_materi);
        MediaController mediaC = new MediaController(this);

        Uri lokasivideo = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.river);
        vv_videomateri.setVideoURI(lokasivideo);
        vv_videomateri.setMediaController(mediaC);
        mediaC.setAnchorView(vv_videomateri);
        mediaC.setAnchorView(vv_videomateri);
        vv_videomateri.start();


    }
}
