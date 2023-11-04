package com.example.mmt;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;


public class PrincipalActivity extends AppCompatActivity{

    private TextView textViewdatosPublicante;
    private static final String VIDEO_SAMPLE = "pantera";

    Button btn_exit;
    FirebaseAuth mAuth;

    private RecyclerView recyclerView;

    private PublicacionEnPpalAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);
        mAuth = FirebaseAuth.getInstance();

        textViewdatosPublicante = findViewById(R.id.datosPublicante);
        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new PublicacionEnPpalAdapter(new ArrayList<>());
        recyclerView.setAdapter(adapter);

        FirebaseFirestore db = FirebaseFirestore.getInstance();

        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();

        String userId = user.getUid();

        DocumentReference docRef = db.collection("user").document(userId);

        docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if (documentSnapshot.exists()) {
                    // El documento existe, puedes obtener los datos del usuario
                    String name = documentSnapshot.getString("name");
                    // Muestra los datos del usuario en las TextView
                    textViewdatosPublicante.setText("Nombre: " + name);
                } else {
                    // El documento no existe, maneja este caso según tus necesidades
                }
            }
        });


        btn_exit = findViewById(R.id.btn_close);
        btn_exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAuth.signOut();
                finish();
                startActivity(new Intent(PrincipalActivity.this, LoginActivity.class));
            }
        });

        getPublicacionesFromFirebase();

    }

    private void getPublicacionesFromFirebase() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference postCollection = db.collection("publicacion");


        postCollection
                .get().addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        List<Publicacion> publicacionesList = new ArrayList<>();

                        for (DocumentSnapshot document : task.getResult()) {
                            Publicacion publicacion = document.toObject(Publicacion.class);
                            publicacion.setId(document.getId());
                            if (publicacion != null) {
                                publicacionesList.add(publicacion);
                            }
                        }

                        // Actualiza el adaptador con la lista de publicaciones
                        adapter.setData(publicacionesList);
                    } else {
                        // Maneja errores aquí
                    }
                });
    }


    public void ir_a_publicar(View view) {
        Intent intent = new Intent(this, PosteoActivity.class);
        startActivity(intent);
    }


    public void perfil(View view) {
        Intent intent = new Intent(this, PerfilActivity.class);
        startActivity(intent);
    }


    public void lauchReproducirvideo(View view) {
        Intent intent = new Intent(this, ReproductorDeVideo.class);
        startActivity(intent);
    }

    public void launchPublicacionSimple(View view) {
        Intent intent = new Intent(this, PublicacionSimpleLista.class);
        startActivity(intent);
    }

    public void launchPublicacionAudio(View view) {
        Intent intent = new Intent(this, PublicacionAudio.class);
        startActivity(intent);
    }

    public void launchGestionMensajeria(View view) {
        Intent intent = new Intent(this, MensajeriaPrivada.class);
        startActivity(intent);
    }

}