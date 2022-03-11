package com.example.russerials;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.graphics.drawable.RoundedBitmapDrawable;
import androidx.core.graphics.drawable.RoundedBitmapDrawableFactory;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.squareup.picasso.Picasso;

import java.util.List;

public class SerialAdapter extends RecyclerView.Adapter<SerialAdapter.SerialViewHolder>{
    private List<SerialView> list;
    private Context parent;
    private Intent intent;
    private Intent intentVideo;
    private FirebaseAuth mAuth;
    private FirebaseUser user;
    public SerialAdapter(List<SerialView> list, Context parent) {
        this.list = list;
        this.parent = parent;
    }

    @NonNull
    @Override
    public SerialViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new SerialViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_serial, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull SerialViewHolder holder, int position) {
        SerialView serialView = list.get(position);
        holder.txtName.setText(serialView.getName());
        holder.txtDes.setText(serialView.getShortDes());
        Picasso.get().load(serialView.getImageId()).into(holder.imagePreview);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class SerialViewHolder extends RecyclerView.ViewHolder{

        TextView txtName, txtDes;
        ImageView imagePreview;
        Button btLike;

        public SerialViewHolder(View itemView){
            super(itemView);

            txtName = itemView.findViewById(R.id.txt_name);
            txtDes = itemView.findViewById(R.id.txtDes);
            imagePreview = itemView.findViewById(R.id.imagePreview);
            //btLike = itemView.findViewById(R.id.button_save);
            final userView userView = new userView();
            mAuth = FirebaseAuth.getInstance();
            user = mAuth.getCurrentUser();


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    SerialView serialView = list.get(getAdapterPosition());
                    intent = new Intent(parent, EpisodelistActivity.class);
                    intentVideo = new Intent(parent, ViewVideoActivity.class);
                    if(!serialView.getUrl().equals("none") || !serialView.getFullDes().equals("none")){
                        intentVideo.putExtra("url", serialView.getUrl());
                        intentVideo.putExtra("des", serialView.getFullDes());
                        parent.startActivity(intentVideo);
                    }
                    else {
                        if(!serialView.getEpisodeList().isEmpty()){
                            intent.putExtra("eplist", serialView.getEpisodeList());
                            Log.d("Eplist", serialView.getEpisodeList());
                            parent.startActivity(intent);
                        }
                    }


                }
            });
        }
    }
}
