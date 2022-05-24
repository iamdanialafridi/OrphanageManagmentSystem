package com.example.orphanagemanagmentsystem.Donor;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.widget.Toast;

import com.example.orphanagemanagmentsystem.Admin.ManageOrphanActivity;
import com.example.orphanagemanagmentsystem.Donor.Holder.OrphanHolder;
import com.example.orphanagemanagmentsystem.Model.Model_Orphan;
import com.example.orphanagemanagmentsystem.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ViewOrphanActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    List<Model_Orphan> model_orphanList;
    ProgressDialog progressDialog;
    DatabaseReference databaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_orphan);
        recyclerView=findViewById(R.id.RvOrphan);
        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Requesting Please Wait");
        progressDialog.show();
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Orphans");
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        model_orphanList=new ArrayList<>();
        fetchAllOrphan();
    }

    private void fetchAllOrphan() {
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (model_orphanList==null){
                    progressDialog.dismiss();
                } else {
                    model_orphanList.clear();
                    for (DataSnapshot dataSnapshot:snapshot.getChildren()){
                        Model_Orphan model_orphan=dataSnapshot.getValue(Model_Orphan.class);
                        model_orphanList.add(model_orphan);
                        progressDialog.dismiss();

                    }

                    OrphanHolder orphanHolder=new OrphanHolder(model_orphanList,ViewOrphanActivity.this);
                    recyclerView.setAdapter(orphanHolder);


                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                progressDialog.dismiss();
                Toast.makeText(ViewOrphanActivity.this, "DataBaseException\t"+error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}