package com.example.orphanagemanagmentsystem.Admin;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.orphanagemanagmentsystem.Model.Model_Orphan;
import com.example.orphanagemanagmentsystem.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.Objects;

public class AddOrphanActivity extends AppCompatActivity {
EditText name,age,gender,addr,dob;
TextView chooseImg;
Button btnAdd;
    ActivityResultLauncher<Intent> activityResultLauncher;
    Uri uri;
    String imageUrl;
    FirebaseStorage storage;
    StorageReference storageReference;
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_orphan);
        name=findViewById(R.id.OrphanName);
        age=findViewById(R.id.OrphanAge);
        gender=findViewById(R.id.OrphanGender);
        addr=findViewById(R.id.OrphanADDR);
        dob=findViewById(R.id.OrphanDOB);
        btnAdd=findViewById(R.id.btnAddOrphan);
        chooseImg=findViewById(R.id.OrphanIMG);
        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        storageReference = FirebaseStorage.getInstance().getReference("Assets");
chooseImg.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/");
        activityResultLauncher.launch(intent);
    }
});
        activityResultLauncher=registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        try {


                            if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                                uri = result.getData().getData();

                            }
                        } catch (Exception e){
                            e.fillInStackTrace();
                            Log.d("imgError","Error\n"+e.getMessage());
                        }



                    }
                });


btnAdd.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {

        String Name =name.getText().toString().trim();
        String Age =age.getText().toString().trim();
        String Gender =gender.getText().toString().trim();
        String DOB =dob.getText().toString().trim();
        String Address =addr.getText().toString().trim();
        if (TextUtils.isEmpty(Name)){
            name.setError("Required");
        } else if (TextUtils.isEmpty(Age)){
            addr.setError("Required");
        } else if (TextUtils.isEmpty(Gender)){
            gender.setError("Required");
        } else if (TextUtils.isEmpty(DOB)){
            dob.setError("Required");
        } else if (TextUtils.isEmpty(Address)){
            addr.setError("Required");
        } else {
            progressDialog.setMessage("Please Wait!.....");
            progressDialog.show();
            StorageReference storageReference2 = storageReference.child(System.currentTimeMillis()+"."+getFileExtension(uri));
            storageReference2.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                    Task<Uri> task= Objects.requireNonNull(Objects.requireNonNull(taskSnapshot.getMetadata()).getReference()).getDownloadUrl();
task.addOnSuccessListener(new OnSuccessListener<Uri>() {
    @Override
    public void onSuccess(Uri imaguri) {
        imageUrl=imaguri.toString();
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("Orphans");
        String key = databaseReference.push().getKey();
        assert key != null;
        Model_Orphan model_orphan=new Model_Orphan(key,Name,DOB,Age,Gender,Address,imageUrl);
databaseReference.child(key).setValue(model_orphan).addOnSuccessListener(new OnSuccessListener<Void>() {
    @Override
    public void onSuccess(Void unused) {
        Toast.makeText(AddOrphanActivity.this, "Orphan Added", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(AddOrphanActivity.this,MainActivity.class));
        progressDialog.dismiss();
    }
})
        .addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                progressDialog.dismiss();

                Toast.makeText(AddOrphanActivity.this, "OrAddOrphanException"+e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });


    }
})
        .addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                progressDialog.dismiss();

                Toast.makeText(AddOrphanActivity.this, "OnDownloadUrlException"+e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
                }
            })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressDialog.dismiss();

                            Toast.makeText(AddOrphanActivity.this, "OnImageUploadException"+e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });


        }
    }
});

    }
    private String getFileExtension(Uri uri){
        ContentResolver contentResolver = getContentResolver();
        MimeTypeMap mimeTypeMap =MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));
    }
}