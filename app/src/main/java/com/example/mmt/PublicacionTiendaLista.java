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


public class PublicacionTiendaLista extends AppCompatActivity {
    private RecyclerView recyclerView;
    private ClasificadoAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_publicacion_tienda_lista);

        Log.d(this.getClass().getSimpleName(), "onCreate pdc01");

        recyclerView = findViewById(R.id.recyclerViewClasificados);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new ClasificadoAdapter(new ArrayList<>());
        recyclerView.setAdapter(adapter);
        Log.d(this.getClass().getSimpleName(), "onCreate pdc02");
        getClasificadosFromFirebase();
    }
    private void getClasificadosFromFirebase() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference postCollection = db.collection("clasificado");
        Log.d(this.getClass().getSimpleName(), "getClasificadosFromFirebase pdc01");

        // Obtén el ID del usuario logueado (asumiendo que el usuario ya está autenticado)
        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        Log.d(this.getClass().getSimpleName(), "getClasificadosFromFirebase pdc02");

        postCollection
                .whereEqualTo("id_user", userId)
                .get().addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        List<Clasificado> clasificadosList = new ArrayList<>();

                        for (DocumentSnapshot document : task.getResult()) {
                            Log.d(this.getClass().getSimpleName(), "foreando pdc01");

                            Clasificado clasificado = document.toObject(Clasificado.class);
                            Log.d(this.getClass().getSimpleName(), "foreando pdc02");
                            clasificado.setId(document.getId());
                            if (clasificado != null) {
                                Log.d(this.getClass().getSimpleName(), "foreando pdc03");

                                clasificadosList.add(clasificado);
                                Log.d(this.getClass().getSimpleName(), "foreando pdc04");

                            }
                        }

                        // Actualiza el adaptador con la lista de publicaciones
                        adapter.setData(clasificadosList);
                    } else {
                        // Maneja errores aquí
                    }
                });
    }

    public void launchNuevoClasificado(View view) {
        Intent intent = new Intent(this, PublicacionTienda.class);
        startActivity(intent);
    }

}


