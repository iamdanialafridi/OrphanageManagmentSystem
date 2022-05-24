package com.example.orphanagemanagmentsystem.Donor;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.orphanagemanagmentsystem.R;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {
Button btnLogout,btnViewOrphan,btnviewDonation;
FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.donor_activity_main);
        btnViewOrphan= findViewById(R.id.donorViewOrphan);
        btnviewDonation= findViewById(R.id.donorViewDonation);
        btnLogout= findViewById(R.id.donorLogout);
        firebaseAuth=FirebaseAuth.getInstance();
        if (firebaseAuth.getCurrentUser() == null){
            startActivity(new Intent(MainActivity.this, com.example.orphanagemanagmentsystem.MainActivity.class));
finish();
        }

        btnviewDonation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,ViewDonationActivity.class));
            }
        });

btnViewOrphan.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
startActivity(new Intent(MainActivity.this,ViewOrphanActivity.class));

    }
});

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               if (firebaseAuth.getCurrentUser() != null){
                   firebaseAuth.signOut();
                   Toast.makeText(MainActivity.this, "Logout Successfully", Toast.LENGTH_SHORT).show();
                   startActivity(new Intent(MainActivity.this, com.example.orphanagemanagmentsystem.MainActivity.class));
                   finish();
               }
            }
        });
    }
}