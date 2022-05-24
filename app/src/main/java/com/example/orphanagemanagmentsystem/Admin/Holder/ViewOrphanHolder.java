package com.example.orphanagemanagmentsystem.Admin.Holder;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.orphanagemanagmentsystem.Admin.ManageOrphanActivity;
import com.example.orphanagemanagmentsystem.Admin.UpdateOrphanActivity;
import com.example.orphanagemanagmentsystem.Model.Model_Orphan;
import com.example.orphanagemanagmentsystem.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ViewOrphanHolder extends RecyclerView.Adapter<ViewOrphanHolder.ViewHolder>{
List<Model_Orphan> model_orphanList;
Context context;

    public ViewOrphanHolder(List<Model_Orphan> model_orphanList, Context context) {
        this.model_orphanList = model_orphanList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context= parent.getContext();
        View view= LayoutInflater.from(context).inflate(R.layout.orphanview,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
Model_Orphan model_orphan=model_orphanList.get(position);
holder.name.setText(model_orphan.getName());
holder.age.setText(model_orphan.getAge());
holder.gender.setText(model_orphan.getGender());
holder.dob.setText(model_orphan.getDOB());
holder.addr.setText(model_orphan.getAddress());
        Picasso.get().load(model_orphan.getImage())
                .fit().centerCrop()
                .placeholder(R.drawable.placeholder)
                .error(R.drawable.placeholder)
                .into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return model_orphanList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView name,age,gender,dob,addr;
        Button btnUpdate,btnDelete;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView=itemView.findViewById(R.id.VieworphanImg);
            name=itemView.findViewById(R.id.tvOrphanName);
            age=itemView.findViewById(R.id.tvOrphanAge);
            gender=itemView.findViewById(R.id.tvOrphanGender);
            dob=itemView.findViewById(R.id.tvOrphanDob);
            addr=itemView.findViewById(R.id.tvOrphanADDR);
            btnUpdate=itemView.findViewById(R.id.btnOrphanUpdate);
            btnDelete=itemView.findViewById(R.id.btnOrphanDelete);
            btnDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int pos = getAdapterPosition();
                    String key = model_orphanList.get(pos).getKey();
                    String imgUrl = model_orphanList.get(pos).getImage();
                  DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("Orphans").child(key);
                    FirebaseStorage firebaseStorage = FirebaseStorage.getInstance();
                    StorageReference storageReference = firebaseStorage.getReferenceFromUrl(imgUrl);
                    storageReference.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            databaseReference.removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    Toast.makeText(context, "Deleted", Toast.LENGTH_SHORT).show();
                                    ((Activity)context).finish();
                                    context.startActivity(new Intent(context, ManageOrphanActivity.class));
                                }
                            });
                        }
                    })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(context, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            });
                }
            });

            btnUpdate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int pos = getAdapterPosition();
                    String key = model_orphanList.get(pos).getKey();
                    Intent intent=new Intent(context, UpdateOrphanActivity.class);
                    intent.putExtra("OrphanKey",key);
                    context.startActivity(intent);

                }
            });

        }
    }
}
