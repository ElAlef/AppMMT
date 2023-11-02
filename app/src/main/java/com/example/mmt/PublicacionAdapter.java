package com.example.mmt;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

public class PublicacionAdapter extends RecyclerView.Adapter<PublicacionAdapter.ViewHolder> {

    FirebaseFirestore mFirestore;
    FirebaseAuth mAuth;

    private List<Publicacion> publicacionesList;

    public PublicacionAdapter(List<Publicacion> publicacionesList) {
        this.publicacionesList = publicacionesList;

        mFirestore = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_post, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Publicacion publicacion = publicacionesList.get(position);

        holder.titleTextView.setText(publicacion.getTitle());
        holder.genereTextView.setText(publicacion.getGenre());
        holder.locationTextView.setText(publicacion.getLocation());
        holder.contentTextView.setText(publicacion.getContent());
        holder.creationDateTextView.setText(publicacion.getCreationDate().toString());

        final String publicacionId = publicacion.getId();
        holder.btnDelete.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                eliminarPublicacion(publicacionId);
            }
        });


    }

    private void eliminarPublicacion(String publicacionId) {
        Log.w(this.getClass().getSimpleName(), "Intentando eliminar publicacion: "+String.valueOf(publicacionId) );

        DocumentReference docRef = mFirestore.collection("post").document(publicacionId);
                docRef.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Log.d(this.getClass().getSimpleName(), "Eliminado con exito post " + publicacionId);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(this.getClass().getSimpleName(), "Error al intentar eliminar el post", e);
                    }
                });



    }
    public int getItemCount() {
        return publicacionesList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView titleTextView;
        TextView genereTextView;
        TextView locationTextView;
        TextView contentTextView;
        TextView creationDateTextView;
        Button btnDelete;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            titleTextView = itemView.findViewById(R.id.titleTextView);
            genereTextView = itemView.findViewById(R.id.genereTextView);
            locationTextView = itemView.findViewById(R.id.locationTextView);
            contentTextView = itemView.findViewById(R.id.contentTextView);
            creationDateTextView = itemView.findViewById(R.id.creationDateTextView);
            btnDelete = itemView.findViewById(R.id.btnDelete);
        }
    }
}
