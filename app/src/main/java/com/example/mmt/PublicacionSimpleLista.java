package com.example.mmt;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;


public class PublicacionSimpleLista extends AppCompatActivity {
    private RecyclerView recyclerView;
    private PublicacionAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_publicacion_simple_lista);

        Log.d(this.getClass().getSimpleName(), "onCreate pdc01");

        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new PublicacionAdapter(getPublicacionesFromFirebase());
        recyclerView.setAdapter(adapter);
        Log.d(this.getClass().getSimpleName(), "onCreate pdc02");
    }

    // Método para obtener los datos de Firebase y devolver una lista de Publicacion
    private List<Publicacion> getPublicacionesFromFirebase() {
        Log.d(this.getClass().getSimpleName(), "getPublicacionesFromFirebase pdc01");

        List<Publicacion> publicacionesList = new ArrayList<>();

        // Obtén la instancia de Firebase Firestore
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        // Obtén el ID del usuario logueado (asumiendo que el usuario ya está autenticado)
        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        Log.d(this.getClass().getSimpleName(), "getPublicacionesFromFirebase userId: "+userId);


        // Obtén una referencia a la colección "Post" en Firestore
        CollectionReference postCollection = db.collection("post");

        // Realiza una consulta para obtener publicaciones basadas en el ID del usuario
        postCollection.whereEqualTo("publisher", userId)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Log.d(this.getClass().getSimpleName(), "getPublicacionesFromFirebase taskIsSuccessful");

                        for (DocumentSnapshot document : task.getResult()) {
                            // Mapea los datos del documento a un objeto Publicacion
                            Log.d(this.getClass().getSimpleName(), "getPublicacionesFromFirebase foreando");

                            Publicacion publicacion = document.toObject(Publicacion.class);
                            publicacion.setId(document.getId());
                            if (publicacion != null) {
                                Log.d(this.getClass().getSimpleName(), "getPublicacionesFromFirebase pub not null");
                                publicacionesList.add(publicacion);
                            }
                        }
                        // Notifica al adaptador que los datos han cambiado
                        adapter.notifyDataSetChanged();
                    } else {
                        Log.d(this.getClass().getSimpleName(), "getPublicacionesFromFirebase Algo salio mal");

                        // Maneja errores aquí, por ejemplo, loguea o muestra un mensaje al usuario
                    }
                });

        return publicacionesList;
    }

    public void launchPublicacionSimple(View view) {
        Intent intent = new Intent(this, PublicacionSimple.class);
        startActivity(intent);
    }

}
