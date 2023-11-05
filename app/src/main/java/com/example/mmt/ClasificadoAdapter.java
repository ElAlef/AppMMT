package com.example.mmt;

import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
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

public class ClasificadoAdapter extends RecyclerView.Adapter<ClasificadoAdapter.ViewHolder> {

    FirebaseFirestore mFirestore;
    FirebaseAuth mAuth;

    private List<Clasificado> clasificadosList;

    public ClasificadoAdapter(List<Clasificado> clasificadosList) {
        this.clasificadosList = clasificadosList;

        mFirestore = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.clasificado_post, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Clasificado clasificado = clasificadosList.get(position);

        holder.titleTextView.setText(clasificado.getTitle());
        holder.priceTextView.setText(clasificado.getPrice());
        holder.locationTextView.setText(clasificado.getLocation());
        holder.contentTextView.setText(clasificado.getContent());
        String videoUrl = clasificado.getVideo();
        holder.video_post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (!TextUtils.isEmpty(videoUrl)) {
                    Intent intent = new Intent(v.getContext(), ReproductorDeVideo.class);
                    intent.putExtra("VIDEO_URL", videoUrl); // Pasa la URL del video como un extra en el Intent
                    v.getContext().startActivity(intent);
                }

            }
        });

        final String clasificadoId = clasificado.getId();
        holder.btnDelete.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                eliminarClasificado(clasificadoId);
            }
        });


    }

    private void eliminarClasificado(String clasificadoId) {
        Log.w(this.getClass().getSimpleName(), "Intentando eliminar clasificado: "+String.valueOf(clasificadoId) );

        DocumentReference docRef = mFirestore.collection("clasificado").document(clasificadoId);
        docRef.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Log.d(this.getClass().getSimpleName(), "Eliminado con exito post " + clasificadoId);
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
        return clasificadosList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView titleTextView;
        TextView priceTextView;
        TextView locationTextView;
        TextView contentTextView;
        Button btnDelete;
        ImageButton video_post;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            titleTextView = itemView.findViewById(R.id.titleTextView);
            priceTextView = itemView.findViewById(R.id.priceTextView);
            locationTextView = itemView.findViewById(R.id.locationTextView);
            contentTextView = itemView.findViewById(R.id.contentTextView);
            btnDelete = itemView.findViewById(R.id.btnDelete);
            video_post = itemView.findViewById(R.id.ibReproducirVideo2);
        }
    }
    public void setData(List<Clasificado> newData) {
        Log.d(this.getClass().getSimpleName(), "setData pdc01");
        clasificadosList = newData;
        notifyDataSetChanged();
        Log.d(this.getClass().getSimpleName(), "setData pdc02");

    }

}
