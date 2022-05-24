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

import com.example.orphanagemanagmentsystem.Model.Model_Donor;
import com.example.orphanagemanagmentsystem.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;

public class Register extends AppCompatActivity {
EditText name,email,phone,address,password;
Button btnReg;
DatabaseReference databaseReference;
FirebaseAuth firebaseAuth;
ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        name=findViewById(R.id.DonorName);
        email=findViewById(R.id.DonorEmail);
        phone=findViewById(R.id.DonorPhone);
        address=findViewById(R.id.DonorADDR);
        password=findViewById(R.id.DonorPassword);
        btnReg=findViewById(R.id.btnRegDonor);
        firebaseAuth=FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Registering Please Wait");
        databaseReference= FirebaseDatabase.getInstance().getReference().child("Donors");
        btnReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String Name=name.getText().toString().trim().toLowerCase();
                String Email=email.getText().toString().trim().toLowerCase();
                String Phone=phone.getText().toString().trim().toLowerCase();
                String Address=address.getText().toString().trim().toLowerCase();
                String Password=password.getText().toString().trim().toLowerCase();
                if (TextUtils.isEmpty(Name)){
                    name.setError("Required");
                } else if (TextUtils.isEmpty(Email)){
                    email.setError("Required");
                }else if (TextUtils.isEmpty(Phone)){
                    phone.setError("Required");
                }else if (TextUtils.isEmpty(Address)){
                    address.setError("Required");
                }else if (TextUtils.isEmpty(Password)){
                    password.setError("Required");
                } else {
                    progressDialog.show();

                    firebaseAuth.createUserWithEmailAndPassword(Email,Password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                        @Override
                        public void onSuccess(AuthResult authResult) {

                            String UID = Objects.requireNonNull(authResult.getUser()).getUid();
                            Model_Donor model_donor=new Model_Donor(Name,Email,Phone,Address,Password,UID);
                            databaseReference.setValue(model_donor).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    Toast.makeText(Register.this, "Donor Registered", Toast.LENGTH_SHORT).show();

                                firebaseAuth.signOut();
                                progressDialog.dismiss();
                                startActivity(new Intent(Register.this,Login.class));
                                finish();
                                }
                            })
                                    .addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            progressDialog.dismiss();
                                            Toast.makeText(Register.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                                        }
                                    });



                        }
                    })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    progressDialog.dismiss();
                                    Toast.makeText(Register.this, "OnRegisterException\t"+e.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            });
                }
            }
        });


    }
}