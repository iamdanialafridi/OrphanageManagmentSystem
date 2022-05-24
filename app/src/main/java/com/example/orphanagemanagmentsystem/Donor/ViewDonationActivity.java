package com.example.orphanagemanagmentsystem.Donor;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.widget.Toast;

import com.example.orphanagemanagmentsystem.Donor.Holder.ViewDonationHolder;
import com.example.orphanagemanagmentsystem.Model.Model_Donation;
import com.example.orphanagemanagmentsystem.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ViewDonationActivity extends AppCompatActivity {
RecyclerView recyclerView;
String UID;
FirebaseAuth firebaseAuth;
DatabaseReference databaseReference;
List<Model_Donation> model_donationList;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_donation);
        recyclerView=findViewById(R.id.rvDonorDonation);
        firebaseAuth=FirebaseAuth.getInstance();
        UID=firebaseAuth.getUid();
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Donation");
recyclerView.setHasFixedSize(true);
        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(true);
        progressDialog.setMessage("Requesting Please Wait");
        progressDialog.show();
recyclerView.setLayoutManager(new LinearLayoutManager(this));
model_donationList=new ArrayList<>();
fetchDonation();

    }

    private void fetchDonation() {
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (model_donationList == null){
                    progressDialog.dismiss();
                }else {
                    model_donationList.clear();
                    for (DataSnapshot data : snapshot.getChildren()){
                        Model_Donation model_donation= data.getValue(Model_Donation.class);
                        assert model_donation != null;
                        if (model_donation.getUID().equals(UID)){
                            model_donationList.add(model_donation);
                            progressDialog.dismiss();
                        }

                    }
                    ViewDonationHolder viewDonationHolder=new ViewDonationHolder(ViewDonationActivity.this,model_donationList);
                    recyclerView.setAdapter(viewDonationHolder);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                progressDialog.dismiss();

                Toast.makeText(ViewDonationActivity.this, "Database Error\t"+ error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}