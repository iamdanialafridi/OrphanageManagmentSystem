package com.example.orphanagemanagmentsystem;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.orphanagemanagmentsystem.Admin.Login;

public class MainActivity extends AppCompatActivity {
Button btnAdmin,btnDonor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnAdmin=findViewById(R.id.btnAdmin);
        btnDonor=findViewById(R.id.btnDonor);
btnAdmin.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        startActivity(new Intent(MainActivity.this, Login.class));
    }
});


btnDonor.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        startActivity(new Intent(MainActivity.this, com.example.orphanagemanagmentsystem.Donor.Login.class));
    }
});


    }
}