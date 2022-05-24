package com.example.orphanagemanagmentsystem.Admin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.orphanagemanagmentsystem.R;

public class Login extends AppCompatActivity {
    EditText email,pass;
    Button btnlogin;
    SessionManager sessionManager;
    private final String adminEmail="admin@gmail.com";
    private final String adminPassword="admin123456";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_activity_login);
        email=findViewById(R.id.adminEmail);
        pass=findViewById(R.id.adminPass);
        btnlogin=findViewById(R.id.btnAdminLogin);
        sessionManager=new SessionManager(this);

        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String Email=email.getText().toString().trim().toLowerCase();
                String Password=pass.getText().toString().trim().toLowerCase();
                if (TextUtils.isEmpty(Email)){
                    email.setError("Required");
                } else if (TextUtils.isEmpty(Password)){
                    pass.setError("Required");
                } else if (!isValidEmail(Email)){
                    email.setError("Invalid Email");
                } else {
                    if (adminEmail.matches(Email) && adminPassword.matches(Password)){
                        sessionManager.setLogin(true);
                        sessionManager.setEmail(Email);
                        Toast.makeText(Login.this, "Logged In Successfully", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(Login.this,MainActivity.class));
                    }else {
                        Log.d("exp404","Invalid Email");
                        Toast.makeText(Login.this, "Invalid Email/Password", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
    public static boolean isValidEmail(CharSequence target) {
        return (!TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches());
    }
}