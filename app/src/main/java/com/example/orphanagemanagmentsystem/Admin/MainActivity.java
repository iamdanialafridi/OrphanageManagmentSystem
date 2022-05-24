package com.example.orphanagemanagmentsystem.Admin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.orphanagemanagmentsystem.R;

public class MainActivity extends AppCompatActivity {
    SessionManager sessionManager;
    Button btnViewOrphan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_activity_main);
        btnViewOrphan=findViewById(R.id.btnViewOrphan);
        sessionManager=new SessionManager(this);
        if (!sessionManager.getLogin() ){
            startActivity(new Intent(this, com.example.orphanagemanagmentsystem.MainActivity.class));
        }

        btnViewOrphan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
startActivity(new Intent(MainActivity.this,ManageOrphanActivity.class));
            }
        });

    }
}