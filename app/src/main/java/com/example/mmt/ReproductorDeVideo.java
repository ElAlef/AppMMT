package com.example.mmt;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.FirebaseFirestore;

public class ReproductorDeVideo extends AppCompatActivity {


   // private static final String VIDEO_SAMPLE = "pantera";
    private VideoView mVideoView;
    private FirebaseFirestore db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reproductor_de_video);

        mVideoView = findViewById(R.id.videoview);
        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("VIDEO_URL")) {
            String videoUrl = intent.getStringExtra("VIDEO_URL");

            MediaController mediaController = new MediaController(this);
            mediaController.setAnchorView(mVideoView);
            mVideoView.setMediaController(mediaController);

            // Reproduce el video desde la URL en el VideoView
            Uri videoUri = Uri.parse(videoUrl);
            mVideoView.setVideoURI(videoUri);
            mVideoView.start();
        }
    }
}
//        db = FirebaseFirestore.getInstance();
//
//        db.collection("publicacion")
//                .document("id_user") // Reemplaza con el ID del documento que deseas obtener
//                .get()
//                .addOnSuccessListener(documentSnapshot -> {
//                    if (documentSnapshot.exists()) {
//                        String videoUrl = documentSnapshot.getString("video"); // Campo donde se almacena la URL del video en Firebase Storage
//                        reproducirVideo(videoUrl);
//                    } else {
//                        Toast.makeText(this, "Documento no encontrado", Toast.LENGTH_SHORT).show();
//                    }
//                })
//                .addOnFailureListener(e -> {
//                    Toast.makeText(this, "Error al obtener datos: " + e.getMessage(), Toast.LENGTH_SHORT).show();
//                });


//        if (savedInstanceState != null) {
//            mCurrentPosition = savedInstanceState.getInt(PLAYBACK_TIME);
//        }
//        MediaController controlador  = new MediaController(this) ;
//        controlador.setMediaPlayer(mVideoView);
//        mVideoView.setMediaController(controlador);


   // private void reproducirVideo(String videoUrl) {
    //    Uri videoUri = getMedia(videoUrl);
   // }

//    private void initializePlayer() {
//
//        mVideoView.start();
//
//    }
//    @Override
//    protected void onSaveInstanceState(@NonNull Bundle outState) {
//        super.onSaveInstanceState(outState);
//        outState.putInt(PLAYBACK_TIME, mVideoView.getCurrentPosition());
//    }
//
//    private void releasePlayer() {
//        mVideoView.stopPlayback();
//    }
//    @Override
//    protected void onStart() {
//        super.onStart();
//        initializePlayer();
//    }
//    @Override
//    protected void onStop() {
//        super.onStop();
//        releasePlayer();
//    }
//
//    @Override
//    protected void onPause() {
//        super.onPause();
//        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
//            mVideoView.pause();
//        }
//    }
//    private Uri getMedia(String mediaName) {
//        return Uri.parse("android.resource://" + getPackageName() +
//                "/raw/" + mediaName);
//    }
//    private int mCurrentPosition = 0;
//    private static final String PLAYBACK_TIME = "play_time";

