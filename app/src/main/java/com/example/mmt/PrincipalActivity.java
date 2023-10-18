package com.example.mmt;

import androidx.appcompat.app.AppCompatActivity;
<<<<<<< HEAD
import android.os.Bundle;
public class PrincipalActivity extends AppCompatActivity {
=======
import androidx.appcompat.widget.SearchView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.Configuration;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.google.firebase.auth.FirebaseAuth;


public class PrincipalActivity extends AppCompatActivity{
    private static final String VIDEO_SAMPLE = "pantera";
    private VideoView mVideoView;
    Button btn_exit;
    FirebaseAuth mAuth;


    private Uri getMedia(String mediaName) {
        return Uri.parse("android.resource://" + getPackageName() +
                "/raw/" + mediaName);
    }
    private int mCurrentPosition = 0;
    private static final String PLAYBACK_TIME = "play_time";

>>>>>>> 3ef642ff09517ba3651c7aa7c4125829a4964c3b

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);
        mAuth = FirebaseAuth.getInstance();
        mVideoView = findViewById(R.id.videoview);
        if (savedInstanceState != null) {
            mCurrentPosition = savedInstanceState.getInt(PLAYBACK_TIME);
        }
        MediaController controlador  = new MediaController(this) ;
        controlador.setMediaPlayer(mVideoView);
        mVideoView.setMediaController(controlador);

        btn_exit = findViewById(R.id.btn_close);
        btn_exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAuth.signOut();
                finish();
                startActivity(new Intent(PrincipalActivity.this, LoginActivity.class));
            }
        });



    }

    private void initializePlayer() {
//
        Uri videoUri = getMedia(VIDEO_SAMPLE);
        mVideoView.setVideoURI(videoUri);
//        if (mCurrentPosition > 0) {
//            mVideoView.seekTo(mCurrentPosition);
//        } else {
//// Saltar a 1 muestra el primer cuadro del video.
//            mVideoView.seekTo(1);
//        }
//
       mVideoView.start();
//        mVideoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
//            @Override
//            public void onCompletion(MediaPlayer mediaPlayer) {
//                Toast.makeText(PrincipalActivity.this, "Reproducción Completa",
//                        Toast.LENGTH_SHORT).show();
//                mVideoView.seekTo(1);
//            }
//        });
//
//
   }
   @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(PLAYBACK_TIME, mVideoView.getCurrentPosition());
    }


    private void releasePlayer() {
        mVideoView.stopPlayback();
    }
    @Override
    protected void onStart() {
        super.onStart();
        initializePlayer();
   }

    @Override
    protected void onStop() {
        super.onStop();
        releasePlayer();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
            mVideoView.pause();
        }
    }

}