package com.example.orphanagemanagmentsystem.Donor;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.orphanagemanagmentsystem.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;

public class Login extends AppCompatActivity {
Button btnReg,btnlogin;
    EditText email,password;

    FirebaseAuth firebaseAuth;
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_activity_login);
        email=findViewById(R.id.LoginDonorEmail);
        password=findViewById(R.id.LoginDonorPassword);
        btnlogin=findViewById(R.id.btnLoginDonor);
        btnReg=findViewById(R.id.btnRegDon);

        firebaseAuth=FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Logging Please Wait");
        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String Email=email.getText().toString().trim().toLowerCase();
                String Password=password.getText().toString().trim().toLowerCase();
                if (TextUtils.isEmpty(Email)){
                    email.setError("Required");
                }else if (TextUtils.isEmpty(Password)){
                    password.setError("Required");
                } else {
                    progressDialog.show();
                    firebaseAuth.signInWithEmailAndPassword(Email,Password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                        @Override
                        public void onSuccess(AuthResult authResult) {
                            progressDialog.dismiss();
                            Toast.makeText(Login.this, "User LoggedIn Successfully", Toast.LENGTH_SHORT).show();
                            finish();
                            startActivity(new Intent(Login.this,MainActivity.class));
                        }
                    })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    progressDialog.dismiss();
                                    Toast.makeText(Login.this, "OnLoginException\t"+e.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            });

                }
            }
        });


        btnReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Login.this,Register.class));
            }
        });
    }
}