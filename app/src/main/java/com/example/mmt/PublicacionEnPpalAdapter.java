
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

public class PublicacionEnPpalAdapter extends RecyclerView.Adapter<PublicacionEnPpalAdapter.ViewHolder> {

    FirebaseFirestore mFirestore;
    FirebaseAuth mAuth;

    private List<Publicacion> publicacionesList;

    public PublicacionEnPpalAdapter(List<Publicacion> publicacionesList) {
        this.publicacionesList = publicacionesList;

        mFirestore = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_post_ppal, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Publicacion publicacion = publicacionesList.get(position);

        holder.titleTextView.setText(publicacion.getTitle());
        //holder.genereTextView.setText(publicacion.getGenre());
        //holder.locationTextView.setText(publicacion.getLocation());
        holder.contentTextView.setText(publicacion.getContent());
//        holder.creationDateTextView.setText(publicacion.getCreationDate().toString());
        String videoUrl = publicacion.getVideo();
        holder.video_post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                if (!TextUtils.isEmpty(videoUrl)) {
//                    Intent intent = new Intent(Intent.ACTION_VIEW);
//                    intent.setData(Uri.parse(videoUrl));
//                    v.getContext().startActivity(intent);
//                }
                if (!TextUtils.isEmpty(videoUrl)) {
                    Intent intent = new Intent(v.getContext(), ReproductorDeVideo.class);
                    intent.putExtra("VIDEO_URL", videoUrl); // Pasa la URL del video como un extra en el Intent
                    v.getContext().startActivity(intent);
                }

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
        ImageButton video_post;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            titleTextView = itemView.findViewById(R.id.titleTextView);
            //genereTextView = itemView.findViewById(R.id.genereTextView);
            //locationTextView = itemView.findViewById(R.id.locationTextView);
            contentTextView = itemView.findViewById(R.id.contentTextView);
            //creationDateTextView = itemView.findViewById(R.id.creationDateTextView);
            video_post = itemView.findViewById(R.id.ibReproducirVideo2);
        }
    }
    public void setData(List<Publicacion> newData) {
        publicacionesList = newData;
        notifyDataSetChanged();
    }

}
