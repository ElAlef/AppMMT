package com.example.mmt;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class PublicacionTienda extends AppCompatActivity {

    Button crear_posteo;
    EditText title,location,content,productPrice;
    LinearLayout linearLayout_image_btn;
    private FirebaseFirestore mfirestore;
    private FirebaseAuth mAuth;

    StorageReference storageReference;
    String storage_path = "posteo/*";

    private static final int COD_SEL_STORAGE = 200;
    private static final int COD_SEL_VIDEO = 300;

    String video;
    String idd;
    private Uri video_url;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_publicacion_tienda);
        mfirestore = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        storageReference = FirebaseStorage.getInstance().getReference();
        DocumentReference id = mfirestore.collection("clasificado").document();


        title = findViewById(R.id.title);
        productPrice = findViewById(R.id.price);
        location = findViewById(R.id.location);
        content = findViewById(R.id.content);
        crear_posteo = findViewById(R.id.btn_publicar);



        crear_posteo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String tituloPosteo = title.getText().toString().trim();
                String descrpcionPosteo = content.getText().toString().trim();
                String price = productPrice.getText().toString().trim();
                String locacion = location.getText().toString().trim();
                String idUser = mAuth.getCurrentUser().getUid();
                if (tituloPosteo.isEmpty() && descrpcionPosteo.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Ingresar los datos", Toast.LENGTH_SHORT).show();
                } else {

                    uploadVideo();

                }
            }
        });
    }

    private void uploadVideo() {
        Intent i = new Intent(Intent.ACTION_PICK);
        i.setType("video/*");

        startActivityForResult(i, COD_SEL_VIDEO);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(resultCode == RESULT_OK){
            if (requestCode == COD_SEL_VIDEO){
                assert data != null;
                Uri video_url = data.getData();
                subirVideo(video_url);
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
    private void subirVideo(Uri video_url) {

        DocumentReference id = mfirestore.collection("clasificado").document();
        String rute_storage_video = storage_path + "" + video + "" + mAuth.getUid() + "" + id.getId() ;
        StorageReference reference = storageReference.child(rute_storage_video);

        reference.putFile(video_url).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();

                uriTask.addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        String download_uri = uri.toString();
                        crearPosteo(download_uri);
                        Toast.makeText(PublicacionTienda.this, "Video actualizado", Toast.LENGTH_SHORT).show();
                    }
                });

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(PublicacionTienda.this, "Error al cargar foto", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void crearPosteo(String video_url) {
        String tituloPosteo = title.getText().toString().trim();
        String descrpcionPosteo = content.getText().toString().trim();
        String price = productPrice.getText().toString().trim();
        String locacion = location.getText().toString().trim();
        String idUser = mAuth.getCurrentUser().getUid();
        DocumentReference id = mfirestore.collection("clasificado").document();



        Map<String, Object> map = new HashMap<>();
        map.put("id_user", idUser);
        map.put("id", id.getId());
        map.put("title", tituloPosteo);
        map.put("price", price);
        map.put("location", locacion);
        map.put("content", descrpcionPosteo);
        map.put("video", video_url);

        mfirestore.collection("clasificado").document(id.getId()).set(map).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {

                Toast.makeText(getApplicationContext(), "Creado exitosamente", Toast.LENGTH_SHORT).show();
                finish();

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getApplicationContext(), "Error al ingresar", Toast.LENGTH_SHORT).show();
            }
        });

    }

}