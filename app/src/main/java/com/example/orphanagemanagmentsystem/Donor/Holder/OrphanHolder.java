package com.example.orphanagemanagmentsystem.Donor.Holder;

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
import com.example.orphanagemanagmentsystem.Donor.SendDonationActivity;
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

public class OrphanHolder extends RecyclerView.Adapter<OrphanHolder.ViewHolder>{
List<Model_Orphan> model_orphanList;
Context context;

    public OrphanHolder(List<Model_Orphan> model_orphanList, Context context) {
        this.model_orphanList = model_orphanList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context= parent.getContext();
        View view= LayoutInflater.from(context).inflate(R.layout.orphanview_xml,parent,false);
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
        Button btnDonote;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView=itemView.findViewById(R.id.VieworphanImg_donor);
            name=itemView.findViewById(R.id.tvOrphanName_donor);
            age=itemView.findViewById(R.id.tvOrphanAge_donor);
            gender=itemView.findViewById(R.id.tvOrphanGender_donor);
            dob=itemView.findViewById(R.id.tvOrphanDob_donor);
            addr=itemView.findViewById(R.id.tvOrphanADDR_donor);
            btnDonote=itemView.findViewById(R.id.btnSendDonation);
            btnDonote.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int pos = getAdapterPosition();
                    String  key = model_orphanList.get(pos).getKey();
Intent intent=new Intent(context, SendDonationActivity.class);
intent.putExtra("KeyOrphan",key);
context.startActivity(intent);

                }
            });



        }
    }
}
